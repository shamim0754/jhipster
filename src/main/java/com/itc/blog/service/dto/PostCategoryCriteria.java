package com.itc.blog.service.dto;

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
 * Criteria class for the {@link com.itc.blog.domain.PostCategory} entity. This class is used
 * in {@link com.itc.blog.web.rest.PostCategoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /post-categories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PostCategoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter aaId;

    public PostCategoryCriteria(){
    }

    public PostCategoryCriteria(PostCategoryCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.aaId = other.aaId == null ? null : other.aaId.copy();
    }

    @Override
    public PostCategoryCriteria copy() {
        return new PostCategoryCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public LongFilter getAaId() {
        return aaId;
    }

    public void setAaId(LongFilter aaId) {
        this.aaId = aaId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PostCategoryCriteria that = (PostCategoryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(aaId, that.aaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        aaId
        );
    }

    @Override
    public String toString() {
        return "PostCategoryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (aaId != null ? "aaId=" + aaId + ", " : "") +
            "}";
    }

}
