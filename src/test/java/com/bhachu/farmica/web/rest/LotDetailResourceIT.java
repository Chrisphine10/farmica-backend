package com.bhachu.farmica.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bhachu.farmica.IntegrationTest;
import com.bhachu.farmica.domain.BatchDetail;
import com.bhachu.farmica.domain.LotDetail;
import com.bhachu.farmica.domain.User;
import com.bhachu.farmica.repository.LotDetailRepository;
import com.bhachu.farmica.service.LotDetailService;
import com.bhachu.farmica.service.dto.LotDetailDTO;
import com.bhachu.farmica.service.mapper.LotDetailMapper;
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
 * Integration tests for the {@link LotDetailResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class LotDetailResourceIT {

    private static final Integer DEFAULT_LOT_NO = 1;
    private static final Integer UPDATED_LOT_NO = 2;

    private static final String ENTITY_API_URL = "/api/lot-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LotDetailRepository lotDetailRepository;

    @Mock
    private LotDetailRepository lotDetailRepositoryMock;

    @Autowired
    private LotDetailMapper lotDetailMapper;

    @Mock
    private LotDetailService lotDetailServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLotDetailMockMvc;

    private LotDetail lotDetail;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LotDetail createEntity(EntityManager em) {
        LotDetail lotDetail = new LotDetail().lotNo(DEFAULT_LOT_NO);
        // Add required entity
        BatchDetail batchDetail;
        if (TestUtil.findAll(em, BatchDetail.class).isEmpty()) {
            batchDetail = BatchDetailResourceIT.createEntity(em);
            em.persist(batchDetail);
            em.flush();
        } else {
            batchDetail = TestUtil.findAll(em, BatchDetail.class).get(0);
        }
        lotDetail.setBatchDetail(batchDetail);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        lotDetail.setUser(user);
        return lotDetail;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LotDetail createUpdatedEntity(EntityManager em) {
        LotDetail lotDetail = new LotDetail().lotNo(UPDATED_LOT_NO);
        // Add required entity
        BatchDetail batchDetail;
        if (TestUtil.findAll(em, BatchDetail.class).isEmpty()) {
            batchDetail = BatchDetailResourceIT.createUpdatedEntity(em);
            em.persist(batchDetail);
            em.flush();
        } else {
            batchDetail = TestUtil.findAll(em, BatchDetail.class).get(0);
        }
        lotDetail.setBatchDetail(batchDetail);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        lotDetail.setUser(user);
        return lotDetail;
    }

    @BeforeEach
    public void initTest() {
        lotDetail = createEntity(em);
    }

    @Test
    @Transactional
    void createLotDetail() throws Exception {
        int databaseSizeBeforeCreate = lotDetailRepository.findAll().size();
        // Create the LotDetail
        LotDetailDTO lotDetailDTO = lotDetailMapper.toDto(lotDetail);
        restLotDetailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lotDetailDTO)))
            .andExpect(status().isCreated());

        // Validate the LotDetail in the database
        List<LotDetail> lotDetailList = lotDetailRepository.findAll();
        assertThat(lotDetailList).hasSize(databaseSizeBeforeCreate + 1);
        LotDetail testLotDetail = lotDetailList.get(lotDetailList.size() - 1);
        assertThat(testLotDetail.getLotNo()).isEqualTo(DEFAULT_LOT_NO);
    }

    @Test
    @Transactional
    void createLotDetailWithExistingId() throws Exception {
        // Create the LotDetail with an existing ID
        lotDetail.setId(1L);
        LotDetailDTO lotDetailDTO = lotDetailMapper.toDto(lotDetail);

        int databaseSizeBeforeCreate = lotDetailRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLotDetailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lotDetailDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LotDetail in the database
        List<LotDetail> lotDetailList = lotDetailRepository.findAll();
        assertThat(lotDetailList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLotNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = lotDetailRepository.findAll().size();
        // set the field null
        lotDetail.setLotNo(null);

        // Create the LotDetail, which fails.
        LotDetailDTO lotDetailDTO = lotDetailMapper.toDto(lotDetail);

        restLotDetailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lotDetailDTO)))
            .andExpect(status().isBadRequest());

        List<LotDetail> lotDetailList = lotDetailRepository.findAll();
        assertThat(lotDetailList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLotDetails() throws Exception {
        // Initialize the database
        lotDetailRepository.saveAndFlush(lotDetail);

        // Get all the lotDetailList
        restLotDetailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lotDetail.getId().intValue())))
            .andExpect(jsonPath("$.[*].lotNo").value(hasItem(DEFAULT_LOT_NO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLotDetailsWithEagerRelationshipsIsEnabled() throws Exception {
        when(lotDetailServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLotDetailMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(lotDetailServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLotDetailsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(lotDetailServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLotDetailMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(lotDetailRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getLotDetail() throws Exception {
        // Initialize the database
        lotDetailRepository.saveAndFlush(lotDetail);

        // Get the lotDetail
        restLotDetailMockMvc
            .perform(get(ENTITY_API_URL_ID, lotDetail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lotDetail.getId().intValue()))
            .andExpect(jsonPath("$.lotNo").value(DEFAULT_LOT_NO));
    }

    @Test
    @Transactional
    void getNonExistingLotDetail() throws Exception {
        // Get the lotDetail
        restLotDetailMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLotDetail() throws Exception {
        // Initialize the database
        lotDetailRepository.saveAndFlush(lotDetail);

        int databaseSizeBeforeUpdate = lotDetailRepository.findAll().size();

        // Update the lotDetail
        LotDetail updatedLotDetail = lotDetailRepository.findById(lotDetail.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLotDetail are not directly saved in db
        em.detach(updatedLotDetail);
        updatedLotDetail.lotNo(UPDATED_LOT_NO);
        LotDetailDTO lotDetailDTO = lotDetailMapper.toDto(updatedLotDetail);

        restLotDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, lotDetailDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lotDetailDTO))
            )
            .andExpect(status().isOk());

        // Validate the LotDetail in the database
        List<LotDetail> lotDetailList = lotDetailRepository.findAll();
        assertThat(lotDetailList).hasSize(databaseSizeBeforeUpdate);
        LotDetail testLotDetail = lotDetailList.get(lotDetailList.size() - 1);
        assertThat(testLotDetail.getLotNo()).isEqualTo(UPDATED_LOT_NO);
    }

    @Test
    @Transactional
    void putNonExistingLotDetail() throws Exception {
        int databaseSizeBeforeUpdate = lotDetailRepository.findAll().size();
        lotDetail.setId(count.incrementAndGet());

        // Create the LotDetail
        LotDetailDTO lotDetailDTO = lotDetailMapper.toDto(lotDetail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLotDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, lotDetailDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lotDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LotDetail in the database
        List<LotDetail> lotDetailList = lotDetailRepository.findAll();
        assertThat(lotDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLotDetail() throws Exception {
        int databaseSizeBeforeUpdate = lotDetailRepository.findAll().size();
        lotDetail.setId(count.incrementAndGet());

        // Create the LotDetail
        LotDetailDTO lotDetailDTO = lotDetailMapper.toDto(lotDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLotDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lotDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LotDetail in the database
        List<LotDetail> lotDetailList = lotDetailRepository.findAll();
        assertThat(lotDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLotDetail() throws Exception {
        int databaseSizeBeforeUpdate = lotDetailRepository.findAll().size();
        lotDetail.setId(count.incrementAndGet());

        // Create the LotDetail
        LotDetailDTO lotDetailDTO = lotDetailMapper.toDto(lotDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLotDetailMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lotDetailDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LotDetail in the database
        List<LotDetail> lotDetailList = lotDetailRepository.findAll();
        assertThat(lotDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLotDetailWithPatch() throws Exception {
        // Initialize the database
        lotDetailRepository.saveAndFlush(lotDetail);

        int databaseSizeBeforeUpdate = lotDetailRepository.findAll().size();

        // Update the lotDetail using partial update
        LotDetail partialUpdatedLotDetail = new LotDetail();
        partialUpdatedLotDetail.setId(lotDetail.getId());

        partialUpdatedLotDetail.lotNo(UPDATED_LOT_NO);

        restLotDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLotDetail.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLotDetail))
            )
            .andExpect(status().isOk());

        // Validate the LotDetail in the database
        List<LotDetail> lotDetailList = lotDetailRepository.findAll();
        assertThat(lotDetailList).hasSize(databaseSizeBeforeUpdate);
        LotDetail testLotDetail = lotDetailList.get(lotDetailList.size() - 1);
        assertThat(testLotDetail.getLotNo()).isEqualTo(UPDATED_LOT_NO);
    }

    @Test
    @Transactional
    void fullUpdateLotDetailWithPatch() throws Exception {
        // Initialize the database
        lotDetailRepository.saveAndFlush(lotDetail);

        int databaseSizeBeforeUpdate = lotDetailRepository.findAll().size();

        // Update the lotDetail using partial update
        LotDetail partialUpdatedLotDetail = new LotDetail();
        partialUpdatedLotDetail.setId(lotDetail.getId());

        partialUpdatedLotDetail.lotNo(UPDATED_LOT_NO);

        restLotDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLotDetail.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLotDetail))
            )
            .andExpect(status().isOk());

        // Validate the LotDetail in the database
        List<LotDetail> lotDetailList = lotDetailRepository.findAll();
        assertThat(lotDetailList).hasSize(databaseSizeBeforeUpdate);
        LotDetail testLotDetail = lotDetailList.get(lotDetailList.size() - 1);
        assertThat(testLotDetail.getLotNo()).isEqualTo(UPDATED_LOT_NO);
    }

    @Test
    @Transactional
    void patchNonExistingLotDetail() throws Exception {
        int databaseSizeBeforeUpdate = lotDetailRepository.findAll().size();
        lotDetail.setId(count.incrementAndGet());

        // Create the LotDetail
        LotDetailDTO lotDetailDTO = lotDetailMapper.toDto(lotDetail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLotDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, lotDetailDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lotDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LotDetail in the database
        List<LotDetail> lotDetailList = lotDetailRepository.findAll();
        assertThat(lotDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLotDetail() throws Exception {
        int databaseSizeBeforeUpdate = lotDetailRepository.findAll().size();
        lotDetail.setId(count.incrementAndGet());

        // Create the LotDetail
        LotDetailDTO lotDetailDTO = lotDetailMapper.toDto(lotDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLotDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lotDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LotDetail in the database
        List<LotDetail> lotDetailList = lotDetailRepository.findAll();
        assertThat(lotDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLotDetail() throws Exception {
        int databaseSizeBeforeUpdate = lotDetailRepository.findAll().size();
        lotDetail.setId(count.incrementAndGet());

        // Create the LotDetail
        LotDetailDTO lotDetailDTO = lotDetailMapper.toDto(lotDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLotDetailMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(lotDetailDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LotDetail in the database
        List<LotDetail> lotDetailList = lotDetailRepository.findAll();
        assertThat(lotDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLotDetail() throws Exception {
        // Initialize the database
        lotDetailRepository.saveAndFlush(lotDetail);

        int databaseSizeBeforeDelete = lotDetailRepository.findAll().size();

        // Delete the lotDetail
        restLotDetailMockMvc
            .perform(delete(ENTITY_API_URL_ID, lotDetail.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LotDetail> lotDetailList = lotDetailRepository.findAll();
        assertThat(lotDetailList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
