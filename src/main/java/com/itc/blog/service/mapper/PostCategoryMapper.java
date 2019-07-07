package com.itc.blog.service.mapper;

import com.itc.blog.domain.*;
import com.itc.blog.service.dto.PostCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PostCategory} and its DTO {@link PostCategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PostCategoryMapper extends EntityMapper<PostCategoryDTO, PostCategory> {


    @Mapping(target = "aas", ignore = true)
    @Mapping(target = "removeAa", ignore = true)
    PostCategory toEntity(PostCategoryDTO postCategoryDTO);

    default PostCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        PostCategory postCategory = new PostCategory();
        postCategory.setId(id);
        return postCategory;
    }
}
