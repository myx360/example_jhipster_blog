package com.lecg.blog.web.rest;

import com.lecg.blog.service.SiteUserService;
import com.lecg.blog.web.rest.errors.BadRequestAlertException;
import com.lecg.blog.service.dto.SiteUserDTO;
import com.lecg.blog.service.dto.SiteUserCriteria;
import com.lecg.blog.service.SiteUserQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.lecg.blog.domain.SiteUser}.
 */
@RestController
@RequestMapping("/api")
public class SiteUserResource {

    private final Logger log = LoggerFactory.getLogger(SiteUserResource.class);

    private static final String ENTITY_NAME = "siteUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SiteUserService siteUserService;

    private final SiteUserQueryService siteUserQueryService;

    public SiteUserResource(SiteUserService siteUserService, SiteUserQueryService siteUserQueryService) {
        this.siteUserService = siteUserService;
        this.siteUserQueryService = siteUserQueryService;
    }

    /**
     * {@code POST  /site-users} : Create a new siteUser.
     *
     * @param siteUserDTO the siteUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new siteUserDTO, or with status {@code 400 (Bad Request)} if the siteUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/site-users")
    public ResponseEntity<SiteUserDTO> createSiteUser(@Valid @RequestBody SiteUserDTO siteUserDTO) throws URISyntaxException {
        log.debug("REST request to save SiteUser : {}", siteUserDTO);
        if (siteUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new siteUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SiteUserDTO result = siteUserService.save(siteUserDTO);
        return ResponseEntity.created(new URI("/api/site-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /site-users} : Updates an existing siteUser.
     *
     * @param siteUserDTO the siteUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated siteUserDTO,
     * or with status {@code 400 (Bad Request)} if the siteUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the siteUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/site-users")
    public ResponseEntity<SiteUserDTO> updateSiteUser(@Valid @RequestBody SiteUserDTO siteUserDTO) throws URISyntaxException {
        log.debug("REST request to update SiteUser : {}", siteUserDTO);
        if (siteUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SiteUserDTO result = siteUserService.save(siteUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, siteUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /site-users} : get all the siteUsers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of siteUsers in body.
     */
    @GetMapping("/site-users")
    public ResponseEntity<List<SiteUserDTO>> getAllSiteUsers(SiteUserCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SiteUsers by criteria: {}", criteria);
        Page<SiteUserDTO> page = siteUserQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /site-users/count} : count all the siteUsers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/site-users/count")
    public ResponseEntity<Long> countSiteUsers(SiteUserCriteria criteria) {
        log.debug("REST request to count SiteUsers by criteria: {}", criteria);
        return ResponseEntity.ok().body(siteUserQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /site-users/:id} : get the "id" siteUser.
     *
     * @param id the id of the siteUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the siteUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/site-users/{id}")
    public ResponseEntity<SiteUserDTO> getSiteUser(@PathVariable Long id) {
        log.debug("REST request to get SiteUser : {}", id);
        Optional<SiteUserDTO> siteUserDTO = siteUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(siteUserDTO);
    }

    /**
     * {@code DELETE  /site-users/:id} : delete the "id" siteUser.
     *
     * @param id the id of the siteUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/site-users/{id}")
    public ResponseEntity<Void> deleteSiteUser(@PathVariable Long id) {
        log.debug("REST request to delete SiteUser : {}", id);
        siteUserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
