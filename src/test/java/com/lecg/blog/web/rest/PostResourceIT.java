package com.lecg.blog.web.rest;

import com.lecg.blog.LecgApp;
import com.lecg.blog.domain.Post;
import com.lecg.blog.domain.Subject;
import com.lecg.blog.domain.Blog;
import com.lecg.blog.repository.PostRepository;
import com.lecg.blog.service.PostService;
import com.lecg.blog.service.dto.PostDTO;
import com.lecg.blog.service.mapper.PostMapper;
import com.lecg.blog.service.dto.PostCriteria;
import com.lecg.blog.service.PostQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lecg.blog.domain.enumeration.PostType;
/**
 * Integration tests for the {@link PostResource} REST controller.
 */
@SpringBootTest(classes = LecgApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PostResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE = LocalDate.ofEpochDay(-1L);

    private static final PostType DEFAULT_TYPE = PostType.NEWS;
    private static final PostType UPDATED_TYPE = PostType.BLOG;

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PINNED = false;
    private static final Boolean UPDATED_PINNED = true;

    private static final LocalDate DEFAULT_EVENT_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EVENT_TIME = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_EVENT_TIME = LocalDate.ofEpochDay(-1L);

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private PostService postService;

    @Autowired
    private PostQueryService postQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPostMockMvc;

    private Post post;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Post createEntity(EntityManager em) {
        Post post = new Post()
            .title(DEFAULT_TITLE)
            .date(DEFAULT_DATE)
            .type(DEFAULT_TYPE)
            .content(DEFAULT_CONTENT)
            .pinned(DEFAULT_PINNED)
            .eventTime(DEFAULT_EVENT_TIME);
        return post;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Post createUpdatedEntity(EntityManager em) {
        Post post = new Post()
            .title(UPDATED_TITLE)
            .date(UPDATED_DATE)
            .type(UPDATED_TYPE)
            .content(UPDATED_CONTENT)
            .pinned(UPDATED_PINNED)
            .eventTime(UPDATED_EVENT_TIME);
        return post;
    }

    @BeforeEach
    public void initTest() {
        post = createEntity(em);
    }

    @Test
    @Transactional
    public void createPost() throws Exception {
        int databaseSizeBeforeCreate = postRepository.findAll().size();
        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);
        restPostMockMvc.perform(post("/api/posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isCreated());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeCreate + 1);
        Post testPost = postList.get(postList.size() - 1);
        assertThat(testPost.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testPost.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testPost.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPost.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testPost.isPinned()).isEqualTo(DEFAULT_PINNED);
        assertThat(testPost.getEventTime()).isEqualTo(DEFAULT_EVENT_TIME);
    }

    @Test
    @Transactional
    public void createPostWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = postRepository.findAll().size();

        // Create the Post with an existing ID
        post.setId(1L);
        PostDTO postDTO = postMapper.toDto(post);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostMockMvc.perform(post("/api/posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = postRepository.findAll().size();
        // set the field null
        post.setTitle(null);

        // Create the Post, which fails.
        PostDTO postDTO = postMapper.toDto(post);


        restPostMockMvc.perform(post("/api/posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isBadRequest());

        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = postRepository.findAll().size();
        // set the field null
        post.setDate(null);

        // Create the Post, which fails.
        PostDTO postDTO = postMapper.toDto(post);


        restPostMockMvc.perform(post("/api/posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isBadRequest());

        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = postRepository.findAll().size();
        // set the field null
        post.setType(null);

        // Create the Post, which fails.
        PostDTO postDTO = postMapper.toDto(post);


        restPostMockMvc.perform(post("/api/posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isBadRequest());

        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = postRepository.findAll().size();
        // set the field null
        post.setContent(null);

        // Create the Post, which fails.
        PostDTO postDTO = postMapper.toDto(post);


        restPostMockMvc.perform(post("/api/posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isBadRequest());

        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPosts() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList
        restPostMockMvc.perform(get("/api/posts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(post.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].pinned").value(hasItem(DEFAULT_PINNED.booleanValue())))
            .andExpect(jsonPath("$.[*].eventTime").value(hasItem(DEFAULT_EVENT_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getPost() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get the post
        restPostMockMvc.perform(get("/api/posts/{id}", post.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(post.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.pinned").value(DEFAULT_PINNED.booleanValue()))
            .andExpect(jsonPath("$.eventTime").value(DEFAULT_EVENT_TIME.toString()));
    }


    @Test
    @Transactional
    public void getPostsByIdFiltering() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        Long id = post.getId();

        defaultPostShouldBeFound("id.equals=" + id);
        defaultPostShouldNotBeFound("id.notEquals=" + id);

        defaultPostShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPostShouldNotBeFound("id.greaterThan=" + id);

        defaultPostShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPostShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPostsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where title equals to DEFAULT_TITLE
        defaultPostShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the postList where title equals to UPDATED_TITLE
        defaultPostShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllPostsByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where title not equals to DEFAULT_TITLE
        defaultPostShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the postList where title not equals to UPDATED_TITLE
        defaultPostShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllPostsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultPostShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the postList where title equals to UPDATED_TITLE
        defaultPostShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllPostsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where title is not null
        defaultPostShouldBeFound("title.specified=true");

        // Get all the postList where title is null
        defaultPostShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllPostsByTitleContainsSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where title contains DEFAULT_TITLE
        defaultPostShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the postList where title contains UPDATED_TITLE
        defaultPostShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllPostsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where title does not contain DEFAULT_TITLE
        defaultPostShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the postList where title does not contain UPDATED_TITLE
        defaultPostShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllPostsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where date equals to DEFAULT_DATE
        defaultPostShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the postList where date equals to UPDATED_DATE
        defaultPostShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPostsByDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where date not equals to DEFAULT_DATE
        defaultPostShouldNotBeFound("date.notEquals=" + DEFAULT_DATE);

        // Get all the postList where date not equals to UPDATED_DATE
        defaultPostShouldBeFound("date.notEquals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPostsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where date in DEFAULT_DATE or UPDATED_DATE
        defaultPostShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the postList where date equals to UPDATED_DATE
        defaultPostShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPostsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where date is not null
        defaultPostShouldBeFound("date.specified=true");

        // Get all the postList where date is null
        defaultPostShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where date is greater than or equal to DEFAULT_DATE
        defaultPostShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the postList where date is greater than or equal to UPDATED_DATE
        defaultPostShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPostsByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where date is less than or equal to DEFAULT_DATE
        defaultPostShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the postList where date is less than or equal to SMALLER_DATE
        defaultPostShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    public void getAllPostsByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where date is less than DEFAULT_DATE
        defaultPostShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the postList where date is less than UPDATED_DATE
        defaultPostShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPostsByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where date is greater than DEFAULT_DATE
        defaultPostShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the postList where date is greater than SMALLER_DATE
        defaultPostShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }


    @Test
    @Transactional
    public void getAllPostsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where type equals to DEFAULT_TYPE
        defaultPostShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the postList where type equals to UPDATED_TYPE
        defaultPostShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllPostsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where type not equals to DEFAULT_TYPE
        defaultPostShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the postList where type not equals to UPDATED_TYPE
        defaultPostShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllPostsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultPostShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the postList where type equals to UPDATED_TYPE
        defaultPostShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllPostsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where type is not null
        defaultPostShouldBeFound("type.specified=true");

        // Get all the postList where type is null
        defaultPostShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByContentIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where content equals to DEFAULT_CONTENT
        defaultPostShouldBeFound("content.equals=" + DEFAULT_CONTENT);

        // Get all the postList where content equals to UPDATED_CONTENT
        defaultPostShouldNotBeFound("content.equals=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void getAllPostsByContentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where content not equals to DEFAULT_CONTENT
        defaultPostShouldNotBeFound("content.notEquals=" + DEFAULT_CONTENT);

        // Get all the postList where content not equals to UPDATED_CONTENT
        defaultPostShouldBeFound("content.notEquals=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void getAllPostsByContentIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where content in DEFAULT_CONTENT or UPDATED_CONTENT
        defaultPostShouldBeFound("content.in=" + DEFAULT_CONTENT + "," + UPDATED_CONTENT);

        // Get all the postList where content equals to UPDATED_CONTENT
        defaultPostShouldNotBeFound("content.in=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void getAllPostsByContentIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where content is not null
        defaultPostShouldBeFound("content.specified=true");

        // Get all the postList where content is null
        defaultPostShouldNotBeFound("content.specified=false");
    }
                @Test
    @Transactional
    public void getAllPostsByContentContainsSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where content contains DEFAULT_CONTENT
        defaultPostShouldBeFound("content.contains=" + DEFAULT_CONTENT);

        // Get all the postList where content contains UPDATED_CONTENT
        defaultPostShouldNotBeFound("content.contains=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void getAllPostsByContentNotContainsSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where content does not contain DEFAULT_CONTENT
        defaultPostShouldNotBeFound("content.doesNotContain=" + DEFAULT_CONTENT);

        // Get all the postList where content does not contain UPDATED_CONTENT
        defaultPostShouldBeFound("content.doesNotContain=" + UPDATED_CONTENT);
    }


    @Test
    @Transactional
    public void getAllPostsByPinnedIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where pinned equals to DEFAULT_PINNED
        defaultPostShouldBeFound("pinned.equals=" + DEFAULT_PINNED);

        // Get all the postList where pinned equals to UPDATED_PINNED
        defaultPostShouldNotBeFound("pinned.equals=" + UPDATED_PINNED);
    }

    @Test
    @Transactional
    public void getAllPostsByPinnedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where pinned not equals to DEFAULT_PINNED
        defaultPostShouldNotBeFound("pinned.notEquals=" + DEFAULT_PINNED);

        // Get all the postList where pinned not equals to UPDATED_PINNED
        defaultPostShouldBeFound("pinned.notEquals=" + UPDATED_PINNED);
    }

    @Test
    @Transactional
    public void getAllPostsByPinnedIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where pinned in DEFAULT_PINNED or UPDATED_PINNED
        defaultPostShouldBeFound("pinned.in=" + DEFAULT_PINNED + "," + UPDATED_PINNED);

        // Get all the postList where pinned equals to UPDATED_PINNED
        defaultPostShouldNotBeFound("pinned.in=" + UPDATED_PINNED);
    }

    @Test
    @Transactional
    public void getAllPostsByPinnedIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where pinned is not null
        defaultPostShouldBeFound("pinned.specified=true");

        // Get all the postList where pinned is null
        defaultPostShouldNotBeFound("pinned.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByEventTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where eventTime equals to DEFAULT_EVENT_TIME
        defaultPostShouldBeFound("eventTime.equals=" + DEFAULT_EVENT_TIME);

        // Get all the postList where eventTime equals to UPDATED_EVENT_TIME
        defaultPostShouldNotBeFound("eventTime.equals=" + UPDATED_EVENT_TIME);
    }

    @Test
    @Transactional
    public void getAllPostsByEventTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where eventTime not equals to DEFAULT_EVENT_TIME
        defaultPostShouldNotBeFound("eventTime.notEquals=" + DEFAULT_EVENT_TIME);

        // Get all the postList where eventTime not equals to UPDATED_EVENT_TIME
        defaultPostShouldBeFound("eventTime.notEquals=" + UPDATED_EVENT_TIME);
    }

    @Test
    @Transactional
    public void getAllPostsByEventTimeIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where eventTime in DEFAULT_EVENT_TIME or UPDATED_EVENT_TIME
        defaultPostShouldBeFound("eventTime.in=" + DEFAULT_EVENT_TIME + "," + UPDATED_EVENT_TIME);

        // Get all the postList where eventTime equals to UPDATED_EVENT_TIME
        defaultPostShouldNotBeFound("eventTime.in=" + UPDATED_EVENT_TIME);
    }

    @Test
    @Transactional
    public void getAllPostsByEventTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where eventTime is not null
        defaultPostShouldBeFound("eventTime.specified=true");

        // Get all the postList where eventTime is null
        defaultPostShouldNotBeFound("eventTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByEventTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where eventTime is greater than or equal to DEFAULT_EVENT_TIME
        defaultPostShouldBeFound("eventTime.greaterThanOrEqual=" + DEFAULT_EVENT_TIME);

        // Get all the postList where eventTime is greater than or equal to UPDATED_EVENT_TIME
        defaultPostShouldNotBeFound("eventTime.greaterThanOrEqual=" + UPDATED_EVENT_TIME);
    }

    @Test
    @Transactional
    public void getAllPostsByEventTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where eventTime is less than or equal to DEFAULT_EVENT_TIME
        defaultPostShouldBeFound("eventTime.lessThanOrEqual=" + DEFAULT_EVENT_TIME);

        // Get all the postList where eventTime is less than or equal to SMALLER_EVENT_TIME
        defaultPostShouldNotBeFound("eventTime.lessThanOrEqual=" + SMALLER_EVENT_TIME);
    }

    @Test
    @Transactional
    public void getAllPostsByEventTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where eventTime is less than DEFAULT_EVENT_TIME
        defaultPostShouldNotBeFound("eventTime.lessThan=" + DEFAULT_EVENT_TIME);

        // Get all the postList where eventTime is less than UPDATED_EVENT_TIME
        defaultPostShouldBeFound("eventTime.lessThan=" + UPDATED_EVENT_TIME);
    }

    @Test
    @Transactional
    public void getAllPostsByEventTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where eventTime is greater than DEFAULT_EVENT_TIME
        defaultPostShouldNotBeFound("eventTime.greaterThan=" + DEFAULT_EVENT_TIME);

        // Get all the postList where eventTime is greater than SMALLER_EVENT_TIME
        defaultPostShouldBeFound("eventTime.greaterThan=" + SMALLER_EVENT_TIME);
    }


    @Test
    @Transactional
    public void getAllPostsByTagIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);
        Subject tag = SubjectResourceIT.createEntity(em);
        em.persist(tag);
        em.flush();
        post.addTag(tag);
        postRepository.saveAndFlush(post);
        Long tagId = tag.getId();

        // Get all the postList where tag equals to tagId
        defaultPostShouldBeFound("tagId.equals=" + tagId);

        // Get all the postList where tag equals to tagId + 1
        defaultPostShouldNotBeFound("tagId.equals=" + (tagId + 1));
    }


    @Test
    @Transactional
    public void getAllPostsByBlogIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);
        Blog blog = BlogResourceIT.createEntity(em);
        em.persist(blog);
        em.flush();
        post.setBlog(blog);
        postRepository.saveAndFlush(post);
        Long blogId = blog.getId();

        // Get all the postList where blog equals to blogId
        defaultPostShouldBeFound("blogId.equals=" + blogId);

        // Get all the postList where blog equals to blogId + 1
        defaultPostShouldNotBeFound("blogId.equals=" + (blogId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPostShouldBeFound(String filter) throws Exception {
        restPostMockMvc.perform(get("/api/posts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(post.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].pinned").value(hasItem(DEFAULT_PINNED.booleanValue())))
            .andExpect(jsonPath("$.[*].eventTime").value(hasItem(DEFAULT_EVENT_TIME.toString())));

        // Check, that the count call also returns 1
        restPostMockMvc.perform(get("/api/posts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPostShouldNotBeFound(String filter) throws Exception {
        restPostMockMvc.perform(get("/api/posts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPostMockMvc.perform(get("/api/posts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPost() throws Exception {
        // Get the post
        restPostMockMvc.perform(get("/api/posts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePost() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        int databaseSizeBeforeUpdate = postRepository.findAll().size();

        // Update the post
        Post updatedPost = postRepository.findById(post.getId()).get();
        // Disconnect from session so that the updates on updatedPost are not directly saved in db
        em.detach(updatedPost);
        updatedPost
            .title(UPDATED_TITLE)
            .date(UPDATED_DATE)
            .type(UPDATED_TYPE)
            .content(UPDATED_CONTENT)
            .pinned(UPDATED_PINNED)
            .eventTime(UPDATED_EVENT_TIME);
        PostDTO postDTO = postMapper.toDto(updatedPost);

        restPostMockMvc.perform(put("/api/posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isOk());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeUpdate);
        Post testPost = postList.get(postList.size() - 1);
        assertThat(testPost.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPost.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testPost.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPost.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testPost.isPinned()).isEqualTo(UPDATED_PINNED);
        assertThat(testPost.getEventTime()).isEqualTo(UPDATED_EVENT_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingPost() throws Exception {
        int databaseSizeBeforeUpdate = postRepository.findAll().size();

        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostMockMvc.perform(put("/api/posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePost() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        int databaseSizeBeforeDelete = postRepository.findAll().size();

        // Delete the post
        restPostMockMvc.perform(delete("/api/posts/{id}", post.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
