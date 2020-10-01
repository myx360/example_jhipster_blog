package com.lecg.blog.web.rest;

import com.lecg.blog.LecgApp;
import com.lecg.blog.domain.SiteUser;
import com.lecg.blog.domain.User;
import com.lecg.blog.domain.Blog;
import com.lecg.blog.repository.SiteUserRepository;
import com.lecg.blog.service.SiteUserService;
import com.lecg.blog.service.dto.SiteUserDTO;
import com.lecg.blog.service.mapper.SiteUserMapper;
import com.lecg.blog.service.dto.SiteUserCriteria;
import com.lecg.blog.service.SiteUserQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SiteUserResource} REST controller.
 */
@SpringBootTest(classes = LecgApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SiteUserResourceIT {

    private static final String DEFAULT_DISPLAY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MODERATOR = false;
    private static final Boolean UPDATED_MODERATOR = true;

    @Autowired
    private SiteUserRepository siteUserRepository;

    @Autowired
    private SiteUserMapper siteUserMapper;

    @Autowired
    private SiteUserService siteUserService;

    @Autowired
    private SiteUserQueryService siteUserQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSiteUserMockMvc;

    private SiteUser siteUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SiteUser createEntity(EntityManager em) {
        SiteUser siteUser = new SiteUser()
            .displayName(DEFAULT_DISPLAY_NAME)
            .moderator(DEFAULT_MODERATOR);
        return siteUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SiteUser createUpdatedEntity(EntityManager em) {
        SiteUser siteUser = new SiteUser()
            .displayName(UPDATED_DISPLAY_NAME)
            .moderator(UPDATED_MODERATOR);
        return siteUser;
    }

    @BeforeEach
    public void initTest() {
        siteUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createSiteUser() throws Exception {
        int databaseSizeBeforeCreate = siteUserRepository.findAll().size();
        // Create the SiteUser
        SiteUserDTO siteUserDTO = siteUserMapper.toDto(siteUser);
        restSiteUserMockMvc.perform(post("/api/site-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(siteUserDTO)))
            .andExpect(status().isCreated());

        // Validate the SiteUser in the database
        List<SiteUser> siteUserList = siteUserRepository.findAll();
        assertThat(siteUserList).hasSize(databaseSizeBeforeCreate + 1);
        SiteUser testSiteUser = siteUserList.get(siteUserList.size() - 1);
        assertThat(testSiteUser.getDisplayName()).isEqualTo(DEFAULT_DISPLAY_NAME);
        assertThat(testSiteUser.isModerator()).isEqualTo(DEFAULT_MODERATOR);
    }

    @Test
    @Transactional
    public void createSiteUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = siteUserRepository.findAll().size();

        // Create the SiteUser with an existing ID
        siteUser.setId(1L);
        SiteUserDTO siteUserDTO = siteUserMapper.toDto(siteUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSiteUserMockMvc.perform(post("/api/site-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(siteUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SiteUser in the database
        List<SiteUser> siteUserList = siteUserRepository.findAll();
        assertThat(siteUserList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDisplayNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteUserRepository.findAll().size();
        // set the field null
        siteUser.setDisplayName(null);

        // Create the SiteUser, which fails.
        SiteUserDTO siteUserDTO = siteUserMapper.toDto(siteUser);


        restSiteUserMockMvc.perform(post("/api/site-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(siteUserDTO)))
            .andExpect(status().isBadRequest());

        List<SiteUser> siteUserList = siteUserRepository.findAll();
        assertThat(siteUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSiteUsers() throws Exception {
        // Initialize the database
        siteUserRepository.saveAndFlush(siteUser);

        // Get all the siteUserList
        restSiteUserMockMvc.perform(get("/api/site-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(siteUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].displayName").value(hasItem(DEFAULT_DISPLAY_NAME)))
            .andExpect(jsonPath("$.[*].moderator").value(hasItem(DEFAULT_MODERATOR.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getSiteUser() throws Exception {
        // Initialize the database
        siteUserRepository.saveAndFlush(siteUser);

        // Get the siteUser
        restSiteUserMockMvc.perform(get("/api/site-users/{id}", siteUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(siteUser.getId().intValue()))
            .andExpect(jsonPath("$.displayName").value(DEFAULT_DISPLAY_NAME))
            .andExpect(jsonPath("$.moderator").value(DEFAULT_MODERATOR.booleanValue()));
    }


    @Test
    @Transactional
    public void getSiteUsersByIdFiltering() throws Exception {
        // Initialize the database
        siteUserRepository.saveAndFlush(siteUser);

        Long id = siteUser.getId();

        defaultSiteUserShouldBeFound("id.equals=" + id);
        defaultSiteUserShouldNotBeFound("id.notEquals=" + id);

        defaultSiteUserShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSiteUserShouldNotBeFound("id.greaterThan=" + id);

        defaultSiteUserShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSiteUserShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSiteUsersByDisplayNameIsEqualToSomething() throws Exception {
        // Initialize the database
        siteUserRepository.saveAndFlush(siteUser);

        // Get all the siteUserList where displayName equals to DEFAULT_DISPLAY_NAME
        defaultSiteUserShouldBeFound("displayName.equals=" + DEFAULT_DISPLAY_NAME);

        // Get all the siteUserList where displayName equals to UPDATED_DISPLAY_NAME
        defaultSiteUserShouldNotBeFound("displayName.equals=" + UPDATED_DISPLAY_NAME);
    }

    @Test
    @Transactional
    public void getAllSiteUsersByDisplayNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        siteUserRepository.saveAndFlush(siteUser);

        // Get all the siteUserList where displayName not equals to DEFAULT_DISPLAY_NAME
        defaultSiteUserShouldNotBeFound("displayName.notEquals=" + DEFAULT_DISPLAY_NAME);

        // Get all the siteUserList where displayName not equals to UPDATED_DISPLAY_NAME
        defaultSiteUserShouldBeFound("displayName.notEquals=" + UPDATED_DISPLAY_NAME);
    }

    @Test
    @Transactional
    public void getAllSiteUsersByDisplayNameIsInShouldWork() throws Exception {
        // Initialize the database
        siteUserRepository.saveAndFlush(siteUser);

        // Get all the siteUserList where displayName in DEFAULT_DISPLAY_NAME or UPDATED_DISPLAY_NAME
        defaultSiteUserShouldBeFound("displayName.in=" + DEFAULT_DISPLAY_NAME + "," + UPDATED_DISPLAY_NAME);

        // Get all the siteUserList where displayName equals to UPDATED_DISPLAY_NAME
        defaultSiteUserShouldNotBeFound("displayName.in=" + UPDATED_DISPLAY_NAME);
    }

    @Test
    @Transactional
    public void getAllSiteUsersByDisplayNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        siteUserRepository.saveAndFlush(siteUser);

        // Get all the siteUserList where displayName is not null
        defaultSiteUserShouldBeFound("displayName.specified=true");

        // Get all the siteUserList where displayName is null
        defaultSiteUserShouldNotBeFound("displayName.specified=false");
    }
                @Test
    @Transactional
    public void getAllSiteUsersByDisplayNameContainsSomething() throws Exception {
        // Initialize the database
        siteUserRepository.saveAndFlush(siteUser);

        // Get all the siteUserList where displayName contains DEFAULT_DISPLAY_NAME
        defaultSiteUserShouldBeFound("displayName.contains=" + DEFAULT_DISPLAY_NAME);

        // Get all the siteUserList where displayName contains UPDATED_DISPLAY_NAME
        defaultSiteUserShouldNotBeFound("displayName.contains=" + UPDATED_DISPLAY_NAME);
    }

    @Test
    @Transactional
    public void getAllSiteUsersByDisplayNameNotContainsSomething() throws Exception {
        // Initialize the database
        siteUserRepository.saveAndFlush(siteUser);

        // Get all the siteUserList where displayName does not contain DEFAULT_DISPLAY_NAME
        defaultSiteUserShouldNotBeFound("displayName.doesNotContain=" + DEFAULT_DISPLAY_NAME);

        // Get all the siteUserList where displayName does not contain UPDATED_DISPLAY_NAME
        defaultSiteUserShouldBeFound("displayName.doesNotContain=" + UPDATED_DISPLAY_NAME);
    }


    @Test
    @Transactional
    public void getAllSiteUsersByModeratorIsEqualToSomething() throws Exception {
        // Initialize the database
        siteUserRepository.saveAndFlush(siteUser);

        // Get all the siteUserList where moderator equals to DEFAULT_MODERATOR
        defaultSiteUserShouldBeFound("moderator.equals=" + DEFAULT_MODERATOR);

        // Get all the siteUserList where moderator equals to UPDATED_MODERATOR
        defaultSiteUserShouldNotBeFound("moderator.equals=" + UPDATED_MODERATOR);
    }

    @Test
    @Transactional
    public void getAllSiteUsersByModeratorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        siteUserRepository.saveAndFlush(siteUser);

        // Get all the siteUserList where moderator not equals to DEFAULT_MODERATOR
        defaultSiteUserShouldNotBeFound("moderator.notEquals=" + DEFAULT_MODERATOR);

        // Get all the siteUserList where moderator not equals to UPDATED_MODERATOR
        defaultSiteUserShouldBeFound("moderator.notEquals=" + UPDATED_MODERATOR);
    }

    @Test
    @Transactional
    public void getAllSiteUsersByModeratorIsInShouldWork() throws Exception {
        // Initialize the database
        siteUserRepository.saveAndFlush(siteUser);

        // Get all the siteUserList where moderator in DEFAULT_MODERATOR or UPDATED_MODERATOR
        defaultSiteUserShouldBeFound("moderator.in=" + DEFAULT_MODERATOR + "," + UPDATED_MODERATOR);

        // Get all the siteUserList where moderator equals to UPDATED_MODERATOR
        defaultSiteUserShouldNotBeFound("moderator.in=" + UPDATED_MODERATOR);
    }

    @Test
    @Transactional
    public void getAllSiteUsersByModeratorIsNullOrNotNull() throws Exception {
        // Initialize the database
        siteUserRepository.saveAndFlush(siteUser);

        // Get all the siteUserList where moderator is not null
        defaultSiteUserShouldBeFound("moderator.specified=true");

        // Get all the siteUserList where moderator is null
        defaultSiteUserShouldNotBeFound("moderator.specified=false");
    }

    @Test
    @Transactional
    public void getAllSiteUsersByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        siteUserRepository.saveAndFlush(siteUser);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        siteUser.setUser(user);
        siteUserRepository.saveAndFlush(siteUser);
        Long userId = user.getId();

        // Get all the siteUserList where user equals to userId
        defaultSiteUserShouldBeFound("userId.equals=" + userId);

        // Get all the siteUserList where user equals to userId + 1
        defaultSiteUserShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllSiteUsersByBlogIsEqualToSomething() throws Exception {
        // Initialize the database
        siteUserRepository.saveAndFlush(siteUser);
        Blog blog = BlogResourceIT.createEntity(em);
        em.persist(blog);
        em.flush();
        siteUser.addBlog(blog);
        siteUserRepository.saveAndFlush(siteUser);
        Long blogId = blog.getId();

        // Get all the siteUserList where blog equals to blogId
        defaultSiteUserShouldBeFound("blogId.equals=" + blogId);

        // Get all the siteUserList where blog equals to blogId + 1
        defaultSiteUserShouldNotBeFound("blogId.equals=" + (blogId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSiteUserShouldBeFound(String filter) throws Exception {
        restSiteUserMockMvc.perform(get("/api/site-users?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(siteUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].displayName").value(hasItem(DEFAULT_DISPLAY_NAME)))
            .andExpect(jsonPath("$.[*].moderator").value(hasItem(DEFAULT_MODERATOR.booleanValue())));

        // Check, that the count call also returns 1
        restSiteUserMockMvc.perform(get("/api/site-users/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSiteUserShouldNotBeFound(String filter) throws Exception {
        restSiteUserMockMvc.perform(get("/api/site-users?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSiteUserMockMvc.perform(get("/api/site-users/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingSiteUser() throws Exception {
        // Get the siteUser
        restSiteUserMockMvc.perform(get("/api/site-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSiteUser() throws Exception {
        // Initialize the database
        siteUserRepository.saveAndFlush(siteUser);

        int databaseSizeBeforeUpdate = siteUserRepository.findAll().size();

        // Update the siteUser
        SiteUser updatedSiteUser = siteUserRepository.findById(siteUser.getId()).get();
        // Disconnect from session so that the updates on updatedSiteUser are not directly saved in db
        em.detach(updatedSiteUser);
        updatedSiteUser
            .displayName(UPDATED_DISPLAY_NAME)
            .moderator(UPDATED_MODERATOR);
        SiteUserDTO siteUserDTO = siteUserMapper.toDto(updatedSiteUser);

        restSiteUserMockMvc.perform(put("/api/site-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(siteUserDTO)))
            .andExpect(status().isOk());

        // Validate the SiteUser in the database
        List<SiteUser> siteUserList = siteUserRepository.findAll();
        assertThat(siteUserList).hasSize(databaseSizeBeforeUpdate);
        SiteUser testSiteUser = siteUserList.get(siteUserList.size() - 1);
        assertThat(testSiteUser.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testSiteUser.isModerator()).isEqualTo(UPDATED_MODERATOR);
    }

    @Test
    @Transactional
    public void updateNonExistingSiteUser() throws Exception {
        int databaseSizeBeforeUpdate = siteUserRepository.findAll().size();

        // Create the SiteUser
        SiteUserDTO siteUserDTO = siteUserMapper.toDto(siteUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSiteUserMockMvc.perform(put("/api/site-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(siteUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SiteUser in the database
        List<SiteUser> siteUserList = siteUserRepository.findAll();
        assertThat(siteUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSiteUser() throws Exception {
        // Initialize the database
        siteUserRepository.saveAndFlush(siteUser);

        int databaseSizeBeforeDelete = siteUserRepository.findAll().size();

        // Delete the siteUser
        restSiteUserMockMvc.perform(delete("/api/site-users/{id}", siteUser.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SiteUser> siteUserList = siteUserRepository.findAll();
        assertThat(siteUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
