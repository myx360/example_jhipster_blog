package com.lecg.blog.service;

import com.lecg.blog.domain.SiteUser;
import com.lecg.blog.repository.SiteUserRepository;
import com.lecg.blog.service.dto.SiteUserDTO;
import com.lecg.blog.service.mapper.SiteUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SiteUser}.
 */
@Service
@Transactional
public class SiteUserService {

    private final Logger log = LoggerFactory.getLogger(SiteUserService.class);

    private final SiteUserRepository siteUserRepository;

    private final SiteUserMapper siteUserMapper;

    public SiteUserService(SiteUserRepository siteUserRepository, SiteUserMapper siteUserMapper) {
        this.siteUserRepository = siteUserRepository;
        this.siteUserMapper = siteUserMapper;
    }

    /**
     * Save a siteUser.
     *
     * @param siteUserDTO the entity to save.
     * @return the persisted entity.
     */
    public SiteUserDTO save(SiteUserDTO siteUserDTO) {
        log.debug("Request to save SiteUser : {}", siteUserDTO);
        SiteUser siteUser = siteUserMapper.toEntity(siteUserDTO);
        siteUser = siteUserRepository.save(siteUser);
        return siteUserMapper.toDto(siteUser);
    }

    /**
     * Get all the siteUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SiteUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SiteUsers");
        return siteUserRepository.findAll(pageable)
            .map(siteUserMapper::toDto);
    }


    /**
     * Get one siteUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SiteUserDTO> findOne(Long id) {
        log.debug("Request to get SiteUser : {}", id);
        return siteUserRepository.findById(id)
            .map(siteUserMapper::toDto);
    }

    /**
     * Delete the siteUser by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SiteUser : {}", id);
        siteUserRepository.deleteById(id);
    }
}
