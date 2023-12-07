package com.bhachu.farmica.web.rest;

import static com.bhachu.farmica.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bhachu.farmica.IntegrationTest;
import com.bhachu.farmica.domain.LotDetail;
import com.bhachu.farmica.domain.SalesDetail;
import com.bhachu.farmica.domain.Style;
import com.bhachu.farmica.domain.User;
import com.bhachu.farmica.domain.WarehouseDetail;
import com.bhachu.farmica.repository.SalesDetailRepository;
import com.bhachu.farmica.service.SalesDetailService;
import com.bhachu.farmica.service.dto.SalesDetailDTO;
import com.bhachu.farmica.service.mapper.SalesDetailMapper;
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
 * Integration tests for the {@link SalesDetailResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SalesDetailResourceIT {

    private static final String DEFAULT_UICODE = "AAAAAAAAAA";
    private static final String UPDATED_UICODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_SALES_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SALES_DATE = LocalDate.now(ZoneId.systemDefault());

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

    private static final String ENTITY_API_URL = "/api/sales-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SalesDetailRepository salesDetailRepository;

    @Mock
    private SalesDetailRepository salesDetailRepositoryMock;

    @Autowired
    private SalesDetailMapper salesDetailMapper;

    @Mock
    private SalesDetailService salesDetailServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSalesDetailMockMvc;

    private SalesDetail salesDetail;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalesDetail createEntity(EntityManager em) {
        SalesDetail salesDetail = new SalesDetail()
            .uicode(DEFAULT_UICODE)
            .salesDate(DEFAULT_SALES_DATE)
            .numberOfCTNs(DEFAULT_NUMBER_OF_CT_NS)
            .receivedCTNs(DEFAULT_RECEIVED_CT_NS)
            .startCTNNumber(DEFAULT_START_CTN_NUMBER)
            .endCTNNumber(DEFAULT_END_CTN_NUMBER)
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
        salesDetail.setWarehouseDetail(warehouseDetail);
        // Add required entity
        LotDetail lotDetail;
        if (TestUtil.findAll(em, LotDetail.class).isEmpty()) {
            lotDetail = LotDetailResourceIT.createEntity(em);
            em.persist(lotDetail);
            em.flush();
        } else {
            lotDetail = TestUtil.findAll(em, LotDetail.class).get(0);
        }
        salesDetail.setLotDetail(lotDetail);
        // Add required entity
        Style style;
        if (TestUtil.findAll(em, Style.class).isEmpty()) {
            style = StyleResourceIT.createEntity(em);
            em.persist(style);
            em.flush();
        } else {
            style = TestUtil.findAll(em, Style.class).get(0);
        }
        salesDetail.setStyle(style);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        salesDetail.setUser(user);
        return salesDetail;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalesDetail createUpdatedEntity(EntityManager em) {
        SalesDetail salesDetail = new SalesDetail()
            .uicode(UPDATED_UICODE)
            .salesDate(UPDATED_SALES_DATE)
            .numberOfCTNs(UPDATED_NUMBER_OF_CT_NS)
            .receivedCTNs(UPDATED_RECEIVED_CT_NS)
            .startCTNNumber(UPDATED_START_CTN_NUMBER)
            .endCTNNumber(UPDATED_END_CTN_NUMBER)
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
        salesDetail.setWarehouseDetail(warehouseDetail);
        // Add required entity
        LotDetail lotDetail;
        if (TestUtil.findAll(em, LotDetail.class).isEmpty()) {
            lotDetail = LotDetailResourceIT.createUpdatedEntity(em);
            em.persist(lotDetail);
            em.flush();
        } else {
            lotDetail = TestUtil.findAll(em, LotDetail.class).get(0);
        }
        salesDetail.setLotDetail(lotDetail);
        // Add required entity
        Style style;
        if (TestUtil.findAll(em, Style.class).isEmpty()) {
            style = StyleResourceIT.createUpdatedEntity(em);
            em.persist(style);
            em.flush();
        } else {
            style = TestUtil.findAll(em, Style.class).get(0);
        }
        salesDetail.setStyle(style);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        salesDetail.setUser(user);
        return salesDetail;
    }

    @BeforeEach
    public void initTest() {
        salesDetail = createEntity(em);
    }

    @Test
    @Transactional
    void createSalesDetail() throws Exception {
        int databaseSizeBeforeCreate = salesDetailRepository.findAll().size();
        // Create the SalesDetail
        SalesDetailDTO salesDetailDTO = salesDetailMapper.toDto(salesDetail);
        restSalesDetailMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(salesDetailDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SalesDetail in the database
        List<SalesDetail> salesDetailList = salesDetailRepository.findAll();
        assertThat(salesDetailList).hasSize(databaseSizeBeforeCreate + 1);
        SalesDetail testSalesDetail = salesDetailList.get(salesDetailList.size() - 1);
        assertThat(testSalesDetail.getUicode()).isEqualTo(DEFAULT_UICODE);
        assertThat(testSalesDetail.getSalesDate()).isEqualTo(DEFAULT_SALES_DATE);
        assertThat(testSalesDetail.getNumberOfCTNs()).isEqualTo(DEFAULT_NUMBER_OF_CT_NS);
        assertThat(testSalesDetail.getReceivedCTNs()).isEqualTo(DEFAULT_RECEIVED_CT_NS);
        assertThat(testSalesDetail.getStartCTNNumber()).isEqualTo(DEFAULT_START_CTN_NUMBER);
        assertThat(testSalesDetail.getEndCTNNumber()).isEqualTo(DEFAULT_END_CTN_NUMBER);
        assertThat(testSalesDetail.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    void createSalesDetailWithExistingId() throws Exception {
        // Create the SalesDetail with an existing ID
        salesDetail.setId(1L);
        SalesDetailDTO salesDetailDTO = salesDetailMapper.toDto(salesDetail);

        int databaseSizeBeforeCreate = salesDetailRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalesDetailMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(salesDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesDetail in the database
        List<SalesDetail> salesDetailList = salesDetailRepository.findAll();
        assertThat(salesDetailList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSalesDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = salesDetailRepository.findAll().size();
        // set the field null
        salesDetail.setSalesDate(null);

        // Create the SalesDetail, which fails.
        SalesDetailDTO salesDetailDTO = salesDetailMapper.toDto(salesDetail);

        restSalesDetailMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(salesDetailDTO))
            )
            .andExpect(status().isBadRequest());

        List<SalesDetail> salesDetailList = salesDetailRepository.findAll();
        assertThat(salesDetailList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumberOfCTNsIsRequired() throws Exception {
        int databaseSizeBeforeTest = salesDetailRepository.findAll().size();
        // set the field null
        salesDetail.setNumberOfCTNs(null);

        // Create the SalesDetail, which fails.
        SalesDetailDTO salesDetailDTO = salesDetailMapper.toDto(salesDetail);

        restSalesDetailMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(salesDetailDTO))
            )
            .andExpect(status().isBadRequest());

        List<SalesDetail> salesDetailList = salesDetailRepository.findAll();
        assertThat(salesDetailList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSalesDetails() throws Exception {
        // Initialize the database
        salesDetailRepository.saveAndFlush(salesDetail);

        // Get all the salesDetailList
        restSalesDetailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesDetail.getId().intValue())))
            .andExpect(jsonPath("$.[*].uicode").value(hasItem(DEFAULT_UICODE)))
            .andExpect(jsonPath("$.[*].salesDate").value(hasItem(DEFAULT_SALES_DATE.toString())))
            .andExpect(jsonPath("$.[*].numberOfCTNs").value(hasItem(DEFAULT_NUMBER_OF_CT_NS)))
            .andExpect(jsonPath("$.[*].receivedCTNs").value(hasItem(DEFAULT_RECEIVED_CT_NS)))
            .andExpect(jsonPath("$.[*].startCTNNumber").value(hasItem(DEFAULT_START_CTN_NUMBER)))
            .andExpect(jsonPath("$.[*].endCTNNumber").value(hasItem(DEFAULT_END_CTN_NUMBER)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSalesDetailsWithEagerRelationshipsIsEnabled() throws Exception {
        when(salesDetailServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSalesDetailMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(salesDetailServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSalesDetailsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(salesDetailServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSalesDetailMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(salesDetailRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getSalesDetail() throws Exception {
        // Initialize the database
        salesDetailRepository.saveAndFlush(salesDetail);

        // Get the salesDetail
        restSalesDetailMockMvc
            .perform(get(ENTITY_API_URL_ID, salesDetail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(salesDetail.getId().intValue()))
            .andExpect(jsonPath("$.uicode").value(DEFAULT_UICODE))
            .andExpect(jsonPath("$.salesDate").value(DEFAULT_SALES_DATE.toString()))
            .andExpect(jsonPath("$.numberOfCTNs").value(DEFAULT_NUMBER_OF_CT_NS))
            .andExpect(jsonPath("$.receivedCTNs").value(DEFAULT_RECEIVED_CT_NS))
            .andExpect(jsonPath("$.startCTNNumber").value(DEFAULT_START_CTN_NUMBER))
            .andExpect(jsonPath("$.endCTNNumber").value(DEFAULT_END_CTN_NUMBER))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)));
    }

    @Test
    @Transactional
    void getNonExistingSalesDetail() throws Exception {
        // Get the salesDetail
        restSalesDetailMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSalesDetail() throws Exception {
        // Initialize the database
        salesDetailRepository.saveAndFlush(salesDetail);

        int databaseSizeBeforeUpdate = salesDetailRepository.findAll().size();

        // Update the salesDetail
        SalesDetail updatedSalesDetail = salesDetailRepository.findById(salesDetail.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSalesDetail are not directly saved in db
        em.detach(updatedSalesDetail);
        updatedSalesDetail
            .uicode(UPDATED_UICODE)
            .salesDate(UPDATED_SALES_DATE)
            .numberOfCTNs(UPDATED_NUMBER_OF_CT_NS)
            .receivedCTNs(UPDATED_RECEIVED_CT_NS)
            .startCTNNumber(UPDATED_START_CTN_NUMBER)
            .endCTNNumber(UPDATED_END_CTN_NUMBER)
            .createdAt(UPDATED_CREATED_AT);
        SalesDetailDTO salesDetailDTO = salesDetailMapper.toDto(updatedSalesDetail);

        restSalesDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, salesDetailDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(salesDetailDTO))
            )
            .andExpect(status().isOk());

        // Validate the SalesDetail in the database
        List<SalesDetail> salesDetailList = salesDetailRepository.findAll();
        assertThat(salesDetailList).hasSize(databaseSizeBeforeUpdate);
        SalesDetail testSalesDetail = salesDetailList.get(salesDetailList.size() - 1);
        assertThat(testSalesDetail.getUicode()).isEqualTo(UPDATED_UICODE);
        assertThat(testSalesDetail.getSalesDate()).isEqualTo(UPDATED_SALES_DATE);
        assertThat(testSalesDetail.getNumberOfCTNs()).isEqualTo(UPDATED_NUMBER_OF_CT_NS);
        assertThat(testSalesDetail.getReceivedCTNs()).isEqualTo(UPDATED_RECEIVED_CT_NS);
        assertThat(testSalesDetail.getStartCTNNumber()).isEqualTo(UPDATED_START_CTN_NUMBER);
        assertThat(testSalesDetail.getEndCTNNumber()).isEqualTo(UPDATED_END_CTN_NUMBER);
        assertThat(testSalesDetail.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingSalesDetail() throws Exception {
        int databaseSizeBeforeUpdate = salesDetailRepository.findAll().size();
        salesDetail.setId(count.incrementAndGet());

        // Create the SalesDetail
        SalesDetailDTO salesDetailDTO = salesDetailMapper.toDto(salesDetail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalesDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, salesDetailDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(salesDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesDetail in the database
        List<SalesDetail> salesDetailList = salesDetailRepository.findAll();
        assertThat(salesDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSalesDetail() throws Exception {
        int databaseSizeBeforeUpdate = salesDetailRepository.findAll().size();
        salesDetail.setId(count.incrementAndGet());

        // Create the SalesDetail
        SalesDetailDTO salesDetailDTO = salesDetailMapper.toDto(salesDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(salesDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesDetail in the database
        List<SalesDetail> salesDetailList = salesDetailRepository.findAll();
        assertThat(salesDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSalesDetail() throws Exception {
        int databaseSizeBeforeUpdate = salesDetailRepository.findAll().size();
        salesDetail.setId(count.incrementAndGet());

        // Create the SalesDetail
        SalesDetailDTO salesDetailDTO = salesDetailMapper.toDto(salesDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesDetailMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(salesDetailDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SalesDetail in the database
        List<SalesDetail> salesDetailList = salesDetailRepository.findAll();
        assertThat(salesDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSalesDetailWithPatch() throws Exception {
        // Initialize the database
        salesDetailRepository.saveAndFlush(salesDetail);

        int databaseSizeBeforeUpdate = salesDetailRepository.findAll().size();

        // Update the salesDetail using partial update
        SalesDetail partialUpdatedSalesDetail = new SalesDetail();
        partialUpdatedSalesDetail.setId(salesDetail.getId());

        partialUpdatedSalesDetail
            .uicode(UPDATED_UICODE)
            .salesDate(UPDATED_SALES_DATE)
            .numberOfCTNs(UPDATED_NUMBER_OF_CT_NS)
            .receivedCTNs(UPDATED_RECEIVED_CT_NS)
            .startCTNNumber(UPDATED_START_CTN_NUMBER)
            .createdAt(UPDATED_CREATED_AT);

        restSalesDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSalesDetail.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSalesDetail))
            )
            .andExpect(status().isOk());

        // Validate the SalesDetail in the database
        List<SalesDetail> salesDetailList = salesDetailRepository.findAll();
        assertThat(salesDetailList).hasSize(databaseSizeBeforeUpdate);
        SalesDetail testSalesDetail = salesDetailList.get(salesDetailList.size() - 1);
        assertThat(testSalesDetail.getUicode()).isEqualTo(UPDATED_UICODE);
        assertThat(testSalesDetail.getSalesDate()).isEqualTo(UPDATED_SALES_DATE);
        assertThat(testSalesDetail.getNumberOfCTNs()).isEqualTo(UPDATED_NUMBER_OF_CT_NS);
        assertThat(testSalesDetail.getReceivedCTNs()).isEqualTo(UPDATED_RECEIVED_CT_NS);
        assertThat(testSalesDetail.getStartCTNNumber()).isEqualTo(UPDATED_START_CTN_NUMBER);
        assertThat(testSalesDetail.getEndCTNNumber()).isEqualTo(DEFAULT_END_CTN_NUMBER);
        assertThat(testSalesDetail.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateSalesDetailWithPatch() throws Exception {
        // Initialize the database
        salesDetailRepository.saveAndFlush(salesDetail);

        int databaseSizeBeforeUpdate = salesDetailRepository.findAll().size();

        // Update the salesDetail using partial update
        SalesDetail partialUpdatedSalesDetail = new SalesDetail();
        partialUpdatedSalesDetail.setId(salesDetail.getId());

        partialUpdatedSalesDetail
            .uicode(UPDATED_UICODE)
            .salesDate(UPDATED_SALES_DATE)
            .numberOfCTNs(UPDATED_NUMBER_OF_CT_NS)
            .receivedCTNs(UPDATED_RECEIVED_CT_NS)
            .startCTNNumber(UPDATED_START_CTN_NUMBER)
            .endCTNNumber(UPDATED_END_CTN_NUMBER)
            .createdAt(UPDATED_CREATED_AT);

        restSalesDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSalesDetail.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSalesDetail))
            )
            .andExpect(status().isOk());

        // Validate the SalesDetail in the database
        List<SalesDetail> salesDetailList = salesDetailRepository.findAll();
        assertThat(salesDetailList).hasSize(databaseSizeBeforeUpdate);
        SalesDetail testSalesDetail = salesDetailList.get(salesDetailList.size() - 1);
        assertThat(testSalesDetail.getUicode()).isEqualTo(UPDATED_UICODE);
        assertThat(testSalesDetail.getSalesDate()).isEqualTo(UPDATED_SALES_DATE);
        assertThat(testSalesDetail.getNumberOfCTNs()).isEqualTo(UPDATED_NUMBER_OF_CT_NS);
        assertThat(testSalesDetail.getReceivedCTNs()).isEqualTo(UPDATED_RECEIVED_CT_NS);
        assertThat(testSalesDetail.getStartCTNNumber()).isEqualTo(UPDATED_START_CTN_NUMBER);
        assertThat(testSalesDetail.getEndCTNNumber()).isEqualTo(UPDATED_END_CTN_NUMBER);
        assertThat(testSalesDetail.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingSalesDetail() throws Exception {
        int databaseSizeBeforeUpdate = salesDetailRepository.findAll().size();
        salesDetail.setId(count.incrementAndGet());

        // Create the SalesDetail
        SalesDetailDTO salesDetailDTO = salesDetailMapper.toDto(salesDetail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalesDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, salesDetailDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(salesDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesDetail in the database
        List<SalesDetail> salesDetailList = salesDetailRepository.findAll();
        assertThat(salesDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSalesDetail() throws Exception {
        int databaseSizeBeforeUpdate = salesDetailRepository.findAll().size();
        salesDetail.setId(count.incrementAndGet());

        // Create the SalesDetail
        SalesDetailDTO salesDetailDTO = salesDetailMapper.toDto(salesDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(salesDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesDetail in the database
        List<SalesDetail> salesDetailList = salesDetailRepository.findAll();
        assertThat(salesDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSalesDetail() throws Exception {
        int databaseSizeBeforeUpdate = salesDetailRepository.findAll().size();
        salesDetail.setId(count.incrementAndGet());

        // Create the SalesDetail
        SalesDetailDTO salesDetailDTO = salesDetailMapper.toDto(salesDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesDetailMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(salesDetailDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SalesDetail in the database
        List<SalesDetail> salesDetailList = salesDetailRepository.findAll();
        assertThat(salesDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSalesDetail() throws Exception {
        // Initialize the database
        salesDetailRepository.saveAndFlush(salesDetail);

        int databaseSizeBeforeDelete = salesDetailRepository.findAll().size();

        // Delete the salesDetail
        restSalesDetailMockMvc
            .perform(delete(ENTITY_API_URL_ID, salesDetail.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SalesDetail> salesDetailList = salesDetailRepository.findAll();
        assertThat(salesDetailList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
