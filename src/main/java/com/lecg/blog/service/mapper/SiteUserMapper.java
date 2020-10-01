package com.lecg.blog.service.mapper;


import com.lecg.blog.domain.*;
import com.lecg.blog.service.dto.SiteUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SiteUser} and its DTO {@link SiteUserDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface SiteUserMapper extends EntityMapper<SiteUserDTO, SiteUser> {

    @Mapping(source = "user.id", target = "userId")
    SiteUserDTO toDto(SiteUser siteUser);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "blogs", ignore = true)
    @Mapping(target = "removeBlog", ignore = true)
    SiteUser toEntity(SiteUserDTO siteUserDTO);

    default SiteUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        SiteUser siteUser = new SiteUser();
        siteUser.setId(id);
        return siteUser;
    }
}
