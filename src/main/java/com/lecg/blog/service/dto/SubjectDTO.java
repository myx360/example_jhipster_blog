package com.lecg.blog.service.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.lecg.blog.domain.Subject} entity.
 */
public class SubjectDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 2)
    private String name;


    private Long postId;

    private Long blogId;

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

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SubjectDTO that = (SubjectDTO) o;

        return new EqualsBuilder()
            .append(id, that.id)
            .append(name, that.name)
            .append(postId, that.postId)
            .append(blogId, that.blogId)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(name)
            .append(postId)
            .append(blogId)
            .toHashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubjectDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", postId=" + getPostId() +
            ", blogId=" + getBlogId() +
            "}";
    }
}
