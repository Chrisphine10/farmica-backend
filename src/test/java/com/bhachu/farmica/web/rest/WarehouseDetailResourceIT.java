package com.bhachu.farmica.web.rest;

import static com.bhachu.farmica.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bhachu.farmica.IntegrationTest;
import com.bhachu.farmica.domain.LotDetail;
import com.bhachu.farmica.domain.PackingZoneDetail;
import com.bhachu.farmica.domain.Style;
import com.bhachu.farmica.domain.User;
import com.bhachu.farmica.domain.WarehouseDetail;
import com.bhachu.farmica.repository.WarehouseDetailRepository;
import com.bhachu.farmica.service.WarehouseDetailService;
import com.bhachu.farmica.service.dto.WarehouseDetailDTO;
import com.bhachu.farmica.service.mapper.WarehouseDetailMapper;
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
 * Integration tests for the {@link WarehouseDetailResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class WarehouseDetailResourceIT {

    private static final String DEFAULT_UICODE = "AAAAAAAAAA";
    private static final String UPDATED_UICODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_WAREHOUSE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_WAREHOUSE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_NUMBER_OF_CT_NS = 1;
    private static final Integer UPDATED_NUMBER_OF_CT_NS = 2;

    private static final Integer DEFAULT_RECEIVED_CT_NS = 1;
    private static final Integer UPDATED_RECEIVED_CT_NS = 2;

    private static final Integer DEFAULT_START_CTN_NUMBER = 1;
    private static final Integer UPDATED_START_CTN_NUMBER = 2;

    private static final Integer DEFAULT_END_CTN_NUMBER = 1;
    private static final Integer UPDATED_END_CTN_NUMBER = 2;

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/warehouse-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WarehouseDetailRepository warehouseDetailRepository;

    @Mock
    private WarehouseDetailRepository warehouseDetailRepositoryMock;

    @Autowired
    private WarehouseDetailMapper warehouseDetailMapper;

    @Mock
    private WarehouseDetailService warehouseDetailServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWarehouseDetailMockMvc;

    private WarehouseDetail warehouseDetail;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WarehouseDetail createEntity(EntityManager em) {
        WarehouseDetail warehouseDetail = new WarehouseDetail()
            .uicode(DEFAULT_UICODE)
            .warehouseDate(DEFAULT_WAREHOUSE_DATE)
            .numberOfCTNs(DEFAULT_NUMBER_OF_CT_NS)
            .receivedCTNs(DEFAULT_RECEIVED_CT_NS)
            .startCTNNumber(DEFAULT_START_CTN_NUMBER)
            .endCTNNumber(DEFAULT_END_CTN_NUMBER)
            .createdAt(DEFAULT_CREATED_AT);
        // Add required entity
        PackingZoneDetail packingZoneDetail;
        if (TestUtil.findAll(em, PackingZoneDetail.class).isEmpty()) {
            packingZoneDetail = PackingZoneDetailResourceIT.createEntity(em);
            em.persist(packingZoneDetail);
            em.flush();
        } else {
            packingZoneDetail = TestUtil.findAll(em, PackingZoneDetail.class).get(0);
        }
        warehouseDetail.setPackingZoneDetail(packingZoneDetail);
        // Add required entity
        LotDetail lotDetail;
        if (TestUtil.findAll(em, LotDetail.class).isEmpty()) {
            lotDetail = LotDetailResourceIT.createEntity(em);
            em.persist(lotDetail);
            em.flush();
        } else {
            lotDetail = TestUtil.findAll(em, LotDetail.class).get(0);
        }
        warehouseDetail.setLotDetail(lotDetail);
        // Add required entity
        Style style;
        if (TestUtil.findAll(em, Style.class).isEmpty()) {
            style = StyleResourceIT.createEntity(em);
            em.persist(style);
            em.flush();
        } else {
            style = TestUtil.findAll(em, Style.class).get(0);
        }
        warehouseDetail.setStyle(style);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        warehouseDetail.setUser(user);
        return warehouseDetail;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WarehouseDetail createUpdatedEntity(EntityManager em) {
        WarehouseDetail warehouseDetail = new WarehouseDetail()
            .uicode(UPDATED_UICODE)
            .warehouseDate(UPDATED_WAREHOUSE_DATE)
            .numberOfCTNs(UPDATED_NUMBER_OF_CT_NS)
            .receivedCTNs(UPDATED_RECEIVED_CT_NS)
            .startCTNNumber(UPDATED_START_CTN_NUMBER)
            .endCTNNumber(UPDATED_END_CTN_NUMBER)
            .createdAt(UPDATED_CREATED_AT);
        // Add required entity
        PackingZoneDetail packingZoneDetail;
        if (TestUtil.findAll(em, PackingZoneDetail.class).isEmpty()) {
            packingZoneDetail = PackingZoneDetailResourceIT.createUpdatedEntity(em);
            em.persist(packingZoneDetail);
            em.flush();
        } else {
            packingZoneDetail = TestUtil.findAll(em, PackingZoneDetail.class).get(0);
        }
        warehouseDetail.setPackingZoneDetail(packingZoneDetail);
        // Add required entity
        LotDetail lotDetail;
        if (TestUtil.findAll(em, LotDetail.class).isEmpty()) {
            lotDetail = LotDetailResourceIT.createUpdatedEntity(em);
            em.persist(lotDetail);
            em.flush();
        } else {
            lotDetail = TestUtil.findAll(em, LotDetail.class).get(0);
        }
        warehouseDetail.setLotDetail(lotDetail);
        // Add required entity
        Style style;
        if (TestUtil.findAll(em, Style.class).isEmpty()) {
            style = StyleResourceIT.createUpdatedEntity(em);
            em.persist(style);
            em.flush();
        } else {
            style = TestUtil.findAll(em, Style.class).get(0);
        }
        warehouseDetail.setStyle(style);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        warehouseDetail.setUser(user);
        return warehouseDetail;
    }

    @BeforeEach
    public void initTest() {
        warehouseDetail = createEntity(em);
    }

    @Test
    @Transactional
    void createWarehouseDetail() throws Exception {
        int databaseSizeBeforeCreate = warehouseDetailRepository.findAll().size();
        // Create the WarehouseDetail
        WarehouseDetailDTO warehouseDetailDTO = warehouseDetailMapper.toDto(warehouseDetail);
        restWarehouseDetailMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(warehouseDetailDTO))
            )
            .andExpect(status().isCreated());

        // Validate the WarehouseDetail in the database
        List<WarehouseDetail> warehouseDetailList = warehouseDetailRepository.findAll();
        assertThat(warehouseDetailList).hasSize(databaseSizeBeforeCreate + 1);
        WarehouseDetail testWarehouseDetail = warehouseDetailList.get(warehouseDetailList.size() - 1);
        assertThat(testWarehouseDetail.getUicode()).isEqualTo(DEFAULT_UICODE);
        assertThat(testWarehouseDetail.getWarehouseDate()).isEqualTo(DEFAULT_WAREHOUSE_DATE);
        assertThat(testWarehouseDetail.getNumberOfCTNs()).isEqualTo(DEFAULT_NUMBER_OF_CT_NS);
        assertThat(testWarehouseDetail.getReceivedCTNs()).isEqualTo(DEFAULT_RECEIVED_CT_NS);
        assertThat(testWarehouseDetail.getStartCTNNumber()).isEqualTo(DEFAULT_START_CTN_NUMBER);
        assertThat(testWarehouseDetail.getEndCTNNumber()).isEqualTo(DEFAULT_END_CTN_NUMBER);
        assertThat(testWarehouseDetail.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    void createWarehouseDetailWithExistingId() throws Exception {
        // Create the WarehouseDetail with an existing ID
        warehouseDetail.setId(1L);
        WarehouseDetailDTO warehouseDetailDTO = warehouseDetailMapper.toDto(warehouseDetail);

        int databaseSizeBeforeCreate = warehouseDetailRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWarehouseDetailMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(warehouseDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WarehouseDetail in the database
        List<WarehouseDetail> warehouseDetailList = warehouseDetailRepository.findAll();
        assertThat(warehouseDetailList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkWarehouseDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = warehouseDetailRepository.findAll().size();
        // set the field null
        warehouseDetail.setWarehouseDate(null);

        // Create the WarehouseDetail, which fails.
        WarehouseDetailDTO warehouseDetailDTO = warehouseDetailMapper.toDto(warehouseDetail);

        restWarehouseDetailMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(warehouseDetailDTO))
            )
            .andExpect(status().isBadRequest());

        List<WarehouseDetail> warehouseDetailList = warehouseDetailRepository.findAll();
        assertThat(warehouseDetailList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllWarehouseDetails() throws Exception {
        // Initialize the database
        warehouseDetailRepository.saveAndFlush(warehouseDetail);

        // Get all the warehouseDetailList
        restWarehouseDetailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(warehouseDetail.getId().intValue())))
            .andExpect(jsonPath("$.[*].uicode").value(hasItem(DEFAULT_UICODE)))
            .andExpect(jsonPath("$.[*].warehouseDate").value(hasItem(DEFAULT_WAREHOUSE_DATE.toString())))
            .andExpect(jsonPath("$.[*].numberOfCTNs").value(hasItem(DEFAULT_NUMBER_OF_CT_NS)))
            .andExpect(jsonPath("$.[*].receivedCTNs").value(hasItem(DEFAULT_RECEIVED_CT_NS)))
            .andExpect(jsonPath("$.[*].startCTNNumber").value(hasItem(DEFAULT_START_CTN_NUMBER)))
            .andExpect(jsonPath("$.[*].endCTNNumber").value(hasItem(DEFAULT_END_CTN_NUMBER)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllWarehouseDetailsWithEagerRelationshipsIsEnabled() throws Exception {
        when(warehouseDetailServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restWarehouseDetailMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(warehouseDetailServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllWarehouseDetailsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(warehouseDetailServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restWarehouseDetailMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(warehouseDetailRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getWarehouseDetail() throws Exception {
        // Initialize the database
        warehouseDetailRepository.saveAndFlush(warehouseDetail);

        // Get the warehouseDetail
        restWarehouseDetailMockMvc
            .perform(get(ENTITY_API_URL_ID, warehouseDetail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(warehouseDetail.getId().intValue()))
            .andExpect(jsonPath("$.uicode").value(DEFAULT_UICODE))
            .andExpect(jsonPath("$.warehouseDate").value(DEFAULT_WAREHOUSE_DATE.toString()))
            .andExpect(jsonPath("$.numberOfCTNs").value(DEFAULT_NUMBER_OF_CT_NS))
            .andExpect(jsonPath("$.receivedCTNs").value(DEFAULT_RECEIVED_CT_NS))
            .andExpect(jsonPath("$.startCTNNumber").value(DEFAULT_START_CTN_NUMBER))
            .andExpect(jsonPath("$.endCTNNumber").value(DEFAULT_END_CTN_NUMBER))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)));
    }

    @Test
    @Transactional
    void getNonExistingWarehouseDetail() throws Exception {
        // Get the warehouseDetail
        restWarehouseDetailMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWarehouseDetail() throws Exception {
        // Initialize the database
        warehouseDetailRepository.saveAndFlush(warehouseDetail);

        int databaseSizeBeforeUpdate = warehouseDetailRepository.findAll().size();

        // Update the warehouseDetail
        WarehouseDetail updatedWarehouseDetail = warehouseDetailRepository.findById(warehouseDetail.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedWarehouseDetail are not directly saved in db
        em.detach(updatedWarehouseDetail);
        updatedWarehouseDetail
            .uicode(UPDATED_UICODE)
            .warehouseDate(UPDATED_WAREHOUSE_DATE)
            .numberOfCTNs(UPDATED_NUMBER_OF_CT_NS)
            .receivedCTNs(UPDATED_RECEIVED_CT_NS)
            .startCTNNumber(UPDATED_START_CTN_NUMBER)
            .endCTNNumber(UPDATED_END_CTN_NUMBER)
            .createdAt(UPDATED_CREATED_AT);
        WarehouseDetailDTO warehouseDetailDTO = warehouseDetailMapper.toDto(updatedWarehouseDetail);

        restWarehouseDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, warehouseDetailDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(warehouseDetailDTO))
            )
            .andExpect(status().isOk());

        // Validate the WarehouseDetail in the database
        List<WarehouseDetail> warehouseDetailList = warehouseDetailRepository.findAll();
        assertThat(warehouseDetailList).hasSize(databaseSizeBeforeUpdate);
        WarehouseDetail testWarehouseDetail = warehouseDetailList.get(warehouseDetailList.size() - 1);
        assertThat(testWarehouseDetail.getUicode()).isEqualTo(UPDATED_UICODE);
        assertThat(testWarehouseDetail.getWarehouseDate()).isEqualTo(UPDATED_WAREHOUSE_DATE);
        assertThat(testWarehouseDetail.getNumberOfCTNs()).isEqualTo(UPDATED_NUMBER_OF_CT_NS);
        assertThat(testWarehouseDetail.getReceivedCTNs()).isEqualTo(UPDATED_RECEIVED_CT_NS);
        assertThat(testWarehouseDetail.getStartCTNNumber()).isEqualTo(UPDATED_START_CTN_NUMBER);
        assertThat(testWarehouseDetail.getEndCTNNumber()).isEqualTo(UPDATED_END_CTN_NUMBER);
        assertThat(testWarehouseDetail.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingWarehouseDetail() throws Exception {
        int databaseSizeBeforeUpdate = warehouseDetailRepository.findAll().size();
        warehouseDetail.setId(count.incrementAndGet());

        // Create the WarehouseDetail
        WarehouseDetailDTO warehouseDetailDTO = warehouseDetailMapper.toDto(warehouseDetail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWarehouseDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, warehouseDetailDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(warehouseDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WarehouseDetail in the database
        List<WarehouseDetail> warehouseDetailList = warehouseDetailRepository.findAll();
        assertThat(warehouseDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWarehouseDetail() throws Exception {
        int databaseSizeBeforeUpdate = warehouseDetailRepository.findAll().size();
        warehouseDetail.setId(count.incrementAndGet());

        // Create the WarehouseDetail
        WarehouseDetailDTO warehouseDetailDTO = warehouseDetailMapper.toDto(warehouseDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWarehouseDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(warehouseDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WarehouseDetail in the database
        List<WarehouseDetail> warehouseDetailList = warehouseDetailRepository.findAll();
        assertThat(warehouseDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWarehouseDetail() throws Exception {
        int databaseSizeBeforeUpdate = warehouseDetailRepository.findAll().size();
        warehouseDetail.setId(count.incrementAndGet());

        // Create the WarehouseDetail
        WarehouseDetailDTO warehouseDetailDTO = warehouseDetailMapper.toDto(warehouseDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWarehouseDetailMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(warehouseDetailDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WarehouseDetail in the database
        List<WarehouseDetail> warehouseDetailList = warehouseDetailRepository.findAll();
        assertThat(warehouseDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWarehouseDetailWithPatch() throws Exception {
        // Initialize the database
        warehouseDetailRepository.saveAndFlush(warehouseDetail);

        int databaseSizeBeforeUpdate = warehouseDetailRepository.findAll().size();

        // Update the warehouseDetail using partial update
        WarehouseDetail partialUpdatedWarehouseDetail = new WarehouseDetail();
        partialUpdatedWarehouseDetail.setId(warehouseDetail.getId());

        partialUpdatedWarehouseDetail.warehouseDate(UPDATED_WAREHOUSE_DATE).createdAt(UPDATED_CREATED_AT);

        restWarehouseDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWarehouseDetail.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWarehouseDetail))
            )
            .andExpect(status().isOk());

        // Validate the WarehouseDetail in the database
        List<WarehouseDetail> warehouseDetailList = warehouseDetailRepository.findAll();
        assertThat(warehouseDetailList).hasSize(databaseSizeBeforeUpdate);
        WarehouseDetail testWarehouseDetail = warehouseDetailList.get(warehouseDetailList.size() - 1);
        assertThat(testWarehouseDetail.getUicode()).isEqualTo(DEFAULT_UICODE);
        assertThat(testWarehouseDetail.getWarehouseDate()).isEqualTo(UPDATED_WAREHOUSE_DATE);
        assertThat(testWarehouseDetail.getNumberOfCTNs()).isEqualTo(DEFAULT_NUMBER_OF_CT_NS);
        assertThat(testWarehouseDetail.getReceivedCTNs()).isEqualTo(DEFAULT_RECEIVED_CT_NS);
        assertThat(testWarehouseDetail.getStartCTNNumber()).isEqualTo(DEFAULT_START_CTN_NUMBER);
        assertThat(testWarehouseDetail.getEndCTNNumber()).isEqualTo(DEFAULT_END_CTN_NUMBER);
        assertThat(testWarehouseDetail.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateWarehouseDetailWithPatch() throws Exception {
        // Initialize the database
        warehouseDetailRepository.saveAndFlush(warehouseDetail);

        int databaseSizeBeforeUpdate = warehouseDetailRepository.findAll().size();

        // Update the warehouseDetail using partial update
        WarehouseDetail partialUpdatedWarehouseDetail = new WarehouseDetail();
        partialUpdatedWarehouseDetail.setId(warehouseDetail.getId());

        partialUpdatedWarehouseDetail
            .uicode(UPDATED_UICODE)
            .warehouseDate(UPDATED_WAREHOUSE_DATE)
            .numberOfCTNs(UPDATED_NUMBER_OF_CT_NS)
            .receivedCTNs(UPDATED_RECEIVED_CT_NS)
            .startCTNNumber(UPDATED_START_CTN_NUMBER)
            .endCTNNumber(UPDATED_END_CTN_NUMBER)
            .createdAt(UPDATED_CREATED_AT);

        restWarehouseDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWarehouseDetail.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWarehouseDetail))
            )
            .andExpect(status().isOk());

        // Validate the WarehouseDetail in the database
        List<WarehouseDetail> warehouseDetailList = warehouseDetailRepository.findAll();
        assertThat(warehouseDetailList).hasSize(databaseSizeBeforeUpdate);
        WarehouseDetail testWarehouseDetail = warehouseDetailList.get(warehouseDetailList.size() - 1);
        assertThat(testWarehouseDetail.getUicode()).isEqualTo(UPDATED_UICODE);
        assertThat(testWarehouseDetail.getWarehouseDate()).isEqualTo(UPDATED_WAREHOUSE_DATE);
        assertThat(testWarehouseDetail.getNumberOfCTNs()).isEqualTo(UPDATED_NUMBER_OF_CT_NS);
        assertThat(testWarehouseDetail.getReceivedCTNs()).isEqualTo(UPDATED_RECEIVED_CT_NS);
        assertThat(testWarehouseDetail.getStartCTNNumber()).isEqualTo(UPDATED_START_CTN_NUMBER);
        assertThat(testWarehouseDetail.getEndCTNNumber()).isEqualTo(UPDATED_END_CTN_NUMBER);
        assertThat(testWarehouseDetail.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingWarehouseDetail() throws Exception {
        int databaseSizeBeforeUpdate = warehouseDetailRepository.findAll().size();
        warehouseDetail.setId(count.incrementAndGet());

        // Create the WarehouseDetail
        WarehouseDetailDTO warehouseDetailDTO = warehouseDetailMapper.toDto(warehouseDetail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWarehouseDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, warehouseDetailDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(warehouseDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WarehouseDetail in the database
        List<WarehouseDetail> warehouseDetailList = warehouseDetailRepository.findAll();
        assertThat(warehouseDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWarehouseDetail() throws Exception {
        int databaseSizeBeforeUpdate = warehouseDetailRepository.findAll().size();
        warehouseDetail.setId(count.incrementAndGet());

        // Create the WarehouseDetail
        WarehouseDetailDTO warehouseDetailDTO = warehouseDetailMapper.toDto(warehouseDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWarehouseDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(warehouseDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WarehouseDetail in the database
        List<WarehouseDetail> warehouseDetailList = warehouseDetailRepository.findAll();
        assertThat(warehouseDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWarehouseDetail() throws Exception {
        int databaseSizeBeforeUpdate = warehouseDetailRepository.findAll().size();
        warehouseDetail.setId(count.incrementAndGet());

        // Create the WarehouseDetail
        WarehouseDetailDTO warehouseDetailDTO = warehouseDetailMapper.toDto(warehouseDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWarehouseDetailMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(warehouseDetailDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WarehouseDetail in the database
        List<WarehouseDetail> warehouseDetailList = warehouseDetailRepository.findAll();
        assertThat(warehouseDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWarehouseDetail() throws Exception {
        // Initialize the database
        warehouseDetailRepository.saveAndFlush(warehouseDetail);

        int databaseSizeBeforeDelete = warehouseDetailRepository.findAll().size();

        // Delete the warehouseDetail
        restWarehouseDetailMockMvc
            .perform(delete(ENTITY_API_URL_ID, warehouseDetail.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WarehouseDetail> warehouseDetailList = warehouseDetailRepository.findAll();
        assertThat(warehouseDetailList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
