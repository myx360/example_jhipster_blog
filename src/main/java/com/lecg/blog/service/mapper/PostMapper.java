package com.lecg.blog.service.mapper;


import com.lecg.blog.domain.*;
import com.lecg.blog.service.dto.PostDTO;

import com.lecg.blog.service.dto.PostSummaryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Post} and its DTO {@link PostDTO}.
 */
@Mapper(componentModel = "spring", uses = {BlogMapper.class, SubjectMapper.class, SiteUserMapper.class})
public interface PostMapper extends EntityMapper<PostDTO, Post> {

    @Mapping(source = "blog.id", target = "blogId")
    @Mapping(source = "blog.user.displayName", target = "author")
    PostDTO toDto(Post post);

    @Mapping(source = "blog.user.displayName", target = "author")
    PostSummaryDTO toSummaryDto(Post post);

    @Mapping(target = "removeSubject", ignore = true)
    @Mapping(source = "blogId", target = "blog")
    Post toEntity(PostDTO postDTO);

    default Post fromId(Long id) {
        if (id == null) {
            return null;
        }
        Post post = new Post();
        post.setId(id);
        return post;
    }
}
