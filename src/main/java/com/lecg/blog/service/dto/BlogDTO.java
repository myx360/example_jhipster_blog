package com.lecg.blog.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.lecg.blog.domain.Blog} entity.
 */
public class BlogDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(min = 3)
    private String name;


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
        if (this == o) {
            return true;
        }
        if (!(o instanceof BlogDTO)) {
            return false;
        }

        return id != null && id.equals(((BlogDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BlogDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", userId=" + getUserId() +
            "}";
    }
}
