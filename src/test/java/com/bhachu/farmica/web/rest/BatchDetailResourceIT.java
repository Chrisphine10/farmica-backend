package com.bhachu.farmica.web.rest;

import static com.bhachu.farmica.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bhachu.farmica.IntegrationTest;
import com.bhachu.farmica.domain.BatchDetail;
import com.bhachu.farmica.domain.Region;
import com.bhachu.farmica.domain.User;
import com.bhachu.farmica.repository.BatchDetailRepository;
import com.bhachu.farmica.service.BatchDetailService;
import com.bhachu.farmica.service.dto.BatchDetailDTO;
import com.bhachu.farmica.service.mapper.BatchDetailMapper;
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
 * Integration tests for the {@link BatchDetailResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class BatchDetailResourceIT {

    private static final String DEFAULT_BATCH_NO = "AAAAAAAAAA";
    private static final String UPDATED_BATCH_NO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_DRIER = 1;
    private static final Integer UPDATED_DRIER = 2;

    private static final String ENTITY_API_URL = "/api/batch-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BatchDetailRepository batchDetailRepository;

    @Mock
    private BatchDetailRepository batchDetailRepositoryMock;

    @Autowired
    private BatchDetailMapper batchDetailMapper;

    @Mock
    private BatchDetailService batchDetailServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBatchDetailMockMvc;

    private BatchDetail batchDetail;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BatchDetail createEntity(EntityManager em) {
        BatchDetail batchDetail = new BatchDetail().batchNo(DEFAULT_BATCH_NO).createdAt(DEFAULT_CREATED_AT).drier(DEFAULT_DRIER);
        // Add required entity
        Region region;
        if (TestUtil.findAll(em, Region.class).isEmpty()) {
            region = RegionResourceIT.createEntity(em);
            em.persist(region);
            em.flush();
        } else {
            region = TestUtil.findAll(em, Region.class).get(0);
        }
        batchDetail.setRegion(region);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        batchDetail.setUser(user);
        return batchDetail;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BatchDetail createUpdatedEntity(EntityManager em) {
        BatchDetail batchDetail = new BatchDetail().batchNo(UPDATED_BATCH_NO).createdAt(UPDATED_CREATED_AT).drier(UPDATED_DRIER);
        // Add required entity
        Region region;
        if (TestUtil.findAll(em, Region.class).isEmpty()) {
            region = RegionResourceIT.createUpdatedEntity(em);
            em.persist(region);
            em.flush();
        } else {
            region = TestUtil.findAll(em, Region.class).get(0);
        }
        batchDetail.setRegion(region);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        batchDetail.setUser(user);
        return batchDetail;
    }

    @BeforeEach
    public void initTest() {
        batchDetail = createEntity(em);
    }

    @Test
    @Transactional
    void createBatchDetail() throws Exception {
        int databaseSizeBeforeCreate = batchDetailRepository.findAll().size();
        // Create the BatchDetail
        BatchDetailDTO batchDetailDTO = batchDetailMapper.toDto(batchDetail);
        restBatchDetailMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(batchDetailDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BatchDetail in the database
        List<BatchDetail> batchDetailList = batchDetailRepository.findAll();
        assertThat(batchDetailList).hasSize(databaseSizeBeforeCreate + 1);
        BatchDetail testBatchDetail = batchDetailList.get(batchDetailList.size() - 1);
        assertThat(testBatchDetail.getBatchNo()).isEqualTo(DEFAULT_BATCH_NO);
        assertThat(testBatchDetail.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testBatchDetail.getDrier()).isEqualTo(DEFAULT_DRIER);
    }

    @Test
    @Transactional
    void createBatchDetailWithExistingId() throws Exception {
        // Create the BatchDetail with an existing ID
        batchDetail.setId(1L);
        BatchDetailDTO batchDetailDTO = batchDetailMapper.toDto(batchDetail);

        int databaseSizeBeforeCreate = batchDetailRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBatchDetailMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(batchDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BatchDetail in the database
        List<BatchDetail> batchDetailList = batchDetailRepository.findAll();
        assertThat(batchDetailList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkBatchNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = batchDetailRepository.findAll().size();
        // set the field null
        batchDetail.setBatchNo(null);

        // Create the BatchDetail, which fails.
        BatchDetailDTO batchDetailDTO = batchDetailMapper.toDto(batchDetail);

        restBatchDetailMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(batchDetailDTO))
            )
            .andExpect(status().isBadRequest());

        List<BatchDetail> batchDetailList = batchDetailRepository.findAll();
        assertThat(batchDetailList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = batchDetailRepository.findAll().size();
        // set the field null
        batchDetail.setCreatedAt(null);

        // Create the BatchDetail, which fails.
        BatchDetailDTO batchDetailDTO = batchDetailMapper.toDto(batchDetail);

        restBatchDetailMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(batchDetailDTO))
            )
            .andExpect(status().isBadRequest());

        List<BatchDetail> batchDetailList = batchDetailRepository.findAll();
        assertThat(batchDetailList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBatchDetails() throws Exception {
        // Initialize the database
        batchDetailRepository.saveAndFlush(batchDetail);

        // Get all the batchDetailList
        restBatchDetailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(batchDetail.getId().intValue())))
            .andExpect(jsonPath("$.[*].batchNo").value(hasItem(DEFAULT_BATCH_NO)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].drier").value(hasItem(DEFAULT_DRIER)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBatchDetailsWithEagerRelationshipsIsEnabled() throws Exception {
        when(batchDetailServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBatchDetailMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(batchDetailServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBatchDetailsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(batchDetailServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBatchDetailMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(batchDetailRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getBatchDetail() throws Exception {
        // Initialize the database
        batchDetailRepository.saveAndFlush(batchDetail);

        // Get the batchDetail
        restBatchDetailMockMvc
            .perform(get(ENTITY_API_URL_ID, batchDetail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(batchDetail.getId().intValue()))
            .andExpect(jsonPath("$.batchNo").value(DEFAULT_BATCH_NO))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.drier").value(DEFAULT_DRIER));
    }

    @Test
    @Transactional
    void getNonExistingBatchDetail() throws Exception {
        // Get the batchDetail
        restBatchDetailMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBatchDetail() throws Exception {
        // Initialize the database
        batchDetailRepository.saveAndFlush(batchDetail);

        int databaseSizeBeforeUpdate = batchDetailRepository.findAll().size();

        // Update the batchDetail
        BatchDetail updatedBatchDetail = batchDetailRepository.findById(batchDetail.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBatchDetail are not directly saved in db
        em.detach(updatedBatchDetail);
        updatedBatchDetail.batchNo(UPDATED_BATCH_NO).createdAt(UPDATED_CREATED_AT).drier(UPDATED_DRIER);
        BatchDetailDTO batchDetailDTO = batchDetailMapper.toDto(updatedBatchDetail);

        restBatchDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, batchDetailDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(batchDetailDTO))
            )
            .andExpect(status().isOk());

        // Validate the BatchDetail in the database
        List<BatchDetail> batchDetailList = batchDetailRepository.findAll();
        assertThat(batchDetailList).hasSize(databaseSizeBeforeUpdate);
        BatchDetail testBatchDetail = batchDetailList.get(batchDetailList.size() - 1);
        assertThat(testBatchDetail.getBatchNo()).isEqualTo(UPDATED_BATCH_NO);
        assertThat(testBatchDetail.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testBatchDetail.getDrier()).isEqualTo(UPDATED_DRIER);
    }

    @Test
    @Transactional
    void putNonExistingBatchDetail() throws Exception {
        int databaseSizeBeforeUpdate = batchDetailRepository.findAll().size();
        batchDetail.setId(count.incrementAndGet());

        // Create the BatchDetail
        BatchDetailDTO batchDetailDTO = batchDetailMapper.toDto(batchDetail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBatchDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, batchDetailDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(batchDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BatchDetail in the database
        List<BatchDetail> batchDetailList = batchDetailRepository.findAll();
        assertThat(batchDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBatchDetail() throws Exception {
        int databaseSizeBeforeUpdate = batchDetailRepository.findAll().size();
        batchDetail.setId(count.incrementAndGet());

        // Create the BatchDetail
        BatchDetailDTO batchDetailDTO = batchDetailMapper.toDto(batchDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBatchDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(batchDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BatchDetail in the database
        List<BatchDetail> batchDetailList = batchDetailRepository.findAll();
        assertThat(batchDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBatchDetail() throws Exception {
        int databaseSizeBeforeUpdate = batchDetailRepository.findAll().size();
        batchDetail.setId(count.incrementAndGet());

        // Create the BatchDetail
        BatchDetailDTO batchDetailDTO = batchDetailMapper.toDto(batchDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBatchDetailMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(batchDetailDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BatchDetail in the database
        List<BatchDetail> batchDetailList = batchDetailRepository.findAll();
        assertThat(batchDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBatchDetailWithPatch() throws Exception {
        // Initialize the database
        batchDetailRepository.saveAndFlush(batchDetail);

        int databaseSizeBeforeUpdate = batchDetailRepository.findAll().size();

        // Update the batchDetail using partial update
        BatchDetail partialUpdatedBatchDetail = new BatchDetail();
        partialUpdatedBatchDetail.setId(batchDetail.getId());

        partialUpdatedBatchDetail.createdAt(UPDATED_CREATED_AT).drier(UPDATED_DRIER);

        restBatchDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBatchDetail.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBatchDetail))
            )
            .andExpect(status().isOk());

        // Validate the BatchDetail in the database
        List<BatchDetail> batchDetailList = batchDetailRepository.findAll();
        assertThat(batchDetailList).hasSize(databaseSizeBeforeUpdate);
        BatchDetail testBatchDetail = batchDetailList.get(batchDetailList.size() - 1);
        assertThat(testBatchDetail.getBatchNo()).isEqualTo(DEFAULT_BATCH_NO);
        assertThat(testBatchDetail.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testBatchDetail.getDrier()).isEqualTo(UPDATED_DRIER);
    }

    @Test
    @Transactional
    void fullUpdateBatchDetailWithPatch() throws Exception {
        // Initialize the database
        batchDetailRepository.saveAndFlush(batchDetail);

        int databaseSizeBeforeUpdate = batchDetailRepository.findAll().size();

        // Update the batchDetail using partial update
        BatchDetail partialUpdatedBatchDetail = new BatchDetail();
        partialUpdatedBatchDetail.setId(batchDetail.getId());

        partialUpdatedBatchDetail.batchNo(UPDATED_BATCH_NO).createdAt(UPDATED_CREATED_AT).drier(UPDATED_DRIER);

        restBatchDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBatchDetail.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBatchDetail))
            )
            .andExpect(status().isOk());

        // Validate the BatchDetail in the database
        List<BatchDetail> batchDetailList = batchDetailRepository.findAll();
        assertThat(batchDetailList).hasSize(databaseSizeBeforeUpdate);
        BatchDetail testBatchDetail = batchDetailList.get(batchDetailList.size() - 1);
        assertThat(testBatchDetail.getBatchNo()).isEqualTo(UPDATED_BATCH_NO);
        assertThat(testBatchDetail.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testBatchDetail.getDrier()).isEqualTo(UPDATED_DRIER);
    }

    @Test
    @Transactional
    void patchNonExistingBatchDetail() throws Exception {
        int databaseSizeBeforeUpdate = batchDetailRepository.findAll().size();
        batchDetail.setId(count.incrementAndGet());

        // Create the BatchDetail
        BatchDetailDTO batchDetailDTO = batchDetailMapper.toDto(batchDetail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBatchDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, batchDetailDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(batchDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BatchDetail in the database
        List<BatchDetail> batchDetailList = batchDetailRepository.findAll();
        assertThat(batchDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBatchDetail() throws Exception {
        int databaseSizeBeforeUpdate = batchDetailRepository.findAll().size();
        batchDetail.setId(count.incrementAndGet());

        // Create the BatchDetail
        BatchDetailDTO batchDetailDTO = batchDetailMapper.toDto(batchDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBatchDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(batchDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BatchDetail in the database
        List<BatchDetail> batchDetailList = batchDetailRepository.findAll();
        assertThat(batchDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBatchDetail() throws Exception {
        int databaseSizeBeforeUpdate = batchDetailRepository.findAll().size();
        batchDetail.setId(count.incrementAndGet());

        // Create the BatchDetail
        BatchDetailDTO batchDetailDTO = batchDetailMapper.toDto(batchDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBatchDetailMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(batchDetailDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BatchDetail in the database
        List<BatchDetail> batchDetailList = batchDetailRepository.findAll();
        assertThat(batchDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBatchDetail() throws Exception {
        // Initialize the database
        batchDetailRepository.saveAndFlush(batchDetail);

        int databaseSizeBeforeDelete = batchDetailRepository.findAll().size();

        // Delete the batchDetail
        restBatchDetailMockMvc
            .perform(delete(ENTITY_API_URL_ID, batchDetail.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BatchDetail> batchDetailList = batchDetailRepository.findAll();
        assertThat(batchDetailList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
