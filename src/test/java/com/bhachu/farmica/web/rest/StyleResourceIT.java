package com.bhachu.farmica.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bhachu.farmica.IntegrationTest;
import com.bhachu.farmica.domain.Style;
import com.bhachu.farmica.domain.User;
import com.bhachu.farmica.domain.enumeration.Grade;
import com.bhachu.farmica.repository.StyleRepository;
import com.bhachu.farmica.service.StyleService;
import com.bhachu.farmica.service.dto.StyleDTO;
import com.bhachu.farmica.service.mapper.StyleMapper;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link StyleResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class StyleResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Grade DEFAULT_GRADE = Grade.PREMIUM;
    private static final Grade UPDATED_GRADE = Grade.STANDARD;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/styles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StyleRepository styleRepository;

    @Mock
    private StyleRepository styleRepositoryMock;

    @Autowired
    private StyleMapper styleMapper;

    @Mock
    private StyleService styleServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStyleMockMvc;

    private Style style;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Style createEntity(EntityManager em) {
        Style style = new Style().name(DEFAULT_NAME).grade(DEFAULT_GRADE).code(DEFAULT_CODE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        style.setUser(user);
        return style;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Style createUpdatedEntity(EntityManager em) {
        Style style = new Style().name(UPDATED_NAME).grade(UPDATED_GRADE).code(UPDATED_CODE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        style.setUser(user);
        return style;
    }

    @BeforeEach
    public void initTest() {
        style = createEntity(em);
    }

    @Test
    @Transactional
    void createStyle() throws Exception {
        int databaseSizeBeforeCreate = styleRepository.findAll().size();
        // Create the Style
        StyleDTO styleDTO = styleMapper.toDto(style);
        restStyleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(styleDTO)))
            .andExpect(status().isCreated());

        // Validate the Style in the database
        List<Style> styleList = styleRepository.findAll();
        assertThat(styleList).hasSize(databaseSizeBeforeCreate + 1);
        Style testStyle = styleList.get(styleList.size() - 1);
        assertThat(testStyle.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStyle.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testStyle.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    void createStyleWithExistingId() throws Exception {
        // Create the Style with an existing ID
        style.setId(1L);
        StyleDTO styleDTO = styleMapper.toDto(style);

        int databaseSizeBeforeCreate = styleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStyleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(styleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Style in the database
        List<Style> styleList = styleRepository.findAll();
        assertThat(styleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = styleRepository.findAll().size();
        // set the field null
        style.setName(null);

        // Create the Style, which fails.
        StyleDTO styleDTO = styleMapper.toDto(style);

        restStyleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(styleDTO)))
            .andExpect(status().isBadRequest());

        List<Style> styleList = styleRepository.findAll();
        assertThat(styleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGradeIsRequired() throws Exception {
        int databaseSizeBeforeTest = styleRepository.findAll().size();
        // set the field null
        style.setGrade(null);

        // Create the Style, which fails.
        StyleDTO styleDTO = styleMapper.toDto(style);

        restStyleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(styleDTO)))
            .andExpect(status().isBadRequest());

        List<Style> styleList = styleRepository.findAll();
        assertThat(styleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = styleRepository.findAll().size();
        // set the field null
        style.setCode(null);

        // Create the Style, which fails.
        StyleDTO styleDTO = styleMapper.toDto(style);

        restStyleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(styleDTO)))
            .andExpect(status().isBadRequest());

        List<Style> styleList = styleRepository.findAll();
        assertThat(styleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStyles() throws Exception {
        // Initialize the database
        styleRepository.saveAndFlush(style);

        // Get all the styleList
        restStyleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(style.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllStylesWithEagerRelationshipsIsEnabled() throws Exception {
        when(styleServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restStyleMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(styleServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllStylesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(styleServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restStyleMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(styleRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getStyle() throws Exception {
        // Initialize the database
        styleRepository.saveAndFlush(style);

        // Get the style
        restStyleMockMvc
            .perform(get(ENTITY_API_URL_ID, style.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(style.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }

    @Test
    @Transactional
    void getNonExistingStyle() throws Exception {
        // Get the style
        restStyleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStyle() throws Exception {
        // Initialize the database
        styleRepository.saveAndFlush(style);

        int databaseSizeBeforeUpdate = styleRepository.findAll().size();

        // Update the style
        Style updatedStyle = styleRepository.findById(style.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedStyle are not directly saved in db
        em.detach(updatedStyle);
        updatedStyle.name(UPDATED_NAME).grade(UPDATED_GRADE).code(UPDATED_CODE);
        StyleDTO styleDTO = styleMapper.toDto(updatedStyle);

        restStyleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, styleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(styleDTO))
            )
            .andExpect(status().isOk());

        // Validate the Style in the database
        List<Style> styleList = styleRepository.findAll();
        assertThat(styleList).hasSize(databaseSizeBeforeUpdate);
        Style testStyle = styleList.get(styleList.size() - 1);
        assertThat(testStyle.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStyle.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testStyle.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void putNonExistingStyle() throws Exception {
        int databaseSizeBeforeUpdate = styleRepository.findAll().size();
        style.setId(count.incrementAndGet());

        // Create the Style
        StyleDTO styleDTO = styleMapper.toDto(style);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStyleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, styleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(styleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Style in the database
        List<Style> styleList = styleRepository.findAll();
        assertThat(styleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStyle() throws Exception {
        int databaseSizeBeforeUpdate = styleRepository.findAll().size();
        style.setId(count.incrementAndGet());

        // Create the Style
        StyleDTO styleDTO = styleMapper.toDto(style);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStyleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(styleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Style in the database
        List<Style> styleList = styleRepository.findAll();
        assertThat(styleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStyle() throws Exception {
        int databaseSizeBeforeUpdate = styleRepository.findAll().size();
        style.setId(count.incrementAndGet());

        // Create the Style
        StyleDTO styleDTO = styleMapper.toDto(style);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStyleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(styleDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Style in the database
        List<Style> styleList = styleRepository.findAll();
        assertThat(styleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStyleWithPatch() throws Exception {
        // Initialize the database
        styleRepository.saveAndFlush(style);

        int databaseSizeBeforeUpdate = styleRepository.findAll().size();

        // Update the style using partial update
        Style partialUpdatedStyle = new Style();
        partialUpdatedStyle.setId(style.getId());

        partialUpdatedStyle.name(UPDATED_NAME);

        restStyleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStyle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStyle))
            )
            .andExpect(status().isOk());

        // Validate the Style in the database
        List<Style> styleList = styleRepository.findAll();
        assertThat(styleList).hasSize(databaseSizeBeforeUpdate);
        Style testStyle = styleList.get(styleList.size() - 1);
        assertThat(testStyle.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStyle.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testStyle.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    void fullUpdateStyleWithPatch() throws Exception {
        // Initialize the database
        styleRepository.saveAndFlush(style);

        int databaseSizeBeforeUpdate = styleRepository.findAll().size();

        // Update the style using partial update
        Style partialUpdatedStyle = new Style();
        partialUpdatedStyle.setId(style.getId());

        partialUpdatedStyle.name(UPDATED_NAME).grade(UPDATED_GRADE).code(UPDATED_CODE);

        restStyleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStyle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStyle))
            )
            .andExpect(status().isOk());

        // Validate the Style in the database
        List<Style> styleList = styleRepository.findAll();
        assertThat(styleList).hasSize(databaseSizeBeforeUpdate);
        Style testStyle = styleList.get(styleList.size() - 1);
        assertThat(testStyle.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStyle.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testStyle.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingStyle() throws Exception {
        int databaseSizeBeforeUpdate = styleRepository.findAll().size();
        style.setId(count.incrementAndGet());

        // Create the Style
        StyleDTO styleDTO = styleMapper.toDto(style);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStyleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, styleDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(styleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Style in the database
        List<Style> styleList = styleRepository.findAll();
        assertThat(styleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStyle() throws Exception {
        int databaseSizeBeforeUpdate = styleRepository.findAll().size();
        style.setId(count.incrementAndGet());

        // Create the Style
        StyleDTO styleDTO = styleMapper.toDto(style);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStyleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(styleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Style in the database
        List<Style> styleList = styleRepository.findAll();
        assertThat(styleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStyle() throws Exception {
        int databaseSizeBeforeUpdate = styleRepository.findAll().size();
        style.setId(count.incrementAndGet());

        // Create the Style
        StyleDTO styleDTO = styleMapper.toDto(style);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStyleMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(styleDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Style in the database
        List<Style> styleList = styleRepository.findAll();
        assertThat(styleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStyle() throws Exception {
        // Initialize the database
        styleRepository.saveAndFlush(style);

        int databaseSizeBeforeDelete = styleRepository.findAll().size();

        // Delete the style
        restStyleMockMvc
            .perform(delete(ENTITY_API_URL_ID, style.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Style> styleList = styleRepository.findAll();
        assertThat(styleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
