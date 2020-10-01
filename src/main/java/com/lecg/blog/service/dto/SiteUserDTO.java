package com.lecg.blog.service.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.lecg.blog.domain.SiteUser} entity.
 */
public class SiteUserDTO implements Serializable {

    private Long id;

    @NotNull
    private String displayName;

    private Boolean moderator;


    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Boolean isModerator() {
        return moderator;
    }

    public void setModerator(Boolean moderator) {
        this.moderator = moderator;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SiteUserDTO that = (SiteUserDTO) o;

        return new EqualsBuilder()
            .append(id, that.id)
            .append(displayName, that.displayName)
            .append(moderator, that.moderator)
            .append(userId, that.userId)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(displayName)
            .append(moderator)
            .append(userId)
            .toHashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SiteUserDTO{" +
            "id=" + getId() +
            ", displayName='" + getDisplayName() + "'" +
            ", moderator='" + isModerator() + "'" +
            ", userId=" + getUserId() +
            "}";
    }
}
