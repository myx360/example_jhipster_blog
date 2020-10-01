package com.lecg.blog.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import com.lecg.blog.domain.enumeration.PostType;

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

    private Boolean pinned;

    private LocalDate eventTime;


    private Long blogId;
    
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostDTO)) {
            return false;
        }

        return id != null && id.equals(((PostDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", date='" + getDate() + "'" +
            ", type='" + getType() + "'" +
            ", content='" + getContent() + "'" +
            ", pinned='" + isPinned() + "'" +
            ", eventTime='" + getEventTime() + "'" +
            ", blogId=" + getBlogId() +
            "}";
    }
}
