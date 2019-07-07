package com.itc.blog.service.mapper;

import com.itc.blog.domain.*;
import com.itc.blog.service.dto.PostDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Post} and its DTO {@link PostDTO}.
 */
@Mapper(componentModel = "spring", uses = {PostCategoryMapper.class})
public interface PostMapper extends EntityMapper<PostDTO, Post> {

    @Mapping(source = "bb.id", target = "bbId")
    PostDTO toDto(Post post);

    @Mapping(source = "bbId", target = "bb")
    Post toEntity(PostDTO postDTO);

    default Post fromId(Long id) {
        if (id == null) {
            return null;
        }
        Post post = new Post();
        post.setId(id);
        return post;
    }
}
