package com.lecg.blog.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.lecg.blog.domain.SiteUser} entity. This class is used
 * in {@link com.lecg.blog.web.rest.SiteUserResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /site-users?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SiteUserCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter displayName;

    private BooleanFilter moderator;

    private LongFilter userId;

    private LongFilter blogId;

    public SiteUserCriteria() {
    }

    public SiteUserCriteria(SiteUserCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.displayName = other.displayName == null ? null : other.displayName.copy();
        this.moderator = other.moderator == null ? null : other.moderator.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.blogId = other.blogId == null ? null : other.blogId.copy();
    }

    @Override
    public SiteUserCriteria copy() {
        return new SiteUserCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDisplayName() {
        return displayName;
    }

    public void setDisplayName(StringFilter displayName) {
        this.displayName = displayName;
    }

    public BooleanFilter getModerator() {
        return moderator;
    }

    public void setModerator(BooleanFilter moderator) {
        this.moderator = moderator;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getBlogId() {
        return blogId;
    }

    public void setBlogId(LongFilter blogId) {
        this.blogId = blogId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SiteUserCriteria that = (SiteUserCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(displayName, that.displayName) &&
            Objects.equals(moderator, that.moderator) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(blogId, that.blogId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        displayName,
        moderator,
        userId,
        blogId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SiteUserCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (displayName != null ? "displayName=" + displayName + ", " : "") +
                (moderator != null ? "moderator=" + moderator + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (blogId != null ? "blogId=" + blogId + ", " : "") +
            "}";
    }

}
