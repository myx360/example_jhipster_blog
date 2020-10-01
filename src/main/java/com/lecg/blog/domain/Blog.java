package com.lecg.blog.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Blog.
 */
@Entity
@Table(name = "blog")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "blog")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Post> posts = new HashSet<>();

    @OneToMany(mappedBy = "blog")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Subject> tags = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "blogs", allowSetters = true)
    private SiteUser user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Blog name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public Blog posts(Set<Post> posts) {
        this.posts = posts;
        return this;
    }

    public Blog addPost(Post post) {
        this.posts.add(post);
        post.setBlog(this);
        return this;
    }

    public Blog removePost(Post post) {
        this.posts.remove(post);
        post.setBlog(null);
        return this;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public Set<Subject> getTags() {
        return tags;
    }

    public Blog tags(Set<Subject> subjects) {
        this.tags = subjects;
        return this;
    }

    public Blog addTag(Subject subject) {
        this.tags.add(subject);
        subject.setBlog(this);
        return this;
    }

    public Blog removeTag(Subject subject) {
        this.tags.remove(subject);
        subject.setBlog(null);
        return this;
    }

    public void setTags(Set<Subject> subjects) {
        this.tags = subjects;
    }

    public SiteUser getUser() {
        return user;
    }

    public Blog user(SiteUser siteUser) {
        this.user = siteUser;
        return this;
    }

    public void setUser(SiteUser siteUser) {
        this.user = siteUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Blog)) {
            return false;
        }
        return id != null && id.equals(((Blog) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Blog{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
