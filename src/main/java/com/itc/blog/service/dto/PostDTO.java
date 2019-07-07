package com.itc.blog.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.itc.blog.domain.Post} entity.
 */
public class PostDTO implements Serializable {

    private Long id;

    private String title;

    private String body;


    private Long bbId;

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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getBbId() {
        return bbId;
    }

    public void setBbId(Long postCategoryId) {
        this.bbId = postCategoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PostDTO postDTO = (PostDTO) o;
        if (postDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), postDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PostDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", body='" + getBody() + "'" +
            ", bb=" + getBbId() +
            "}";
    }
}
