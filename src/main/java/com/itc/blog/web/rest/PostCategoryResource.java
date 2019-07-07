package com.itc.blog.web.rest;

import com.itc.blog.service.PostCategoryService;
import com.itc.blog.web.rest.errors.BadRequestAlertException;
import com.itc.blog.service.dto.PostCategoryDTO;
import com.itc.blog.service.dto.PostCategoryCriteria;
import com.itc.blog.service.PostCategoryQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.itc.blog.domain.PostCategory}.
 */
@RestController
@RequestMapping("/api")
public class PostCategoryResource {

    private final Logger log = LoggerFactory.getLogger(PostCategoryResource.class);

    private static final String ENTITY_NAME = "postCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PostCategoryService postCategoryService;

    private final PostCategoryQueryService postCategoryQueryService;

    public PostCategoryResource(PostCategoryService postCategoryService, PostCategoryQueryService postCategoryQueryService) {
        this.postCategoryService = postCategoryService;
        this.postCategoryQueryService = postCategoryQueryService;
    }

    /**
     * {@code POST  /post-categories} : Create a new postCategory.
     *
     * @param postCategoryDTO the postCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new postCategoryDTO, or with status {@code 400 (Bad Request)} if the postCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/post-categories")
    public ResponseEntity<PostCategoryDTO> createPostCategory(@RequestBody PostCategoryDTO postCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save PostCategory : {}", postCategoryDTO);
        if (postCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new postCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PostCategoryDTO result = postCategoryService.save(postCategoryDTO);
        return ResponseEntity.created(new URI("/api/post-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /post-categories} : Updates an existing postCategory.
     *
     * @param postCategoryDTO the postCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated postCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the postCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the postCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/post-categories")
    public ResponseEntity<PostCategoryDTO> updatePostCategory(@RequestBody PostCategoryDTO postCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update PostCategory : {}", postCategoryDTO);
        if (postCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PostCategoryDTO result = postCategoryService.save(postCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, postCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /post-categories} : get all the postCategories.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of postCategories in body.
     */
    @GetMapping("/post-categories")
    public ResponseEntity<List<PostCategoryDTO>> getAllPostCategories(PostCategoryCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get PostCategories by criteria: {}", criteria);
        Page<PostCategoryDTO> page = postCategoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /post-categories/count} : count all the postCategories.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/post-categories/count")
    public ResponseEntity<Long> countPostCategories(PostCategoryCriteria criteria) {
        log.debug("REST request to count PostCategories by criteria: {}", criteria);
        return ResponseEntity.ok().body(postCategoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /post-categories/:id} : get the "id" postCategory.
     *
     * @param id the id of the postCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the postCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/post-categories/{id}")
    public ResponseEntity<PostCategoryDTO> getPostCategory(@PathVariable Long id) {
        log.debug("REST request to get PostCategory : {}", id);
        Optional<PostCategoryDTO> postCategoryDTO = postCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(postCategoryDTO);
    }

    /**
     * {@code DELETE  /post-categories/:id} : delete the "id" postCategory.
     *
     * @param id the id of the postCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/post-categories/{id}")
    public ResponseEntity<Void> deletePostCategory(@PathVariable Long id) {
        log.debug("REST request to delete PostCategory : {}", id);
        postCategoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
