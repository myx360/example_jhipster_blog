package com.lecg.blog.service.mapper;


import com.lecg.blog.domain.*;
import com.lecg.blog.service.dto.SubjectDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Subject} and its DTO {@link SubjectDTO}.
 */
@Mapper(componentModel = "spring", uses = {PostMapper.class, BlogMapper.class})
public interface SubjectMapper extends EntityMapper<SubjectDTO, Subject> {

    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "blog.id", target = "blogId")
    SubjectDTO toDto(Subject subject);

    @Mapping(source = "postId", target = "post")
    @Mapping(source = "blogId", target = "blog")
    Subject toEntity(SubjectDTO subjectDTO);

    default Subject fromId(Long id) {
        if (id == null) {
            return null;
        }
        Subject subject = new Subject();
        subject.setId(id);
        return subject;
    }
}
