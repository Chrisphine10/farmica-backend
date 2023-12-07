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
import com.bhachu.farmica.repository.PackingZoneDetailRepository;
import com.bhachu.farmica.service.PackingZoneDetailService;
import com.bhachu.farmica.service.dto.PackingZoneDetailDTO;
import com.bhachu.farmica.service.mapper.PackingZoneDetailMapper;
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
 * Integration tests for the {@link PackingZoneDetailResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PackingZoneDetailResourceIT {

    private static final String DEFAULT_UICODE = "AAAAAAAAAA";
    private static final String UPDATED_UICODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PDN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PDN_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PACKAGE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PACKAGE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_WEIGHT_RECEIVED = 1D;
    private static final Double UPDATED_WEIGHT_RECEIVED = 2D;

    private static final Double DEFAULT_WEIGHT_BALANCE = 1D;
    private static final Double UPDATED_WEIGHT_BALANCE = 2D;

    private static final Integer DEFAULT_NUMBER_OF_CT_NS = 1;
    private static final Integer UPDATED_NUMBER_OF_CT_NS = 2;

    private static final Integer DEFAULT_RECEIVED_CT_NS = 1;
    private static final Integer UPDATED_RECEIVED_CT_NS = 2;

    private static final Integer DEFAULT_START_CTN_NUMBER = 1;
    private static final Integer UPDATED_START_CTN_NUMBER = 2;

    private static final Integer DEFAULT_END_CTN_NUMBER = 1;
    private static final Integer UPDATED_END_CTN_NUMBER = 2;

    private static final Integer DEFAULT_NUMBER_OF_CT_NS_REWORKED = 1;
    private static final Integer UPDATED_NUMBER_OF_CT_NS_REWORKED = 2;

    private static final Integer DEFAULT_NUMBER_OF_CT_NS_SOLD = 1;
    private static final Integer UPDATED_NUMBER_OF_CT_NS_SOLD = 2;

    private static final Integer DEFAULT_NUMBER_OF_CT_NS_PACKED = 1;
    private static final Integer UPDATED_NUMBER_OF_CT_NS_PACKED = 2;

    private static final Integer DEFAULT_NUMBER_OF_CT_NS_IN_WAREHOUSE = 1;
    private static final Integer UPDATED_NUMBER_OF_CT_NS_IN_WAREHOUSE = 2;

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/packing-zone-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PackingZoneDetailRepository packingZoneDetailRepository;

    @Mock
    private PackingZoneDetailRepository packingZoneDetailRepositoryMock;

    @Autowired
    private PackingZoneDetailMapper packingZoneDetailMapper;

    @Mock
    private PackingZoneDetailService packingZoneDetailServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPackingZoneDetailMockMvc;

    private PackingZoneDetail packingZoneDetail;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PackingZoneDetail createEntity(EntityManager em) {
        PackingZoneDetail packingZoneDetail = new PackingZoneDetail()
            .uicode(DEFAULT_UICODE)
            .pdnDate(DEFAULT_PDN_DATE)
            .packageDate(DEFAULT_PACKAGE_DATE)
            .weightReceived(DEFAULT_WEIGHT_RECEIVED)
            .weightBalance(DEFAULT_WEIGHT_BALANCE)
            .numberOfCTNs(DEFAULT_NUMBER_OF_CT_NS)
            .receivedCTNs(DEFAULT_RECEIVED_CT_NS)
            .startCTNNumber(DEFAULT_START_CTN_NUMBER)
            .endCTNNumber(DEFAULT_END_CTN_NUMBER)
            .numberOfCTNsReworked(DEFAULT_NUMBER_OF_CT_NS_REWORKED)
            .numberOfCTNsSold(DEFAULT_NUMBER_OF_CT_NS_SOLD)
            .numberOfCTNsPacked(DEFAULT_NUMBER_OF_CT_NS_PACKED)
            .numberOfCTNsInWarehouse(DEFAULT_NUMBER_OF_CT_NS_IN_WAREHOUSE)
            .createdAt(DEFAULT_CREATED_AT);
        // Add required entity
        LotDetail lotDetail;
        if (TestUtil.findAll(em, LotDetail.class).isEmpty()) {
            lotDetail = LotDetailResourceIT.createEntity(em);
            em.persist(lotDetail);
            em.flush();
        } else {
            lotDetail = TestUtil.findAll(em, LotDetail.class).get(0);
        }
        packingZoneDetail.setLotDetail(lotDetail);
        // Add required entity
        Style style;
        if (TestUtil.findAll(em, Style.class).isEmpty()) {
            style = StyleResourceIT.createEntity(em);
            em.persist(style);
            em.flush();
        } else {
            style = TestUtil.findAll(em, Style.class).get(0);
        }
        packingZoneDetail.setStyle(style);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        packingZoneDetail.setUser(user);
        return packingZoneDetail;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PackingZoneDetail createUpdatedEntity(EntityManager em) {
        PackingZoneDetail packingZoneDetail = new PackingZoneDetail()
            .uicode(UPDATED_UICODE)
            .pdnDate(UPDATED_PDN_DATE)
            .packageDate(UPDATED_PACKAGE_DATE)
            .weightReceived(UPDATED_WEIGHT_RECEIVED)
            .weightBalance(UPDATED_WEIGHT_BALANCE)
            .numberOfCTNs(UPDATED_NUMBER_OF_CT_NS)
            .receivedCTNs(UPDATED_RECEIVED_CT_NS)
            .startCTNNumber(UPDATED_START_CTN_NUMBER)
            .endCTNNumber(UPDATED_END_CTN_NUMBER)
            .numberOfCTNsReworked(UPDATED_NUMBER_OF_CT_NS_REWORKED)
            .numberOfCTNsSold(UPDATED_NUMBER_OF_CT_NS_SOLD)
            .numberOfCTNsPacked(UPDATED_NUMBER_OF_CT_NS_PACKED)
            .numberOfCTNsInWarehouse(UPDATED_NUMBER_OF_CT_NS_IN_WAREHOUSE)
            .createdAt(UPDATED_CREATED_AT);
        // Add required entity
        LotDetail lotDetail;
        if (TestUtil.findAll(em, LotDetail.class).isEmpty()) {
            lotDetail = LotDetailResourceIT.createUpdatedEntity(em);
            em.persist(lotDetail);
            em.flush();
        } else {
            lotDetail = TestUtil.findAll(em, LotDetail.class).get(0);
        }
        packingZoneDetail.setLotDetail(lotDetail);
        // Add required entity
        Style style;
        if (TestUtil.findAll(em, Style.class).isEmpty()) {
            style = StyleResourceIT.createUpdatedEntity(em);
            em.persist(style);
            em.flush();
        } else {
            style = TestUtil.findAll(em, Style.class).get(0);
        }
        packingZoneDetail.setStyle(style);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        packingZoneDetail.setUser(user);
        return packingZoneDetail;
    }

    @BeforeEach
    public void initTest() {
        packingZoneDetail = createEntity(em);
    }

    @Test
    @Transactional
    void createPackingZoneDetail() throws Exception {
        int databaseSizeBeforeCreate = packingZoneDetailRepository.findAll().size();
        // Create the PackingZoneDetail
        PackingZoneDetailDTO packingZoneDetailDTO = packingZoneDetailMapper.toDto(packingZoneDetail);
        restPackingZoneDetailMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(packingZoneDetailDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PackingZoneDetail in the database
        List<PackingZoneDetail> packingZoneDetailList = packingZoneDetailRepository.findAll();
        assertThat(packingZoneDetailList).hasSize(databaseSizeBeforeCreate + 1);
        PackingZoneDetail testPackingZoneDetail = packingZoneDetailList.get(packingZoneDetailList.size() - 1);
        assertThat(testPackingZoneDetail.getUicode()).isEqualTo(DEFAULT_UICODE);
        assertThat(testPackingZoneDetail.getPdnDate()).isEqualTo(DEFAULT_PDN_DATE);
        assertThat(testPackingZoneDetail.getPackageDate()).isEqualTo(DEFAULT_PACKAGE_DATE);
        assertThat(testPackingZoneDetail.getWeightReceived()).isEqualTo(DEFAULT_WEIGHT_RECEIVED);
        assertThat(testPackingZoneDetail.getWeightBalance()).isEqualTo(DEFAULT_WEIGHT_BALANCE);
        assertThat(testPackingZoneDetail.getNumberOfCTNs()).isEqualTo(DEFAULT_NUMBER_OF_CT_NS);
        assertThat(testPackingZoneDetail.getReceivedCTNs()).isEqualTo(DEFAULT_RECEIVED_CT_NS);
        assertThat(testPackingZoneDetail.getStartCTNNumber()).isEqualTo(DEFAULT_START_CTN_NUMBER);
        assertThat(testPackingZoneDetail.getEndCTNNumber()).isEqualTo(DEFAULT_END_CTN_NUMBER);
        assertThat(testPackingZoneDetail.getNumberOfCTNsReworked()).isEqualTo(DEFAULT_NUMBER_OF_CT_NS_REWORKED);
        assertThat(testPackingZoneDetail.getNumberOfCTNsSold()).isEqualTo(DEFAULT_NUMBER_OF_CT_NS_SOLD);
        assertThat(testPackingZoneDetail.getNumberOfCTNsPacked()).isEqualTo(DEFAULT_NUMBER_OF_CT_NS_PACKED);
        assertThat(testPackingZoneDetail.getNumberOfCTNsInWarehouse()).isEqualTo(DEFAULT_NUMBER_OF_CT_NS_IN_WAREHOUSE);
        assertThat(testPackingZoneDetail.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    void createPackingZoneDetailWithExistingId() throws Exception {
        // Create the PackingZoneDetail with an existing ID
        packingZoneDetail.setId(1L);
        PackingZoneDetailDTO packingZoneDetailDTO = packingZoneDetailMapper.toDto(packingZoneDetail);

        int databaseSizeBeforeCreate = packingZoneDetailRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPackingZoneDetailMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(packingZoneDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PackingZoneDetail in the database
        List<PackingZoneDetail> packingZoneDetailList = packingZoneDetailRepository.findAll();
        assertThat(packingZoneDetailList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPdnDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = packingZoneDetailRepository.findAll().size();
        // set the field null
        packingZoneDetail.setPdnDate(null);

        // Create the PackingZoneDetail, which fails.
        PackingZoneDetailDTO packingZoneDetailDTO = packingZoneDetailMapper.toDto(packingZoneDetail);

        restPackingZoneDetailMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(packingZoneDetailDTO))
            )
            .andExpect(status().isBadRequest());

        List<PackingZoneDetail> packingZoneDetailList = packingZoneDetailRepository.findAll();
        assertThat(packingZoneDetailList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPackageDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = packingZoneDetailRepository.findAll().size();
        // set the field null
        packingZoneDetail.setPackageDate(null);

        // Create the PackingZoneDetail, which fails.
        PackingZoneDetailDTO packingZoneDetailDTO = packingZoneDetailMapper.toDto(packingZoneDetail);

        restPackingZoneDetailMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(packingZoneDetailDTO))
            )
            .andExpect(status().isBadRequest());

        List<PackingZoneDetail> packingZoneDetailList = packingZoneDetailRepository.findAll();
        assertThat(packingZoneDetailList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWeightReceivedIsRequired() throws Exception {
        int databaseSizeBeforeTest = packingZoneDetailRepository.findAll().size();
        // set the field null
        packingZoneDetail.setWeightReceived(null);

        // Create the PackingZoneDetail, which fails.
        PackingZoneDetailDTO packingZoneDetailDTO = packingZoneDetailMapper.toDto(packingZoneDetail);

        restPackingZoneDetailMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(packingZoneDetailDTO))
            )
            .andExpect(status().isBadRequest());

        List<PackingZoneDetail> packingZoneDetailList = packingZoneDetailRepository.findAll();
        assertThat(packingZoneDetailList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWeightBalanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = packingZoneDetailRepository.findAll().size();
        // set the field null
        packingZoneDetail.setWeightBalance(null);

        // Create the PackingZoneDetail, which fails.
        PackingZoneDetailDTO packingZoneDetailDTO = packingZoneDetailMapper.toDto(packingZoneDetail);

        restPackingZoneDetailMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(packingZoneDetailDTO))
            )
            .andExpect(status().isBadRequest());

        List<PackingZoneDetail> packingZoneDetailList = packingZoneDetailRepository.findAll();
        assertThat(packingZoneDetailList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumberOfCTNsIsRequired() throws Exception {
        int databaseSizeBeforeTest = packingZoneDetailRepository.findAll().size();
        // set the field null
        packingZoneDetail.setNumberOfCTNs(null);

        // Create the PackingZoneDetail, which fails.
        PackingZoneDetailDTO packingZoneDetailDTO = packingZoneDetailMapper.toDto(packingZoneDetail);

        restPackingZoneDetailMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(packingZoneDetailDTO))
            )
            .andExpect(status().isBadRequest());

        List<PackingZoneDetail> packingZoneDetailList = packingZoneDetailRepository.findAll();
        assertThat(packingZoneDetailList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPackingZoneDetails() throws Exception {
        // Initialize the database
        packingZoneDetailRepository.saveAndFlush(packingZoneDetail);

        // Get all the packingZoneDetailList
        restPackingZoneDetailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(packingZoneDetail.getId().intValue())))
            .andExpect(jsonPath("$.[*].uicode").value(hasItem(DEFAULT_UICODE)))
            .andExpect(jsonPath("$.[*].pdnDate").value(hasItem(DEFAULT_PDN_DATE.toString())))
            .andExpect(jsonPath("$.[*].packageDate").value(hasItem(DEFAULT_PACKAGE_DATE.toString())))
            .andExpect(jsonPath("$.[*].weightReceived").value(hasItem(DEFAULT_WEIGHT_RECEIVED.doubleValue())))
            .andExpect(jsonPath("$.[*].weightBalance").value(hasItem(DEFAULT_WEIGHT_BALANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].numberOfCTNs").value(hasItem(DEFAULT_NUMBER_OF_CT_NS)))
            .andExpect(jsonPath("$.[*].receivedCTNs").value(hasItem(DEFAULT_RECEIVED_CT_NS)))
            .andExpect(jsonPath("$.[*].startCTNNumber").value(hasItem(DEFAULT_START_CTN_NUMBER)))
            .andExpect(jsonPath("$.[*].endCTNNumber").value(hasItem(DEFAULT_END_CTN_NUMBER)))
            .andExpect(jsonPath("$.[*].numberOfCTNsReworked").value(hasItem(DEFAULT_NUMBER_OF_CT_NS_REWORKED)))
            .andExpect(jsonPath("$.[*].numberOfCTNsSold").value(hasItem(DEFAULT_NUMBER_OF_CT_NS_SOLD)))
            .andExpect(jsonPath("$.[*].numberOfCTNsPacked").value(hasItem(DEFAULT_NUMBER_OF_CT_NS_PACKED)))
            .andExpect(jsonPath("$.[*].numberOfCTNsInWarehouse").value(hasItem(DEFAULT_NUMBER_OF_CT_NS_IN_WAREHOUSE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPackingZoneDetailsWithEagerRelationshipsIsEnabled() throws Exception {
        when(packingZoneDetailServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPackingZoneDetailMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(packingZoneDetailServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPackingZoneDetailsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(packingZoneDetailServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPackingZoneDetailMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(packingZoneDetailRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPackingZoneDetail() throws Exception {
        // Initialize the database
        packingZoneDetailRepository.saveAndFlush(packingZoneDetail);

        // Get the packingZoneDetail
        restPackingZoneDetailMockMvc
            .perform(get(ENTITY_API_URL_ID, packingZoneDetail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(packingZoneDetail.getId().intValue()))
            .andExpect(jsonPath("$.uicode").value(DEFAULT_UICODE))
            .andExpect(jsonPath("$.pdnDate").value(DEFAULT_PDN_DATE.toString()))
            .andExpect(jsonPath("$.packageDate").value(DEFAULT_PACKAGE_DATE.toString()))
            .andExpect(jsonPath("$.weightReceived").value(DEFAULT_WEIGHT_RECEIVED.doubleValue()))
            .andExpect(jsonPath("$.weightBalance").value(DEFAULT_WEIGHT_BALANCE.doubleValue()))
            .andExpect(jsonPath("$.numberOfCTNs").value(DEFAULT_NUMBER_OF_CT_NS))
            .andExpect(jsonPath("$.receivedCTNs").value(DEFAULT_RECEIVED_CT_NS))
            .andExpect(jsonPath("$.startCTNNumber").value(DEFAULT_START_CTN_NUMBER))
            .andExpect(jsonPath("$.endCTNNumber").value(DEFAULT_END_CTN_NUMBER))
            .andExpect(jsonPath("$.numberOfCTNsReworked").value(DEFAULT_NUMBER_OF_CT_NS_REWORKED))
            .andExpect(jsonPath("$.numberOfCTNsSold").value(DEFAULT_NUMBER_OF_CT_NS_SOLD))
            .andExpect(jsonPath("$.numberOfCTNsPacked").value(DEFAULT_NUMBER_OF_CT_NS_PACKED))
            .andExpect(jsonPath("$.numberOfCTNsInWarehouse").value(DEFAULT_NUMBER_OF_CT_NS_IN_WAREHOUSE))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)));
    }

    @Test
    @Transactional
    void getNonExistingPackingZoneDetail() throws Exception {
        // Get the packingZoneDetail
        restPackingZoneDetailMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPackingZoneDetail() throws Exception {
        // Initialize the database
        packingZoneDetailRepository.saveAndFlush(packingZoneDetail);

        int databaseSizeBeforeUpdate = packingZoneDetailRepository.findAll().size();

        // Update the packingZoneDetail
        PackingZoneDetail updatedPackingZoneDetail = packingZoneDetailRepository.findById(packingZoneDetail.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPackingZoneDetail are not directly saved in db
        em.detach(updatedPackingZoneDetail);
        updatedPackingZoneDetail
            .uicode(UPDATED_UICODE)
            .pdnDate(UPDATED_PDN_DATE)
            .packageDate(UPDATED_PACKAGE_DATE)
            .weightReceived(UPDATED_WEIGHT_RECEIVED)
            .weightBalance(UPDATED_WEIGHT_BALANCE)
            .numberOfCTNs(UPDATED_NUMBER_OF_CT_NS)
            .receivedCTNs(UPDATED_RECEIVED_CT_NS)
            .startCTNNumber(UPDATED_START_CTN_NUMBER)
            .endCTNNumber(UPDATED_END_CTN_NUMBER)
            .numberOfCTNsReworked(UPDATED_NUMBER_OF_CT_NS_REWORKED)
            .numberOfCTNsSold(UPDATED_NUMBER_OF_CT_NS_SOLD)
            .numberOfCTNsPacked(UPDATED_NUMBER_OF_CT_NS_PACKED)
            .numberOfCTNsInWarehouse(UPDATED_NUMBER_OF_CT_NS_IN_WAREHOUSE)
            .createdAt(UPDATED_CREATED_AT);
        PackingZoneDetailDTO packingZoneDetailDTO = packingZoneDetailMapper.toDto(updatedPackingZoneDetail);

        restPackingZoneDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, packingZoneDetailDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(packingZoneDetailDTO))
            )
            .andExpect(status().isOk());

        // Validate the PackingZoneDetail in the database
        List<PackingZoneDetail> packingZoneDetailList = packingZoneDetailRepository.findAll();
        assertThat(packingZoneDetailList).hasSize(databaseSizeBeforeUpdate);
        PackingZoneDetail testPackingZoneDetail = packingZoneDetailList.get(packingZoneDetailList.size() - 1);
        assertThat(testPackingZoneDetail.getUicode()).isEqualTo(UPDATED_UICODE);
        assertThat(testPackingZoneDetail.getPdnDate()).isEqualTo(UPDATED_PDN_DATE);
        assertThat(testPackingZoneDetail.getPackageDate()).isEqualTo(UPDATED_PACKAGE_DATE);
        assertThat(testPackingZoneDetail.getWeightReceived()).isEqualTo(UPDATED_WEIGHT_RECEIVED);
        assertThat(testPackingZoneDetail.getWeightBalance()).isEqualTo(UPDATED_WEIGHT_BALANCE);
        assertThat(testPackingZoneDetail.getNumberOfCTNs()).isEqualTo(UPDATED_NUMBER_OF_CT_NS);
        assertThat(testPackingZoneDetail.getReceivedCTNs()).isEqualTo(UPDATED_RECEIVED_CT_NS);
        assertThat(testPackingZoneDetail.getStartCTNNumber()).isEqualTo(UPDATED_START_CTN_NUMBER);
        assertThat(testPackingZoneDetail.getEndCTNNumber()).isEqualTo(UPDATED_END_CTN_NUMBER);
        assertThat(testPackingZoneDetail.getNumberOfCTNsReworked()).isEqualTo(UPDATED_NUMBER_OF_CT_NS_REWORKED);
        assertThat(testPackingZoneDetail.getNumberOfCTNsSold()).isEqualTo(UPDATED_NUMBER_OF_CT_NS_SOLD);
        assertThat(testPackingZoneDetail.getNumberOfCTNsPacked()).isEqualTo(UPDATED_NUMBER_OF_CT_NS_PACKED);
        assertThat(testPackingZoneDetail.getNumberOfCTNsInWarehouse()).isEqualTo(UPDATED_NUMBER_OF_CT_NS_IN_WAREHOUSE);
        assertThat(testPackingZoneDetail.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingPackingZoneDetail() throws Exception {
        int databaseSizeBeforeUpdate = packingZoneDetailRepository.findAll().size();
        packingZoneDetail.setId(count.incrementAndGet());

        // Create the PackingZoneDetail
        PackingZoneDetailDTO packingZoneDetailDTO = packingZoneDetailMapper.toDto(packingZoneDetail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPackingZoneDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, packingZoneDetailDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(packingZoneDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PackingZoneDetail in the database
        List<PackingZoneDetail> packingZoneDetailList = packingZoneDetailRepository.findAll();
        assertThat(packingZoneDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPackingZoneDetail() throws Exception {
        int databaseSizeBeforeUpdate = packingZoneDetailRepository.findAll().size();
        packingZoneDetail.setId(count.incrementAndGet());

        // Create the PackingZoneDetail
        PackingZoneDetailDTO packingZoneDetailDTO = packingZoneDetailMapper.toDto(packingZoneDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPackingZoneDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(packingZoneDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PackingZoneDetail in the database
        List<PackingZoneDetail> packingZoneDetailList = packingZoneDetailRepository.findAll();
        assertThat(packingZoneDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPackingZoneDetail() throws Exception {
        int databaseSizeBeforeUpdate = packingZoneDetailRepository.findAll().size();
        packingZoneDetail.setId(count.incrementAndGet());

        // Create the PackingZoneDetail
        PackingZoneDetailDTO packingZoneDetailDTO = packingZoneDetailMapper.toDto(packingZoneDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPackingZoneDetailMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(packingZoneDetailDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PackingZoneDetail in the database
        List<PackingZoneDetail> packingZoneDetailList = packingZoneDetailRepository.findAll();
        assertThat(packingZoneDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePackingZoneDetailWithPatch() throws Exception {
        // Initialize the database
        packingZoneDetailRepository.saveAndFlush(packingZoneDetail);

        int databaseSizeBeforeUpdate = packingZoneDetailRepository.findAll().size();

        // Update the packingZoneDetail using partial update
        PackingZoneDetail partialUpdatedPackingZoneDetail = new PackingZoneDetail();
        partialUpdatedPackingZoneDetail.setId(packingZoneDetail.getId());

        partialUpdatedPackingZoneDetail
            .pdnDate(UPDATED_PDN_DATE)
            .weightBalance(UPDATED_WEIGHT_BALANCE)
            .receivedCTNs(UPDATED_RECEIVED_CT_NS)
            .startCTNNumber(UPDATED_START_CTN_NUMBER)
            .endCTNNumber(UPDATED_END_CTN_NUMBER);

        restPackingZoneDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPackingZoneDetail.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPackingZoneDetail))
            )
            .andExpect(status().isOk());

        // Validate the PackingZoneDetail in the database
        List<PackingZoneDetail> packingZoneDetailList = packingZoneDetailRepository.findAll();
        assertThat(packingZoneDetailList).hasSize(databaseSizeBeforeUpdate);
        PackingZoneDetail testPackingZoneDetail = packingZoneDetailList.get(packingZoneDetailList.size() - 1);
        assertThat(testPackingZoneDetail.getUicode()).isEqualTo(DEFAULT_UICODE);
        assertThat(testPackingZoneDetail.getPdnDate()).isEqualTo(UPDATED_PDN_DATE);
        assertThat(testPackingZoneDetail.getPackageDate()).isEqualTo(DEFAULT_PACKAGE_DATE);
        assertThat(testPackingZoneDetail.getWeightReceived()).isEqualTo(DEFAULT_WEIGHT_RECEIVED);
        assertThat(testPackingZoneDetail.getWeightBalance()).isEqualTo(UPDATED_WEIGHT_BALANCE);
        assertThat(testPackingZoneDetail.getNumberOfCTNs()).isEqualTo(DEFAULT_NUMBER_OF_CT_NS);
        assertThat(testPackingZoneDetail.getReceivedCTNs()).isEqualTo(UPDATED_RECEIVED_CT_NS);
        assertThat(testPackingZoneDetail.getStartCTNNumber()).isEqualTo(UPDATED_START_CTN_NUMBER);
        assertThat(testPackingZoneDetail.getEndCTNNumber()).isEqualTo(UPDATED_END_CTN_NUMBER);
        assertThat(testPackingZoneDetail.getNumberOfCTNsReworked()).isEqualTo(DEFAULT_NUMBER_OF_CT_NS_REWORKED);
        assertThat(testPackingZoneDetail.getNumberOfCTNsSold()).isEqualTo(DEFAULT_NUMBER_OF_CT_NS_SOLD);
        assertThat(testPackingZoneDetail.getNumberOfCTNsPacked()).isEqualTo(DEFAULT_NUMBER_OF_CT_NS_PACKED);
        assertThat(testPackingZoneDetail.getNumberOfCTNsInWarehouse()).isEqualTo(DEFAULT_NUMBER_OF_CT_NS_IN_WAREHOUSE);
        assertThat(testPackingZoneDetail.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    void fullUpdatePackingZoneDetailWithPatch() throws Exception {
        // Initialize the database
        packingZoneDetailRepository.saveAndFlush(packingZoneDetail);

        int databaseSizeBeforeUpdate = packingZoneDetailRepository.findAll().size();

        // Update the packingZoneDetail using partial update
        PackingZoneDetail partialUpdatedPackingZoneDetail = new PackingZoneDetail();
        partialUpdatedPackingZoneDetail.setId(packingZoneDetail.getId());

        partialUpdatedPackingZoneDetail
            .uicode(UPDATED_UICODE)
            .pdnDate(UPDATED_PDN_DATE)
            .packageDate(UPDATED_PACKAGE_DATE)
            .weightReceived(UPDATED_WEIGHT_RECEIVED)
            .weightBalance(UPDATED_WEIGHT_BALANCE)
            .numberOfCTNs(UPDATED_NUMBER_OF_CT_NS)
            .receivedCTNs(UPDATED_RECEIVED_CT_NS)
            .startCTNNumber(UPDATED_START_CTN_NUMBER)
            .endCTNNumber(UPDATED_END_CTN_NUMBER)
            .numberOfCTNsReworked(UPDATED_NUMBER_OF_CT_NS_REWORKED)
            .numberOfCTNsSold(UPDATED_NUMBER_OF_CT_NS_SOLD)
            .numberOfCTNsPacked(UPDATED_NUMBER_OF_CT_NS_PACKED)
            .numberOfCTNsInWarehouse(UPDATED_NUMBER_OF_CT_NS_IN_WAREHOUSE)
            .createdAt(UPDATED_CREATED_AT);

        restPackingZoneDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPackingZoneDetail.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPackingZoneDetail))
            )
            .andExpect(status().isOk());

        // Validate the PackingZoneDetail in the database
        List<PackingZoneDetail> packingZoneDetailList = packingZoneDetailRepository.findAll();
        assertThat(packingZoneDetailList).hasSize(databaseSizeBeforeUpdate);
        PackingZoneDetail testPackingZoneDetail = packingZoneDetailList.get(packingZoneDetailList.size() - 1);
        assertThat(testPackingZoneDetail.getUicode()).isEqualTo(UPDATED_UICODE);
        assertThat(testPackingZoneDetail.getPdnDate()).isEqualTo(UPDATED_PDN_DATE);
        assertThat(testPackingZoneDetail.getPackageDate()).isEqualTo(UPDATED_PACKAGE_DATE);
        assertThat(testPackingZoneDetail.getWeightReceived()).isEqualTo(UPDATED_WEIGHT_RECEIVED);
        assertThat(testPackingZoneDetail.getWeightBalance()).isEqualTo(UPDATED_WEIGHT_BALANCE);
        assertThat(testPackingZoneDetail.getNumberOfCTNs()).isEqualTo(UPDATED_NUMBER_OF_CT_NS);
        assertThat(testPackingZoneDetail.getReceivedCTNs()).isEqualTo(UPDATED_RECEIVED_CT_NS);
        assertThat(testPackingZoneDetail.getStartCTNNumber()).isEqualTo(UPDATED_START_CTN_NUMBER);
        assertThat(testPackingZoneDetail.getEndCTNNumber()).isEqualTo(UPDATED_END_CTN_NUMBER);
        assertThat(testPackingZoneDetail.getNumberOfCTNsReworked()).isEqualTo(UPDATED_NUMBER_OF_CT_NS_REWORKED);
        assertThat(testPackingZoneDetail.getNumberOfCTNsSold()).isEqualTo(UPDATED_NUMBER_OF_CT_NS_SOLD);
        assertThat(testPackingZoneDetail.getNumberOfCTNsPacked()).isEqualTo(UPDATED_NUMBER_OF_CT_NS_PACKED);
        assertThat(testPackingZoneDetail.getNumberOfCTNsInWarehouse()).isEqualTo(UPDATED_NUMBER_OF_CT_NS_IN_WAREHOUSE);
        assertThat(testPackingZoneDetail.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingPackingZoneDetail() throws Exception {
        int databaseSizeBeforeUpdate = packingZoneDetailRepository.findAll().size();
        packingZoneDetail.setId(count.incrementAndGet());

        // Create the PackingZoneDetail
        PackingZoneDetailDTO packingZoneDetailDTO = packingZoneDetailMapper.toDto(packingZoneDetail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPackingZoneDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, packingZoneDetailDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(packingZoneDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PackingZoneDetail in the database
        List<PackingZoneDetail> packingZoneDetailList = packingZoneDetailRepository.findAll();
        assertThat(packingZoneDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPackingZoneDetail() throws Exception {
        int databaseSizeBeforeUpdate = packingZoneDetailRepository.findAll().size();
        packingZoneDetail.setId(count.incrementAndGet());

        // Create the PackingZoneDetail
        PackingZoneDetailDTO packingZoneDetailDTO = packingZoneDetailMapper.toDto(packingZoneDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPackingZoneDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(packingZoneDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PackingZoneDetail in the database
        List<PackingZoneDetail> packingZoneDetailList = packingZoneDetailRepository.findAll();
        assertThat(packingZoneDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPackingZoneDetail() throws Exception {
        int databaseSizeBeforeUpdate = packingZoneDetailRepository.findAll().size();
        packingZoneDetail.setId(count.incrementAndGet());

        // Create the PackingZoneDetail
        PackingZoneDetailDTO packingZoneDetailDTO = packingZoneDetailMapper.toDto(packingZoneDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPackingZoneDetailMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(packingZoneDetailDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PackingZoneDetail in the database
        List<PackingZoneDetail> packingZoneDetailList = packingZoneDetailRepository.findAll();
        assertThat(packingZoneDetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePackingZoneDetail() throws Exception {
        // Initialize the database
        packingZoneDetailRepository.saveAndFlush(packingZoneDetail);

        int databaseSizeBeforeDelete = packingZoneDetailRepository.findAll().size();

        // Delete the packingZoneDetail
        restPackingZoneDetailMockMvc
            .perform(delete(ENTITY_API_URL_ID, packingZoneDetail.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PackingZoneDetail> packingZoneDetailList = packingZoneDetailRepository.findAll();
        assertThat(packingZoneDetailList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
