package com.lecg.blog.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.lecg.blog.domain.enumeration.PostType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.lecg.blog.domain.Post} entity. This class is used
 * in {@link com.lecg.blog.web.rest.PostResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /posts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PostCriteria implements Serializable, Criteria {
    /**
     * Class for filtering PostType
     */
    public static class PostTypeFilter extends Filter<PostType> {

        public PostTypeFilter() {
        }

        public PostTypeFilter(PostTypeFilter filter) {
            super(filter);
        }

        @Override
        public PostTypeFilter copy() {
            return new PostTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private LocalDateFilter date;

    private PostTypeFilter type;

    private StringFilter content;

    private BooleanFilter pinned;

    private LocalDateFilter eventTime;

    private LongFilter subjectId;

    private LongFilter blogId;

    public PostCriteria() {
    }

    public PostCriteria(PostCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.content = other.content == null ? null : other.content.copy();
        this.pinned = other.pinned == null ? null : other.pinned.copy();
        this.eventTime = other.eventTime == null ? null : other.eventTime.copy();
        this.subjectId = other.subjectId == null ? null : other.subjectId.copy();
        this.blogId = other.blogId == null ? null : other.blogId.copy();
    }

    @Override
    public PostCriteria copy() {
        return new PostCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public LocalDateFilter getDate() {
        return date;
    }

    public void setDate(LocalDateFilter date) {
        this.date = date;
    }

    public PostTypeFilter getType() {
        return type;
    }

    public void setType(PostTypeFilter type) {
        this.type = type;
    }

    public StringFilter getContent() {
        return content;
    }

    public void setContent(StringFilter content) {
        this.content = content;
    }

    public BooleanFilter getPinned() {
        return pinned;
    }

    public void setPinned(BooleanFilter pinned) {
        this.pinned = pinned;
    }

    public LocalDateFilter getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalDateFilter eventTime) {
        this.eventTime = eventTime;
    }

    public LongFilter getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(LongFilter subjectId) {
        this.subjectId = subjectId;
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
        final PostCriteria that = (PostCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(date, that.date) &&
            Objects.equals(type, that.type) &&
            Objects.equals(content, that.content) &&
            Objects.equals(pinned, that.pinned) &&
            Objects.equals(eventTime, that.eventTime) &&
            Objects.equals(subjectId, that.subjectId) &&
            Objects.equals(blogId, that.blogId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        title,
        date,
        type,
        content,
        pinned,
        eventTime,
        subjectId,
        blogId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (content != null ? "content=" + content + ", " : "") +
                (pinned != null ? "pinned=" + pinned + ", " : "") +
                (eventTime != null ? "eventTime=" + eventTime + ", " : "") +
                (subjectId != null ? "subjectId=" + subjectId + ", " : "") +
                (blogId != null ? "blogId=" + blogId + ", " : "") +
            "}";
    }

}
