package com.lecg.blog.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.lecg.blog.domain.enumeration.PostType;

/**
 * A Post.
 */
@Entity
@Table(name = "post")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PostType type;

    @NotNull
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "pinned")
    private Boolean pinned;

    @Column(name = "event_time")
    private LocalDate eventTime;

    @OneToMany(mappedBy = "post")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Subject> tags = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "posts", allowSetters = true)
    private Blog blog;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Post title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public Post date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public PostType getType() {
        return type;
    }

    public Post type(PostType type) {
        this.type = type;
        return this;
    }

    public void setType(PostType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public Post content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean isPinned() {
        return pinned;
    }

    public Post pinned(Boolean pinned) {
        this.pinned = pinned;
        return this;
    }

    public void setPinned(Boolean pinned) {
        this.pinned = pinned;
    }

    public LocalDate getEventTime() {
        return eventTime;
    }

    public Post eventTime(LocalDate eventTime) {
        this.eventTime = eventTime;
        return this;
    }

    public void setEventTime(LocalDate eventTime) {
        this.eventTime = eventTime;
    }

    public Set<Subject> getTags() {
        return tags;
    }

    public Post tags(Set<Subject> subjects) {
        this.tags = subjects;
        return this;
    }

    public Post addTag(Subject subject) {
        this.tags.add(subject);
        subject.setPost(this);
        return this;
    }

    public Post removeTag(Subject subject) {
        this.tags.remove(subject);
        subject.setPost(null);
        return this;
    }

    public void setTags(Set<Subject> subjects) {
        this.tags = subjects;
    }

    public Blog getBlog() {
        return blog;
    }

    public Post blog(Blog blog) {
        this.blog = blog;
        return this;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Post)) {
            return false;
        }
        return id != null && id.equals(((Post) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Post{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", date='" + getDate() + "'" +
            ", type='" + getType() + "'" +
            ", content='" + getContent() + "'" +
            ", pinned='" + isPinned() + "'" +
            ", eventTime='" + getEventTime() + "'" +
            "}";
    }
}
