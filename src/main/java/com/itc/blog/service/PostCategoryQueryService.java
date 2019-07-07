package com.itc.blog.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.itc.blog.domain.PostCategory;
import com.itc.blog.domain.*; // for static metamodels
import com.itc.blog.repository.PostCategoryRepository;
import com.itc.blog.service.dto.PostCategoryCriteria;
import com.itc.blog.service.dto.PostCategoryDTO;
import com.itc.blog.service.mapper.PostCategoryMapper;

/**
 * Service for executing complex queries for {@link PostCategory} entities in the database.
 * The main input is a {@link PostCategoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PostCategoryDTO} or a {@link Page} of {@link PostCategoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PostCategoryQueryService extends QueryService<PostCategory> {

    private final Logger log = LoggerFactory.getLogger(PostCategoryQueryService.class);

    private final PostCategoryRepository postCategoryRepository;

    private final PostCategoryMapper postCategoryMapper;

    public PostCategoryQueryService(PostCategoryRepository postCategoryRepository, PostCategoryMapper postCategoryMapper) {
        this.postCategoryRepository = postCategoryRepository;
        this.postCategoryMapper = postCategoryMapper;
    }

    /**
     * Return a {@link List} of {@link PostCategoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PostCategoryDTO> findByCriteria(PostCategoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PostCategory> specification = createSpecification(criteria);
        return postCategoryMapper.toDto(postCategoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PostCategoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PostCategoryDTO> findByCriteria(PostCategoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PostCategory> specification = createSpecification(criteria);
        return postCategoryRepository.findAll(specification, page)
            .map(postCategoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PostCategoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PostCategory> specification = createSpecification(criteria);
        return postCategoryRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<PostCategory> createSpecification(PostCategoryCriteria criteria) {
        Specification<PostCategory> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), PostCategory_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), PostCategory_.name));
            }
            if (criteria.getAaId() != null) {
                specification = specification.and(buildSpecification(criteria.getAaId(),
                    root -> root.join(PostCategory_.aas, JoinType.LEFT).get(Post_.id)));
            }
        }
        return specification;
    }
}
