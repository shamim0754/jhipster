package com.itc.blog.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Md.shamim Miah
 * Date : 12-07-2019
 */
@Entity
@Table(name = "post_category")
public class PostCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "bb")
    private Set<Post> aas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public PostCategory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Post> getAas() {
        return aas;
    }

    public PostCategory aas(Set<Post> posts) {
        this.aas = posts;
        return this;
    }

    public PostCategory addAa(Post post) {
        this.aas.add(post);
        post.setBb(this);
        return this;
    }

    public PostCategory removeAa(Post post) {
        this.aas.remove(post);
        post.setBb(null);
        return this;
    }

    public void setAas(Set<Post> posts) {
        this.aas = posts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostCategory)) {
            return false;
        }
        return id != null && id.equals(((PostCategory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PostCategory{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
