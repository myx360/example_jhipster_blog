package com.lecg.blog.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.lecg.blog.domain.SiteUser;
import com.lecg.blog.domain.*; // for static metamodels
import com.lecg.blog.repository.SiteUserRepository;
import com.lecg.blog.service.dto.SiteUserCriteria;
import com.lecg.blog.service.dto.SiteUserDTO;
import com.lecg.blog.service.mapper.SiteUserMapper;

/**
 * Service for executing complex queries for {@link SiteUser} entities in the database.
 * The main input is a {@link SiteUserCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SiteUserDTO} or a {@link Page} of {@link SiteUserDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SiteUserQueryService extends QueryService<SiteUser> {

    private final Logger log = LoggerFactory.getLogger(SiteUserQueryService.class);

    private final SiteUserRepository siteUserRepository;

    private final SiteUserMapper siteUserMapper;

    public SiteUserQueryService(SiteUserRepository siteUserRepository, SiteUserMapper siteUserMapper) {
        this.siteUserRepository = siteUserRepository;
        this.siteUserMapper = siteUserMapper;
    }

    /**
     * Return a {@link List} of {@link SiteUserDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SiteUserDTO> findByCriteria(SiteUserCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SiteUser> specification = createSpecification(criteria);
        return siteUserMapper.toDto(siteUserRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SiteUserDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SiteUserDTO> findByCriteria(SiteUserCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SiteUser> specification = createSpecification(criteria);
        return siteUserRepository.findAll(specification, page)
            .map(siteUserMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SiteUserCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SiteUser> specification = createSpecification(criteria);
        return siteUserRepository.count(specification);
    }

    /**
     * Function to convert {@link SiteUserCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SiteUser> createSpecification(SiteUserCriteria criteria) {
        Specification<SiteUser> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SiteUser_.id));
            }
            if (criteria.getDisplayName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDisplayName(), SiteUser_.displayName));
            }
            if (criteria.getModerator() != null) {
                specification = specification.and(buildSpecification(criteria.getModerator(), SiteUser_.moderator));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(SiteUser_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getBlogId() != null) {
                specification = specification.and(buildSpecification(criteria.getBlogId(),
                    root -> root.join(SiteUser_.blogs, JoinType.LEFT).get(Blog_.id)));
            }
        }
        return specification;
    }
}
