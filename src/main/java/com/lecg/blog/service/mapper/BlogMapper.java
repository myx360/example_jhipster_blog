package com.lecg.blog.service.mapper;


import com.lecg.blog.domain.*;
import com.lecg.blog.service.dto.BlogDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Blog} and its DTO {@link BlogDTO}.
 */
@Mapper(componentModel = "spring", uses = {SiteUserMapper.class})
public interface BlogMapper extends EntityMapper<BlogDTO, Blog> {

    @Mapping(source = "user.id", target = "userId")
    BlogDTO toDto(Blog blog);

    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "removePost", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "removeTag", ignore = true)
    @Mapping(source = "userId", target = "user")
    Blog toEntity(BlogDTO blogDTO);

    default Blog fromId(Long id) {
        if (id == null) {
            return null;
        }
        Blog blog = new Blog();
        blog.setId(id);
        return blog;
    }
}
