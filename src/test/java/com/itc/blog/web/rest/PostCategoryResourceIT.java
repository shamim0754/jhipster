package com.itc.blog.web.rest;

import com.itc.blog.BlogApp;
import com.itc.blog.domain.PostCategory;
import com.itc.blog.domain.Post;
import com.itc.blog.repository.PostCategoryRepository;
import com.itc.blog.service.PostCategoryService;
import com.itc.blog.service.dto.PostCategoryDTO;
import com.itc.blog.service.mapper.PostCategoryMapper;
import com.itc.blog.web.rest.errors.ExceptionTranslator;
import com.itc.blog.service.dto.PostCategoryCriteria;
import com.itc.blog.service.PostCategoryQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.itc.blog.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link PostCategoryResource} REST controller.
 */
@SpringBootTest(classes = BlogApp.class)
public class PostCategoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private PostCategoryRepository postCategoryRepository;

    @Autowired
    private PostCategoryMapper postCategoryMapper;

    @Autowired
    private PostCategoryService postCategoryService;

    @Autowired
    private PostCategoryQueryService postCategoryQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restPostCategoryMockMvc;

    private PostCategory postCategory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PostCategoryResource postCategoryResource = new PostCategoryResource(postCategoryService, postCategoryQueryService);
        this.restPostCategoryMockMvc = MockMvcBuilders.standaloneSetup(postCategoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostCategory createEntity(EntityManager em) {
        PostCategory postCategory = new PostCategory()
            .name(DEFAULT_NAME);
        return postCategory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostCategory createUpdatedEntity(EntityManager em) {
        PostCategory postCategory = new PostCategory()
            .name(UPDATED_NAME);
        return postCategory;
    }

    @BeforeEach
    public void initTest() {
        postCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createPostCategory() throws Exception {
        int databaseSizeBeforeCreate = postCategoryRepository.findAll().size();

        // Create the PostCategory
        PostCategoryDTO postCategoryDTO = postCategoryMapper.toDto(postCategory);
        restPostCategoryMockMvc.perform(post("/api/post-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(postCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the PostCategory in the database
        List<PostCategory> postCategoryList = postCategoryRepository.findAll();
        assertThat(postCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        PostCategory testPostCategory = postCategoryList.get(postCategoryList.size() - 1);
        assertThat(testPostCategory.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createPostCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = postCategoryRepository.findAll().size();

        // Create the PostCategory with an existing ID
        postCategory.setId(1L);
        PostCategoryDTO postCategoryDTO = postCategoryMapper.toDto(postCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostCategoryMockMvc.perform(post("/api/post-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(postCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PostCategory in the database
        List<PostCategory> postCategoryList = postCategoryRepository.findAll();
        assertThat(postCategoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPostCategories() throws Exception {
        // Initialize the database
        postCategoryRepository.saveAndFlush(postCategory);

        // Get all the postCategoryList
        restPostCategoryMockMvc.perform(get("/api/post-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getPostCategory() throws Exception {
        // Initialize the database
        postCategoryRepository.saveAndFlush(postCategory);

        // Get the postCategory
        restPostCategoryMockMvc.perform(get("/api/post-categories/{id}", postCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(postCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAllPostCategoriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        postCategoryRepository.saveAndFlush(postCategory);

        // Get all the postCategoryList where name equals to DEFAULT_NAME
        defaultPostCategoryShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the postCategoryList where name equals to UPDATED_NAME
        defaultPostCategoryShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPostCategoriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        postCategoryRepository.saveAndFlush(postCategory);

        // Get all the postCategoryList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPostCategoryShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the postCategoryList where name equals to UPDATED_NAME
        defaultPostCategoryShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPostCategoriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        postCategoryRepository.saveAndFlush(postCategory);

        // Get all the postCategoryList where name is not null
        defaultPostCategoryShouldBeFound("name.specified=true");

        // Get all the postCategoryList where name is null
        defaultPostCategoryShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostCategoriesByAaIsEqualToSomething() throws Exception {
        // Initialize the database
        Post aa = PostResourceIT.createEntity(em);
        em.persist(aa);
        em.flush();
        postCategory.addAa(aa);
        postCategoryRepository.saveAndFlush(postCategory);
        Long aaId = aa.getId();

        // Get all the postCategoryList where aa equals to aaId
        defaultPostCategoryShouldBeFound("aaId.equals=" + aaId);

        // Get all the postCategoryList where aa equals to aaId + 1
        defaultPostCategoryShouldNotBeFound("aaId.equals=" + (aaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPostCategoryShouldBeFound(String filter) throws Exception {
        restPostCategoryMockMvc.perform(get("/api/post-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restPostCategoryMockMvc.perform(get("/api/post-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPostCategoryShouldNotBeFound(String filter) throws Exception {
        restPostCategoryMockMvc.perform(get("/api/post-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPostCategoryMockMvc.perform(get("/api/post-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPostCategory() throws Exception {
        // Get the postCategory
        restPostCategoryMockMvc.perform(get("/api/post-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePostCategory() throws Exception {
        // Initialize the database
        postCategoryRepository.saveAndFlush(postCategory);

        int databaseSizeBeforeUpdate = postCategoryRepository.findAll().size();

        // Update the postCategory
        PostCategory updatedPostCategory = postCategoryRepository.findById(postCategory.getId()).get();
        // Disconnect from session so that the updates on updatedPostCategory are not directly saved in db
        em.detach(updatedPostCategory);
        updatedPostCategory
            .name(UPDATED_NAME);
        PostCategoryDTO postCategoryDTO = postCategoryMapper.toDto(updatedPostCategory);

        restPostCategoryMockMvc.perform(put("/api/post-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(postCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the PostCategory in the database
        List<PostCategory> postCategoryList = postCategoryRepository.findAll();
        assertThat(postCategoryList).hasSize(databaseSizeBeforeUpdate);
        PostCategory testPostCategory = postCategoryList.get(postCategoryList.size() - 1);
        assertThat(testPostCategory.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPostCategory() throws Exception {
        int databaseSizeBeforeUpdate = postCategoryRepository.findAll().size();

        // Create the PostCategory
        PostCategoryDTO postCategoryDTO = postCategoryMapper.toDto(postCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostCategoryMockMvc.perform(put("/api/post-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(postCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PostCategory in the database
        List<PostCategory> postCategoryList = postCategoryRepository.findAll();
        assertThat(postCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePostCategory() throws Exception {
        // Initialize the database
        postCategoryRepository.saveAndFlush(postCategory);

        int databaseSizeBeforeDelete = postCategoryRepository.findAll().size();

        // Delete the postCategory
        restPostCategoryMockMvc.perform(delete("/api/post-categories/{id}", postCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PostCategory> postCategoryList = postCategoryRepository.findAll();
        assertThat(postCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostCategory.class);
        PostCategory postCategory1 = new PostCategory();
        postCategory1.setId(1L);
        PostCategory postCategory2 = new PostCategory();
        postCategory2.setId(postCategory1.getId());
        assertThat(postCategory1).isEqualTo(postCategory2);
        postCategory2.setId(2L);
        assertThat(postCategory1).isNotEqualTo(postCategory2);
        postCategory1.setId(null);
        assertThat(postCategory1).isNotEqualTo(postCategory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostCategoryDTO.class);
        PostCategoryDTO postCategoryDTO1 = new PostCategoryDTO();
        postCategoryDTO1.setId(1L);
        PostCategoryDTO postCategoryDTO2 = new PostCategoryDTO();
        assertThat(postCategoryDTO1).isNotEqualTo(postCategoryDTO2);
        postCategoryDTO2.setId(postCategoryDTO1.getId());
        assertThat(postCategoryDTO1).isEqualTo(postCategoryDTO2);
        postCategoryDTO2.setId(2L);
        assertThat(postCategoryDTO1).isNotEqualTo(postCategoryDTO2);
        postCategoryDTO1.setId(null);
        assertThat(postCategoryDTO1).isNotEqualTo(postCategoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(postCategoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(postCategoryMapper.fromId(null)).isNull();
    }
}
