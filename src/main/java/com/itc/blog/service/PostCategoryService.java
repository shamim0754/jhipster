package com.itc.blog.service;

import com.itc.blog.domain.PostCategory;
import com.itc.blog.repository.PostCategoryRepository;
import com.itc.blog.service.dto.PostCategoryDTO;
import com.itc.blog.service.mapper.PostCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PostCategory}.
 */
@Service
@Transactional
public class PostCategoryService {

    private final Logger log = LoggerFactory.getLogger(PostCategoryService.class);

    private final PostCategoryRepository postCategoryRepository;

    private final PostCategoryMapper postCategoryMapper;

    public PostCategoryService(PostCategoryRepository postCategoryRepository, PostCategoryMapper postCategoryMapper) {
        this.postCategoryRepository = postCategoryRepository;
        this.postCategoryMapper = postCategoryMapper;
    }

    /**
     * Save a postCategory.
     *
     * @param postCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    public PostCategoryDTO save(PostCategoryDTO postCategoryDTO) {
        log.debug("Request to save PostCategory : {}", postCategoryDTO);
        PostCategory postCategory = postCategoryMapper.toEntity(postCategoryDTO);
        postCategory = postCategoryRepository.save(postCategory);
        return postCategoryMapper.toDto(postCategory);
    }

    /**
     * Get all the postCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PostCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PostCategories");
        return postCategoryRepository.findAll(pageable)
            .map(postCategoryMapper::toDto);
    }


    /**
     * Get one postCategory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PostCategoryDTO> findOne(Long id) {
        log.debug("Request to get PostCategory : {}", id);
        return postCategoryRepository.findById(id)
            .map(postCategoryMapper::toDto);
    }

    /**
     * Delete the postCategory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PostCategory : {}", id);
        postCategoryRepository.deleteById(id);
    }
}
