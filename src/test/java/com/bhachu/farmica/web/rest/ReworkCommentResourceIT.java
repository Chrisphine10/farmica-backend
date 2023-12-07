package com.bhachu.farmica.web.rest;

import static com.bhachu.farmica.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bhachu.farmica.IntegrationTest;
import com.bhachu.farmica.domain.ReworkComment;
import com.bhachu.farmica.domain.ReworkDetail;
import com.bhachu.farmica.domain.User;
import com.bhachu.farmica.repository.ReworkCommentRepository;
import com.bhachu.farmica.service.ReworkCommentService;
import com.bhachu.farmica.service.dto.ReworkCommentDTO;
import com.bhachu.farmica.service.mapper.ReworkCommentMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
 * Integration tests for the {@link ReworkCommentResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ReworkCommentResourceIT {

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/rework-comments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ReworkCommentRepository reworkCommentRepository;

    @Mock
    private ReworkCommentRepository reworkCommentRepositoryMock;

    @Autowired
    private ReworkCommentMapper reworkCommentMapper;

    @Mock
    private ReworkCommentService reworkCommentServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReworkCommentMockMvc;

    private ReworkComment reworkComment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReworkComment createEntity(EntityManager em) {
        ReworkComment reworkComment = new ReworkComment().comment(DEFAULT_COMMENT).createdAt(DEFAULT_CREATED_AT);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        reworkComment.setUser(user);
        // Add required entity
        ReworkDetail reworkDetail;
        if (TestUtil.findAll(em, ReworkDetail.class).isEmpty()) {
            reworkDetail = ReworkDetailResourceIT.createEntity(em);
            em.persist(reworkDetail);
            em.flush();
        } else {
            reworkDetail = TestUtil.findAll(em, ReworkDetail.class).get(0);
        }
        reworkComment.setReworkDetail(reworkDetail);
        return reworkComment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReworkComment createUpdatedEntity(EntityManager em) {
        ReworkComment reworkComment = new ReworkComment().comment(UPDATED_COMMENT).createdAt(UPDATED_CREATED_AT);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        reworkComment.setUser(user);
        // Add required entity
        ReworkDetail reworkDetail;
        if (TestUtil.findAll(em, ReworkDetail.class).isEmpty()) {
            reworkDetail = ReworkDetailResourceIT.createUpdatedEntity(em);
            em.persist(reworkDetail);
            em.flush();
        } else {
            reworkDetail = TestUtil.findAll(em, ReworkDetail.class).get(0);
        }
        reworkComment.setReworkDetail(reworkDetail);
        return reworkComment;
    }

    @BeforeEach
    public void initTest() {
        reworkComment = createEntity(em);
    }

    @Test
    @Transactional
    void createReworkComment() throws Exception {
        int databaseSizeBeforeCreate = reworkCommentRepository.findAll().size();
        // Create the ReworkComment
        ReworkCommentDTO reworkCommentDTO = reworkCommentMapper.toDto(reworkComment);
        restReworkCommentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reworkCommentDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ReworkComment in the database
        List<ReworkComment> reworkCommentList = reworkCommentRepository.findAll();
        assertThat(reworkCommentList).hasSize(databaseSizeBeforeCreate + 1);
        ReworkComment testReworkComment = reworkCommentList.get(reworkCommentList.size() - 1);
        assertThat(testReworkComment.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testReworkComment.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    void createReworkCommentWithExistingId() throws Exception {
        // Create the ReworkComment with an existing ID
        reworkComment.setId(1L);
        ReworkCommentDTO reworkCommentDTO = reworkCommentMapper.toDto(reworkComment);

        int databaseSizeBeforeCreate = reworkCommentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReworkCommentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reworkCommentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReworkComment in the database
        List<ReworkComment> reworkCommentList = reworkCommentRepository.findAll();
        assertThat(reworkCommentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = reworkCommentRepository.findAll().size();
        // set the field null
        reworkComment.setCreatedAt(null);

        // Create the ReworkComment, which fails.
        ReworkCommentDTO reworkCommentDTO = reworkCommentMapper.toDto(reworkComment);

        restReworkCommentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reworkCommentDTO))
            )
            .andExpect(status().isBadRequest());

        List<ReworkComment> reworkCommentList = reworkCommentRepository.findAll();
        assertThat(reworkCommentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllReworkComments() throws Exception {
        // Initialize the database
        reworkCommentRepository.saveAndFlush(reworkComment);

        // Get all the reworkCommentList
        restReworkCommentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reworkComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllReworkCommentsWithEagerRelationshipsIsEnabled() throws Exception {
        when(reworkCommentServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restReworkCommentMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(reworkCommentServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllReworkCommentsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(reworkCommentServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restReworkCommentMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(reworkCommentRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getReworkComment() throws Exception {
        // Initialize the database
        reworkCommentRepository.saveAndFlush(reworkComment);

        // Get the reworkComment
        restReworkCommentMockMvc
            .perform(get(ENTITY_API_URL_ID, reworkComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reworkComment.getId().intValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)));
    }

    @Test
    @Transactional
    void getNonExistingReworkComment() throws Exception {
        // Get the reworkComment
        restReworkCommentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingReworkComment() throws Exception {
        // Initialize the database
        reworkCommentRepository.saveAndFlush(reworkComment);

        int databaseSizeBeforeUpdate = reworkCommentRepository.findAll().size();

        // Update the reworkComment
        ReworkComment updatedReworkComment = reworkCommentRepository.findById(reworkComment.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedReworkComment are not directly saved in db
        em.detach(updatedReworkComment);
        updatedReworkComment.comment(UPDATED_COMMENT).createdAt(UPDATED_CREATED_AT);
        ReworkCommentDTO reworkCommentDTO = reworkCommentMapper.toDto(updatedReworkComment);

        restReworkCommentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reworkCommentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reworkCommentDTO))
            )
            .andExpect(status().isOk());

        // Validate the ReworkComment in the database
        List<ReworkComment> reworkCommentList = reworkCommentRepository.findAll();
        assertThat(reworkCommentList).hasSize(databaseSizeBeforeUpdate);
        ReworkComment testReworkComment = reworkCommentList.get(reworkCommentList.size() - 1);
        assertThat(testReworkComment.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testReworkComment.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingReworkComment() throws Exception {
        int databaseSizeBeforeUpdate = reworkCommentRepository.findAll().size();
        reworkComment.setId(count.incrementAndGet());

        // Create the ReworkComment
        ReworkCommentDTO reworkCommentDTO = reworkCommentMapper.toDto(reworkComment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReworkCommentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reworkCommentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reworkCommentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReworkComment in the database
        List<ReworkComment> reworkCommentList = reworkCommentRepository.findAll();
        assertThat(reworkCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReworkComment() throws Exception {
        int databaseSizeBeforeUpdate = reworkCommentRepository.findAll().size();
        reworkComment.setId(count.incrementAndGet());

        // Create the ReworkComment
        ReworkCommentDTO reworkCommentDTO = reworkCommentMapper.toDto(reworkComment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReworkCommentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reworkCommentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReworkComment in the database
        List<ReworkComment> reworkCommentList = reworkCommentRepository.findAll();
        assertThat(reworkCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReworkComment() throws Exception {
        int databaseSizeBeforeUpdate = reworkCommentRepository.findAll().size();
        reworkComment.setId(count.incrementAndGet());

        // Create the ReworkComment
        ReworkCommentDTO reworkCommentDTO = reworkCommentMapper.toDto(reworkComment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReworkCommentMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reworkCommentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReworkComment in the database
        List<ReworkComment> reworkCommentList = reworkCommentRepository.findAll();
        assertThat(reworkCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReworkCommentWithPatch() throws Exception {
        // Initialize the database
        reworkCommentRepository.saveAndFlush(reworkComment);

        int databaseSizeBeforeUpdate = reworkCommentRepository.findAll().size();

        // Update the reworkComment using partial update
        ReworkComment partialUpdatedReworkComment = new ReworkComment();
        partialUpdatedReworkComment.setId(reworkComment.getId());

        partialUpdatedReworkComment.createdAt(UPDATED_CREATED_AT);

        restReworkCommentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReworkComment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReworkComment))
            )
            .andExpect(status().isOk());

        // Validate the ReworkComment in the database
        List<ReworkComment> reworkCommentList = reworkCommentRepository.findAll();
        assertThat(reworkCommentList).hasSize(databaseSizeBeforeUpdate);
        ReworkComment testReworkComment = reworkCommentList.get(reworkCommentList.size() - 1);
        assertThat(testReworkComment.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testReworkComment.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateReworkCommentWithPatch() throws Exception {
        // Initialize the database
        reworkCommentRepository.saveAndFlush(reworkComment);

        int databaseSizeBeforeUpdate = reworkCommentRepository.findAll().size();

        // Update the reworkComment using partial update
        ReworkComment partialUpdatedReworkComment = new ReworkComment();
        partialUpdatedReworkComment.setId(reworkComment.getId());

        partialUpdatedReworkComment.comment(UPDATED_COMMENT).createdAt(UPDATED_CREATED_AT);

        restReworkCommentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReworkComment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReworkComment))
            )
            .andExpect(status().isOk());

        // Validate the ReworkComment in the database
        List<ReworkComment> reworkCommentList = reworkCommentRepository.findAll();
        assertThat(reworkCommentList).hasSize(databaseSizeBeforeUpdate);
        ReworkComment testReworkComment = reworkCommentList.get(reworkCommentList.size() - 1);
        assertThat(testReworkComment.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testReworkComment.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingReworkComment() throws Exception {
        int databaseSizeBeforeUpdate = reworkCommentRepository.findAll().size();
        reworkComment.setId(count.incrementAndGet());

        // Create the ReworkComment
        ReworkCommentDTO reworkCommentDTO = reworkCommentMapper.toDto(reworkComment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReworkCommentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, reworkCommentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reworkCommentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReworkComment in the database
        List<ReworkComment> reworkCommentList = reworkCommentRepository.findAll();
        assertThat(reworkCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReworkComment() throws Exception {
        int databaseSizeBeforeUpdate = reworkCommentRepository.findAll().size();
        reworkComment.setId(count.incrementAndGet());

        // Create the ReworkComment
        ReworkCommentDTO reworkCommentDTO = reworkCommentMapper.toDto(reworkComment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReworkCommentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reworkCommentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReworkComment in the database
        List<ReworkComment> reworkCommentList = reworkCommentRepository.findAll();
        assertThat(reworkCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReworkComment() throws Exception {
        int databaseSizeBeforeUpdate = reworkCommentRepository.findAll().size();
        reworkComment.setId(count.incrementAndGet());

        // Create the ReworkComment
        ReworkCommentDTO reworkCommentDTO = reworkCommentMapper.toDto(reworkComment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReworkCommentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reworkCommentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReworkComment in the database
        List<ReworkComment> reworkCommentList = reworkCommentRepository.findAll();
        assertThat(reworkCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReworkComment() throws Exception {
        // Initialize the database
        reworkCommentRepository.saveAndFlush(reworkComment);

        int databaseSizeBeforeDelete = reworkCommentRepository.findAll().size();

        // Delete the reworkComment
        restReworkCommentMockMvc
            .perform(delete(ENTITY_API_URL_ID, reworkComment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReworkComment> reworkCommentList = reworkCommentRepository.findAll();
        assertThat(reworkCommentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
