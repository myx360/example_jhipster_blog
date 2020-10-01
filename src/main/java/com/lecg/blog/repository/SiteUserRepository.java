package com.lecg.blog.repository;

import com.lecg.blog.domain.SiteUser;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SiteUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SiteUserRepository extends JpaRepository<SiteUser, Long>, JpaSpecificationExecutor<SiteUser> {
}
