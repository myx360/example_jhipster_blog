package com.lecg.blog.service.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link com.lecg.blog.domain.Blog} entity.
 */
public class BlogDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3)
    private String name;

    private List<SubjectDTO> subjects;

    public List<SubjectDTO> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectDTO> subjects) {
        this.subjects = subjects;
    }

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long siteUserId) {
        this.userId = siteUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        BlogDTO blogDTO = (BlogDTO) o;

        return new EqualsBuilder()
            .append(id, blogDTO.id)
            .append(name, blogDTO.name)
            .append(subjects, blogDTO.subjects)
            .append(userId, blogDTO.userId)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(name)
            .append(subjects)
            .append(userId)
            .toHashCode();
    }

    @Override
    public String toString() {
        return "BlogDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", subjects=" + subjects +
            ", userId=" + userId +
            '}';
    }
}
