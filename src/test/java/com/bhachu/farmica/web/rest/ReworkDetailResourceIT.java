package com.bhachu.farmica.web.rest;

import static com.bhachu.farmica.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bhachu.farmica.IntegrationTest;
import com.bhachu.farmica.domain.LotDetail;
import com.bhachu.farmica.domain.ReworkDetail;
import com.bhachu.farmica.domain.User;
import com.bhachu.farmica.domain.WarehouseDetail;
import com.bhachu.farmica.domain.enumeration.ReworkStatus;
import com.bhachu.farmica.repository.ReworkDetailRepository;
import com.bhachu.farmica.service.ReworkDetailService;
import com.bhachu.farmica.service.dto.ReworkDetailDTO;
import com.bhachu.farmica.service.mapper.ReworkDetailMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.LocalDate;
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
 * Integration tests for the {@link ReworkDetailResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ReworkDetailResourceIT {

    private static final String DEFAULT_UICODE = "AAAAAAAAAA";
    private static final String UPDATED_UICODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PDN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PDN_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_REWORK_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REWORK_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_NUMBER_OF_CT_NS = 1;
    private static final Integer UPDATED_NUMBER_OF_CT_NS = 2;

    private static final Integer DEFAULT_START_CTN_NUMBER = 1;
    private static final Integer UPDATED_START_CTN_NUMBER = 2;

    private static final Integer DEFAULT_END_CTN_NUMBER = 1;
    private static final Integer UPDATED_END_CTN_NUMBER = 2;

    private static final ReworkStatus DEFAULT_STATUS = ReworkStatus.PENDING;
    private static final ReworkStatus UPDATED_STATUS = ReworkStatus.COMPLETE;

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/rework-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ReworkDetailRepository reworkDetailRepository;

    @Mock
    private ReworkDetailRepository reworkDetailRepositoryMock;

    @Autowired
    private ReworkDetailMapper reworkDetailMapper;

    @Mock
    private ReworkDetailService reworkDetailServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReworkDetailMockMvc;

    private ReworkDetail reworkDetail;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReworkDetail createEntity(EntityManager em) {
        ReworkDetail reworkDetail = new ReworkDetail()
            .uicode(DEFAULT_UICODE)
            .pdnDate(DEFAULT_PDN_DATE)
            .reworkDate(DEFAULT_REWORK_DATE)
            .numberOfCTNs(DEFAULT_NUMBER_OF_CT_NS)
            .startCTNNumber(DEFAULT_START_CTN_NUMBER)
            .endCTNNumber(DEFAULT_END_CTN_NUMBER)
            .status(DEFAULT_STATUS)
            .createdAt(DEFAULT_CREATED_AT);
        // Add required entity
        WarehouseDetail warehouseDetail;
        if (TestUtil.findAll(em, WarehouseDetail.class).isEmpty()) {
            warehouseDetail = WarehouseDetailResourceIT.createEntity(em);
            em.persist(warehouseDetail);
            em.flush();
        } else {
            warehouseDetail = TestUtil.findAll(em, WarehouseDetail.class).get(0);
        }
        reworkDetail.setWarehouseDetail(warehouseDetail);
        // Add required entity
        LotDetail lotDetail;
        if (TestUtil.findAll(em, LotDetail.class).isEmpty()) {
            lotDetail = LotDetailResourceIT.createEntity(em);
            em.persist(lotDetail);
            em.flush();
        } else {
            lotDetail = TestUtil.findAll(em, LotDetail.class).get(0);
        }
        reworkDetail.setLotDetail(lotDetail);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        reworkDetail.setUser(user);
        return reworkDetail;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReworkDetail createUpdatedEntity(EntityManager em) {
        ReworkDetail reworkDetail = new ReworkDetail()
            .uicode(UPDATED_UICODE)
            .pdnDate(UPDATED_PDN_DATE)
            .reworkDate(UPDATED_REWORK_DATE)
            .numberOfCTNs(UPDATED_NUMBER_OF_CT_NS)
            .startCTNNumber(UPDATED_START_CTN_NUMBER)
            .endCTNNumber(UPDATED_END_CTN_NUMBER)
            .status(UPDATED_STATUS)
            .createdAt(UPDATED_CREATED_AT);
        // Add required entity
        WarehouseDetail warehouseDetail;
        if (TestUtil.findAll(em, WarehouseDetail.class).isEmpty()) {
            warehouseDetail = WarehouseDetailResourceIT.createUpdatedEntity(em);
            em.persist(warehouseDetail);
            em.flush();
        } else {
            warehouseDetail = TestUtil.findAll(em, WarehouseDetail.class).get(0);
        }
        reworkDetail.setWarehouseDetail(warehouseDetail);
        // Add required entity
        LotDetail lotDetail;
        if (TestUtil.findAll(em, LotDetail.class).isEmpty()) {
            lotDetail = LotDetailResourceIT.createUpdatedEntity(em);
            em.persist(lotDetail);
            em.flush();
        } else {
            lotDetail = TestUtil.findAll(em, LotDetail.class).get(0);
        }
        reworkDetail.setLotDetail(lotDetail);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        reworkDetail.setUser(user);
        return reworkDetail;
    }

    @BeforeEach
    public void initTest() {
        reworkDetail = createEntity(em);
    }

    @Test
    @Transactional
    void createReworkDetail() throws Exception {
        int databaseSizeBeforeCreate = reworkDetailRepository.findAll().size();
        // Create the ReworkDetail
        ReworkDetailDTO reworkDetailDTO = reworkDetailMapper.toDto(reworkDetail);
        restReworkDetailMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reworkDetailDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ReworkDetail in the database
        List<ReworkDetail> reworkDetailList = reworkDetailRepository.findAll();
        assertThat(reworkDetailList).hasSize(databaseSizeBeforeCreate + 1);
        ReworkDetail testReworkDetail = reworkDetailList.get(reworkDetailList.size() - 1);
        assertThat(testReworkDetail.getUicode()).isEqualTo(DEFAULT_UICODE);
        assertThat(testReworkDetail.getPdnDate()).isEqualTo(DEFAULT_PDN_DATE);
        assertThat(testReworkDetail.getReworkDate()).isEqualTo(DEFAULT_REWORK_DATE);
        assertThat(testReworkDetail.getNumberOfCTNs()).isEqualTo(DEFAULT_NUMBER_OF_CT_NS);
        assertThat(testReworkDetail.getStartCTNNumber()).isEqualTo(DEFAULT_START_CTN_NUMBER);
        assertThat(testReworkDetail.getEndCTNNumber()).isEqualTo(DEFAULT_END_CTN_NUMBER);
        assertThat(testReworkDetail.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testReworkDetail.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    void createReworkDetailWithExistingId() throws Exception {
        // Create the ReworkDetail with an existing ID
        reworkDetail.setId(1L);
        ReworkDetailDTO reworkDetailDTO = reworkDetailMapper.toDto(reworkDetail);

        int databaseSizeBeforeCreate = reworkDetailRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReworkDetailMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reworkDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReworkDetail in the database
        List<ReworkDetail> reworkDetailList = reworkDetailRepository.findAll();
        assertThat(reworkDetailList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPdnDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = reworkDetailRepository.findAll().size();
        // set the field null
        reworkDetail.setPdnDate(null);

        // Create the ReworkDetail, which fails.
        ReworkDetailDTO reworkDetailDTO = reworkDetailMapper.toDto(reworkDetail);

        restReworkDetailMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reworkDetailDTO))
            )
            .andExpect(status().isBadRequest());

        List<ReworkDetail> reworkDetailList = reworkDetailRepository.findAll();
        assertThat(reworkDetailList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkReworkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = reworkDetailRepository.findAll().size();
        // set the field null
        reworkDetail.setReworkDate(null);

        // Create the ReworkDetail, which fails.
        ReworkDetailDTO reworkDetailDTO = reworkDetailMapper.toDto(reworkDetail);

        restReworkDetailMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reworkDetailDTO))
            )
            .andExpect(status().isBadRequest());

        List<ReworkDetail> reworkDetailList = reworkDetailRepository.findAll();
        assertThat(reworkDetailList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumberOfCTNsIsRequired() throws Exception {
        int databaseSizeBeforeTest = reworkDetailRepository.findAll().size();
        // set the field null
        reworkDetail.setNumberOfCTNs(null);

        // Create the ReworkDetail, which fails.
        ReworkDetailDTO reworkDetailDTO = reworkDetailMapper.toDto(reworkDetail);

        restReworkDetailMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reworkDetailDTO))
            )
            .andExpect(status().isBadRequest());

        List<ReworkDetail> reworkDetailList = reworkDetailRepository.findAll();
        assertThat(reworkDetailList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = reworkDetailRepository.findAll().size();
        // set the field null
        reworkDetail.setStatus(null);

        // Create the ReworkDetail, which fails.
        ReworkDetailDTO reworkDetailDTO = reworkDetailMapper.toDto(reworkDetail);

        restReworkDetailMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reworkDetailDTO))
            )
            .andExpect(status().isBadRequest());

        List<ReworkDetail> reworkDetailList = reworkDetailRepository.findAll();
        assertThat(reworkDetailList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllReworkDetails() throws Exception {
        // Initialize the database
        reworkDetailRepository.saveAndFlush(reworkDetail);

        // Get all the reworkDetailList
        restReworkDetailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reworkDetail.getId().intValue())))
            .andExpect(jsonPath("$.[*].uicode").value(hasItem(DEFAULT_UICODE)))
            .andExpect(jsonPath("$.[*].pdnDate").value(hasItem(DEFAULT_PDN_DATE.toString())))
            .andExpect(jsonPath("$.[*].reworkDate").value(hasItem(DEFAULT_REWORK_DATE.toString())))
            .andExpect(jsonPath("$.[*].numberOfCTNs").value(hasItem(DEFAULT_NUMBER_OF_CT_NS)))
            .andExpect(jsonPath("$.[*].startCTNNumber").value(hasItem(DEFAULT_START_CTN_NUMBER)))
            .andExpect(jsonPath("$.[*].endCTNNumber").value(hasItem(DEFAULT_END_CTN_NUMBER)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllReworkDetailsWithEagerRelationshipsIsEnabled() throws Exception {
        when(reworkDetailServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restReworkDetailMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(reworkDetailServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllReworkDetailsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(reworkDetailServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restReworkDetailMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(reworkDetailRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getReworkDetail() throws Exception {
        // Initialize the database
        reworkDetailRepository.saveAndFlush(reworkDetail);

        // Get the reworkDetail
        restReworkDetailMockMvc
            .perform(get(ENTITY_API_URL_ID, reworkDetail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reworkDetail.getId().intValue()))
            .andExpect(jsonPath("$.uicode").value(DEFAULT_UICODE))
            .andExpect(jsonPath("$.pdnDate").value(DEFAULT_PDN_DATE.toString()))
            .andExpect(jsonPath("$.reworkDate").value(DEFAULT_REWORK_DATE.toString()))
            .andExpect(jsonPath("$.numberOfCTNs").value(DEFAULT_NUMBER_OF_CT_NS))
            .andExpect(jsonPath("$.startCTNNumber").value(DEFAULT_START_CTN_NUMBER))
            .andExpect(jsonPath("$.endCTNNumber").value(DEFAULT_END_CTN_NUMBER))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)));
    }

    @Test
    @Transactional
    void getNonExistingReworkDetail() throws Exception {
        // Get the reworkDetail
        restReworkDetailMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingReworkDetail() throws Exception {
        // Initialize the database
        reworkDetailRepository.saveAndFlush(reworkDetail);

        int databaseSizeBeforeUpdate = reworkDetailRepository.findAll().size();

        // Update the reworkDetail
        ReworkDetail updatedReworkDetail = reworkDetailRepository.findById(reworkDetail.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedReworkDetail are not directly saved in db
        em.detach(updatedReworkDetail);
        updatedReworkDetail
            .uicode(UPDATED_UICODE)
            .pdnDate(UPDATED_PDN_DATE)
            .reworkDate(UPDATED_REWORK_DATE)
            .numberOfCTNs(UPDATED_NUMBER_OF_CT_NS)
            .startCTNNumber(UPDATED_START_CTN_NUMBER)
            .endCTNNumber(UPDATED_END_CTN_NUMBER)
            .status(UPDATED_STATUS)
            .createdAt(UPDATED_CREATED_AT);
        ReworkDetailDTO reworkDetailDTO = reworkDetailMapper.toDto(updatedReworkDetail);

        restReworkDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reworkDetailDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reworkDetailDTO))
            )
            .andExpect(status().isOk());

        // Validate the ReworkDetail in the database
        List<ReworkDetail> reworkDetailList = reworkDetailRepository.findAll();
        assertThat(reworkDetailList).hasSize(databaseSizeBeforeUpdate);
        ReworkDetail testReworkDetail = reworkDetailList.get(reworkDetailList.size() - 1);
        assertThat(testReworkDetail.getUicode()).isEqualTo(UPDATED_UICODE);
        assertThat(testReworkDetail.getPdnDate()).isEqualTo(UPDATED_PDN_DATE);
        assertThat(testReworkDetail.getReworkDate()).isEqualTo(UPDATED_REWORK_DATE);
        assertThat(testReworkDetail.getNumberOfCTNs()).isEqualTo(UPDATED_NUMBER_OF_CT_NS);
        assertThat(testReworkDetail.getStartCTNNumber()).isEqualTo(UPDATED_START_CTN_NUMBER);
        assertThat(testReworkDetail.getEndCTNNumber()).isEqualTo(UPDATED_END_CTN_NUMBER);
        assertThat(testReworkDetail.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testReworkDetail.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingReworkDetail() throws Exception {
        int databaseSizeBeforeUpdate = reworkDetailRepository.findAll().size();
        reworkDetail.setId(count.incrementAndGet());

        // Create the ReworkDetail
        ReworkDetailDTO reworkDetailDTO = reworkDetailMapper.toDto(reworkDetail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReworkDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reworkDetailDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reworkDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReworkDetail in the database
        List<ReworkDetail> reworkDetailList = reworkDetailRepository.findAll();
        assertThat(reworkDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReworkDetail() throws Exception {
        int databaseSizeBeforeUpdate = reworkDetailRepository.findAll().size();
        reworkDetail.setId(count.incrementAndGet());

        // Create the ReworkDetail
        ReworkDetailDTO reworkDetailDTO = reworkDetailMapper.toDto(reworkDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReworkDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reworkDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReworkDetail in the database
        List<ReworkDetail> reworkDetailList = reworkDetailRepository.findAll();
        assertThat(reworkDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReworkDetail() throws Exception {
        int databaseSizeBeforeUpdate = reworkDetailRepository.findAll().size();
        reworkDetail.setId(count.incrementAndGet());

        // Create the ReworkDetail
        ReworkDetailDTO reworkDetailDTO = reworkDetailMapper.toDto(reworkDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReworkDetailMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reworkDetailDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReworkDetail in the database
        List<ReworkDetail> reworkDetailList = reworkDetailRepository.findAll();
        assertThat(reworkDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReworkDetailWithPatch() throws Exception {
        // Initialize the database
        reworkDetailRepository.saveAndFlush(reworkDetail);

        int databaseSizeBeforeUpdate = reworkDetailRepository.findAll().size();

        // Update the reworkDetail using partial update
        ReworkDetail partialUpdatedReworkDetail = new ReworkDetail();
        partialUpdatedReworkDetail.setId(reworkDetail.getId());

        partialUpdatedReworkDetail.pdnDate(UPDATED_PDN_DATE).status(UPDATED_STATUS);

        restReworkDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReworkDetail.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReworkDetail))
            )
            .andExpect(status().isOk());

        // Validate the ReworkDetail in the database
        List<ReworkDetail> reworkDetailList = reworkDetailRepository.findAll();
        assertThat(reworkDetailList).hasSize(databaseSizeBeforeUpdate);
        ReworkDetail testReworkDetail = reworkDetailList.get(reworkDetailList.size() - 1);
        assertThat(testReworkDetail.getUicode()).isEqualTo(DEFAULT_UICODE);
        assertThat(testReworkDetail.getPdnDate()).isEqualTo(UPDATED_PDN_DATE);
        assertThat(testReworkDetail.getReworkDate()).isEqualTo(DEFAULT_REWORK_DATE);
        assertThat(testReworkDetail.getNumberOfCTNs()).isEqualTo(DEFAULT_NUMBER_OF_CT_NS);
        assertThat(testReworkDetail.getStartCTNNumber()).isEqualTo(DEFAULT_START_CTN_NUMBER);
        assertThat(testReworkDetail.getEndCTNNumber()).isEqualTo(DEFAULT_END_CTN_NUMBER);
        assertThat(testReworkDetail.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testReworkDetail.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateReworkDetailWithPatch() throws Exception {
        // Initialize the database
        reworkDetailRepository.saveAndFlush(reworkDetail);

        int databaseSizeBeforeUpdate = reworkDetailRepository.findAll().size();

        // Update the reworkDetail using partial update
        ReworkDetail partialUpdatedReworkDetail = new ReworkDetail();
        partialUpdatedReworkDetail.setId(reworkDetail.getId());

        partialUpdatedReworkDetail
            .uicode(UPDATED_UICODE)
            .pdnDate(UPDATED_PDN_DATE)
            .reworkDate(UPDATED_REWORK_DATE)
            .numberOfCTNs(UPDATED_NUMBER_OF_CT_NS)
            .startCTNNumber(UPDATED_START_CTN_NUMBER)
            .endCTNNumber(UPDATED_END_CTN_NUMBER)
            .status(UPDATED_STATUS)
            .createdAt(UPDATED_CREATED_AT);

        restReworkDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReworkDetail.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReworkDetail))
            )
            .andExpect(status().isOk());

        // Validate the ReworkDetail in the database
        List<ReworkDetail> reworkDetailList = reworkDetailRepository.findAll();
        assertThat(reworkDetailList).hasSize(databaseSizeBeforeUpdate);
        ReworkDetail testReworkDetail = reworkDetailList.get(reworkDetailList.size() - 1);
        assertThat(testReworkDetail.getUicode()).isEqualTo(UPDATED_UICODE);
        assertThat(testReworkDetail.getPdnDate()).isEqualTo(UPDATED_PDN_DATE);
        assertThat(testReworkDetail.getReworkDate()).isEqualTo(UPDATED_REWORK_DATE);
        assertThat(testReworkDetail.getNumberOfCTNs()).isEqualTo(UPDATED_NUMBER_OF_CT_NS);
        assertThat(testReworkDetail.getStartCTNNumber()).isEqualTo(UPDATED_START_CTN_NUMBER);
        assertThat(testReworkDetail.getEndCTNNumber()).isEqualTo(UPDATED_END_CTN_NUMBER);
        assertThat(testReworkDetail.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testReworkDetail.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingReworkDetail() throws Exception {
        int databaseSizeBeforeUpdate = reworkDetailRepository.findAll().size();
        reworkDetail.setId(count.incrementAndGet());

        // Create the ReworkDetail
        ReworkDetailDTO reworkDetailDTO = reworkDetailMapper.toDto(reworkDetail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReworkDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, reworkDetailDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reworkDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReworkDetail in the database
        List<ReworkDetail> reworkDetailList = reworkDetailRepository.findAll();
        assertThat(reworkDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReworkDetail() throws Exception {
        int databaseSizeBeforeUpdate = reworkDetailRepository.findAll().size();
        reworkDetail.setId(count.incrementAndGet());

        // Create the ReworkDetail
        ReworkDetailDTO reworkDetailDTO = reworkDetailMapper.toDto(reworkDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReworkDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reworkDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReworkDetail in the database
        List<ReworkDetail> reworkDetailList = reworkDetailRepository.findAll();
        assertThat(reworkDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReworkDetail() throws Exception {
        int databaseSizeBeforeUpdate = reworkDetailRepository.findAll().size();
        reworkDetail.setId(count.incrementAndGet());

        // Create the ReworkDetail
        ReworkDetailDTO reworkDetailDTO = reworkDetailMapper.toDto(reworkDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReworkDetailMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reworkDetailDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReworkDetail in the database
        List<ReworkDetail> reworkDetailList = reworkDetailRepository.findAll();
        assertThat(reworkDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReworkDetail() throws Exception {
        // Initialize the database
        reworkDetailRepository.saveAndFlush(reworkDetail);

        int databaseSizeBeforeDelete = reworkDetailRepository.findAll().size();

        // Delete the reworkDetail
        restReworkDetailMockMvc
            .perform(delete(ENTITY_API_URL_ID, reworkDetail.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReworkDetail> reworkDetailList = reworkDetailRepository.findAll();
        assertThat(reworkDetailList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
