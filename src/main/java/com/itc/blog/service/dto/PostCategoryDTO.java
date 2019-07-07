package com.itc.blog.service.dto;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.itc.blog.domain.PostCategory} entity.
 */
@ApiModel(description = "@author Md.shamim Miah Date : 12-07-2019")
public class PostCategoryDTO implements Serializable {

    private Long id;

    private String name;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PostCategoryDTO postCategoryDTO = (PostCategoryDTO) o;
        if (postCategoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), postCategoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PostCategoryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
