package com.bhachu.farmica.web.rest;

import static com.bhachu.farmica.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bhachu.farmica.IntegrationTest;
import com.bhachu.farmica.domain.Comment;
import com.bhachu.farmica.domain.User;
import com.bhachu.farmica.domain.enumeration.CurrentZone;
import com.bhachu.farmica.repository.CommentRepository;
import com.bhachu.farmica.service.CommentService;
import com.bhachu.farmica.service.dto.CommentDTO;
import com.bhachu.farmica.service.mapper.CommentMapper;
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
 * Integration tests for the {@link CommentResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CommentResourceIT {

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final CurrentZone DEFAULT_STATUS = CurrentZone.PACKING;
    private static final CurrentZone UPDATED_STATUS = CurrentZone.REWORK;

    private static final Integer DEFAULT_ZONE_ID = 1;
    private static final Integer UPDATED_ZONE_ID = 2;

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/comments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CommentRepository commentRepository;

    @Mock
    private CommentRepository commentRepositoryMock;

    @Autowired
    private CommentMapper commentMapper;

    @Mock
    private CommentService commentServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommentMockMvc;

    private Comment comment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comment createEntity(EntityManager em) {
        Comment comment = new Comment()
            .comment(DEFAULT_COMMENT)
            .status(DEFAULT_STATUS)
            .zoneId(DEFAULT_ZONE_ID)
            .createdAt(DEFAULT_CREATED_AT);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        comment.setUser(user);
        return comment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comment createUpdatedEntity(EntityManager em) {
        Comment comment = new Comment()
            .comment(UPDATED_COMMENT)
            .status(UPDATED_STATUS)
            .zoneId(UPDATED_ZONE_ID)
            .createdAt(UPDATED_CREATED_AT);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        comment.setUser(user);
        return comment;
    }

    @BeforeEach
    public void initTest() {
        comment = createEntity(em);
    }

    @Test
    @Transactional
    void createComment() throws Exception {
        int databaseSizeBeforeCreate = commentRepository.findAll().size();
        // Create the Comment
        CommentDTO commentDTO = commentMapper.toDto(comment);
        restCommentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commentDTO)))
            .andExpect(status().isCreated());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeCreate + 1);
        Comment testComment = commentList.get(commentList.size() - 1);
        assertThat(testComment.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testComment.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testComment.getZoneId()).isEqualTo(DEFAULT_ZONE_ID);
        assertThat(testComment.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    void createCommentWithExistingId() throws Exception {
        // Create the Comment with an existing ID
        comment.setId(1L);
        CommentDTO commentDTO = commentMapper.toDto(comment);

        int databaseSizeBeforeCreate = commentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = commentRepository.findAll().size();
        // set the field null
        comment.setCreatedAt(null);

        // Create the Comment, which fails.
        CommentDTO commentDTO = commentMapper.toDto(comment);

        restCommentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commentDTO)))
            .andExpect(status().isBadRequest());

        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllComments() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList
        restCommentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comment.getId().intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].zoneId").value(hasItem(DEFAULT_ZONE_ID)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCommentsWithEagerRelationshipsIsEnabled() throws Exception {
        when(commentServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCommentMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(commentServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCommentsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(commentServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCommentMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(commentRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getComment() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get the comment
        restCommentMockMvc
            .perform(get(ENTITY_API_URL_ID, comment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(comment.getId().intValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.zoneId").value(DEFAULT_ZONE_ID))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)));
    }

    @Test
    @Transactional
    void getNonExistingComment() throws Exception {
        // Get the comment
        restCommentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingComment() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        int databaseSizeBeforeUpdate = commentRepository.findAll().size();

        // Update the comment
        Comment updatedComment = commentRepository.findById(comment.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedComment are not directly saved in db
        em.detach(updatedComment);
        updatedComment.comment(UPDATED_COMMENT).status(UPDATED_STATUS).zoneId(UPDATED_ZONE_ID).createdAt(UPDATED_CREATED_AT);
        CommentDTO commentDTO = commentMapper.toDto(updatedComment);

        restCommentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, commentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commentDTO))
            )
            .andExpect(status().isOk());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeUpdate);
        Comment testComment = commentList.get(commentList.size() - 1);
        assertThat(testComment.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testComment.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testComment.getZoneId()).isEqualTo(UPDATED_ZONE_ID);
        assertThat(testComment.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingComment() throws Exception {
        int databaseSizeBeforeUpdate = commentRepository.findAll().size();
        comment.setId(count.incrementAndGet());

        // Create the Comment
        CommentDTO commentDTO = commentMapper.toDto(comment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, commentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchComment() throws Exception {
        int databaseSizeBeforeUpdate = commentRepository.findAll().size();
        comment.setId(count.incrementAndGet());

        // Create the Comment
        CommentDTO commentDTO = commentMapper.toDto(comment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamComment() throws Exception {
        int databaseSizeBeforeUpdate = commentRepository.findAll().size();
        comment.setId(count.incrementAndGet());

        // Create the Comment
        CommentDTO commentDTO = commentMapper.toDto(comment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCommentWithPatch() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        int databaseSizeBeforeUpdate = commentRepository.findAll().size();

        // Update the comment using partial update
        Comment partialUpdatedComment = new Comment();
        partialUpdatedComment.setId(comment.getId());

        partialUpdatedComment.createdAt(UPDATED_CREATED_AT);

        restCommentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedComment))
            )
            .andExpect(status().isOk());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeUpdate);
        Comment testComment = commentList.get(commentList.size() - 1);
        assertThat(testComment.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testComment.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testComment.getZoneId()).isEqualTo(DEFAULT_ZONE_ID);
        assertThat(testComment.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateCommentWithPatch() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        int databaseSizeBeforeUpdate = commentRepository.findAll().size();

        // Update the comment using partial update
        Comment partialUpdatedComment = new Comment();
        partialUpdatedComment.setId(comment.getId());

        partialUpdatedComment.comment(UPDATED_COMMENT).status(UPDATED_STATUS).zoneId(UPDATED_ZONE_ID).createdAt(UPDATED_CREATED_AT);

        restCommentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedComment))
            )
            .andExpect(status().isOk());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeUpdate);
        Comment testComment = commentList.get(commentList.size() - 1);
        assertThat(testComment.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testComment.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testComment.getZoneId()).isEqualTo(UPDATED_ZONE_ID);
        assertThat(testComment.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingComment() throws Exception {
        int databaseSizeBeforeUpdate = commentRepository.findAll().size();
        comment.setId(count.incrementAndGet());

        // Create the Comment
        CommentDTO commentDTO = commentMapper.toDto(comment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, commentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(commentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchComment() throws Exception {
        int databaseSizeBeforeUpdate = commentRepository.findAll().size();
        comment.setId(count.incrementAndGet());

        // Create the Comment
        CommentDTO commentDTO = commentMapper.toDto(comment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(commentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamComment() throws Exception {
        int databaseSizeBeforeUpdate = commentRepository.findAll().size();
        comment.setId(count.incrementAndGet());

        // Create the Comment
        CommentDTO commentDTO = commentMapper.toDto(comment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommentMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(commentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteComment() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        int databaseSizeBeforeDelete = commentRepository.findAll().size();

        // Delete the comment
        restCommentMockMvc
            .perform(delete(ENTITY_API_URL_ID, comment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
