package com.lecg.blog.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.lecg.blog.domain.enumeration.PostType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * A DTO for the {@link com.lecg.blog.domain.Post} entity.
 */
public class PostDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    @NotNull
    private LocalDate date;

    @NotNull
    private PostType type;

    @NotNull
    private String content;

    private String author;

    private Boolean pinned;

    private LocalDate eventTime;

    private Long blogId;

    private List<SubjectDTO> subjects;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public PostType getType() {
        return type;
    }

    public void setType(PostType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean isPinned() {
        return pinned;
    }

    public void setPinned(Boolean pinned) {
        this.pinned = pinned;
    }

    public LocalDate getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalDate eventTime) {
        this.eventTime = eventTime;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<SubjectDTO> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectDTO> subjects) {
        this.subjects = subjects;
    }

    @Override
    public String toString() {
        return "PostDTO{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", date=" + date +
            ", type=" + type +
            ", content='" + content + '\'' +
            ", author='" + author + '\'' +
            ", pinned=" + pinned +
            ", eventTime=" + eventTime +
            ", blogId=" + blogId +
            ", subjects=" + subjects +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        PostDTO postDTO = (PostDTO) o;

        return new EqualsBuilder()
            .append(id, postDTO.id)
            .append(title, postDTO.title)
            .append(date, postDTO.date)
            .append(type, postDTO.type)
            .append(content, postDTO.content)
            .append(author, postDTO.author)
            .append(pinned, postDTO.pinned)
            .append(eventTime, postDTO.eventTime)
            .append(blogId, postDTO.blogId)
            .append(subjects, postDTO.subjects)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(title)
            .append(date)
            .append(type)
            .append(content)
            .append(author)
            .append(pinned)
            .append(eventTime)
            .append(blogId)
            .append(subjects)
            .toHashCode();
    }
}
