package com.lecg.blog.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A SiteUser.
 */
@Entity
@Table(name = "site_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SiteUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "display_name", nullable = false, unique = true)
    private String displayName;

    @Column(name = "moderator")
    private Boolean moderator;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Blog> blogs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public SiteUser displayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Boolean isModerator() {
        return moderator;
    }

    public SiteUser moderator(Boolean moderator) {
        this.moderator = moderator;
        return this;
    }

    public void setModerator(Boolean moderator) {
        this.moderator = moderator;
    }

    public User getUser() {
        return user;
    }

    public SiteUser user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Blog> getBlogs() {
        return blogs;
    }

    public SiteUser blogs(Set<Blog> blogs) {
        this.blogs = blogs;
        return this;
    }

    public SiteUser addBlog(Blog blog) {
        this.blogs.add(blog);
        blog.setUser(this);
        return this;
    }

    public SiteUser removeBlog(Blog blog) {
        this.blogs.remove(blog);
        blog.setUser(null);
        return this;
    }

    public void setBlogs(Set<Blog> blogs) {
        this.blogs = blogs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SiteUser)) {
            return false;
        }
        return id != null && id.equals(((SiteUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SiteUser{" +
            "id=" + getId() +
            ", displayName='" + getDisplayName() + "'" +
            ", moderator='" + isModerator() + "'" +
            "}";
    }
}
