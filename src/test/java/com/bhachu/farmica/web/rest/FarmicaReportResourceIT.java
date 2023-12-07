package com.bhachu.farmica.web.rest;

import static com.bhachu.farmica.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bhachu.farmica.IntegrationTest;
import com.bhachu.farmica.domain.FarmicaReport;
import com.bhachu.farmica.repository.FarmicaReportRepository;
import com.bhachu.farmica.service.dto.FarmicaReportDTO;
import com.bhachu.farmica.service.mapper.FarmicaReportMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FarmicaReportResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FarmicaReportResourceIT {

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_TOTAL_ITEMS_IN_WAREHOUSE = 1;
    private static final Integer UPDATED_TOTAL_ITEMS_IN_WAREHOUSE = 2;

    private static final Integer DEFAULT_TOTAL_ITEMS_IN_SALES = 1;
    private static final Integer UPDATED_TOTAL_ITEMS_IN_SALES = 2;

    private static final Integer DEFAULT_TOTAL_ITEMS_IN_REWORK = 1;
    private static final Integer UPDATED_TOTAL_ITEMS_IN_REWORK = 2;

    private static final Integer DEFAULT_TOTAL_ITEMS_IN_PACKING = 1;
    private static final Integer UPDATED_TOTAL_ITEMS_IN_PACKING = 2;

    private static final Integer DEFAULT_TOTAL_ITEMS = 1;
    private static final Integer UPDATED_TOTAL_ITEMS = 2;

    private static final String ENTITY_API_URL = "/api/farmica-reports";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FarmicaReportRepository farmicaReportRepository;

    @Autowired
    private FarmicaReportMapper farmicaReportMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFarmicaReportMockMvc;

    private FarmicaReport farmicaReport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FarmicaReport createEntity(EntityManager em) {
        FarmicaReport farmicaReport = new FarmicaReport()
            .createdAt(DEFAULT_CREATED_AT)
            .totalItemsInWarehouse(DEFAULT_TOTAL_ITEMS_IN_WAREHOUSE)
            .totalItemsInSales(DEFAULT_TOTAL_ITEMS_IN_SALES)
            .totalItemsInRework(DEFAULT_TOTAL_ITEMS_IN_REWORK)
            .totalItemsInPacking(DEFAULT_TOTAL_ITEMS_IN_PACKING)
            .totalItems(DEFAULT_TOTAL_ITEMS);
        return farmicaReport;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FarmicaReport createUpdatedEntity(EntityManager em) {
        FarmicaReport farmicaReport = new FarmicaReport()
            .createdAt(UPDATED_CREATED_AT)
            .totalItemsInWarehouse(UPDATED_TOTAL_ITEMS_IN_WAREHOUSE)
            .totalItemsInSales(UPDATED_TOTAL_ITEMS_IN_SALES)
            .totalItemsInRework(UPDATED_TOTAL_ITEMS_IN_REWORK)
            .totalItemsInPacking(UPDATED_TOTAL_ITEMS_IN_PACKING)
            .totalItems(UPDATED_TOTAL_ITEMS);
        return farmicaReport;
    }

    @BeforeEach
    public void initTest() {
        farmicaReport = createEntity(em);
    }

    @Test
    @Transactional
    void createFarmicaReport() throws Exception {
        int databaseSizeBeforeCreate = farmicaReportRepository.findAll().size();
        // Create the FarmicaReport
        FarmicaReportDTO farmicaReportDTO = farmicaReportMapper.toDto(farmicaReport);
        restFarmicaReportMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(farmicaReportDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FarmicaReport in the database
        List<FarmicaReport> farmicaReportList = farmicaReportRepository.findAll();
        assertThat(farmicaReportList).hasSize(databaseSizeBeforeCreate + 1);
        FarmicaReport testFarmicaReport = farmicaReportList.get(farmicaReportList.size() - 1);
        assertThat(testFarmicaReport.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testFarmicaReport.getTotalItemsInWarehouse()).isEqualTo(DEFAULT_TOTAL_ITEMS_IN_WAREHOUSE);
        assertThat(testFarmicaReport.getTotalItemsInSales()).isEqualTo(DEFAULT_TOTAL_ITEMS_IN_SALES);
        assertThat(testFarmicaReport.getTotalItemsInRework()).isEqualTo(DEFAULT_TOTAL_ITEMS_IN_REWORK);
        assertThat(testFarmicaReport.getTotalItemsInPacking()).isEqualTo(DEFAULT_TOTAL_ITEMS_IN_PACKING);
        assertThat(testFarmicaReport.getTotalItems()).isEqualTo(DEFAULT_TOTAL_ITEMS);
    }

    @Test
    @Transactional
    void createFarmicaReportWithExistingId() throws Exception {
        // Create the FarmicaReport with an existing ID
        farmicaReport.setId(1L);
        FarmicaReportDTO farmicaReportDTO = farmicaReportMapper.toDto(farmicaReport);

        int databaseSizeBeforeCreate = farmicaReportRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFarmicaReportMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(farmicaReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FarmicaReport in the database
        List<FarmicaReport> farmicaReportList = farmicaReportRepository.findAll();
        assertThat(farmicaReportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = farmicaReportRepository.findAll().size();
        // set the field null
        farmicaReport.setCreatedAt(null);

        // Create the FarmicaReport, which fails.
        FarmicaReportDTO farmicaReportDTO = farmicaReportMapper.toDto(farmicaReport);

        restFarmicaReportMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(farmicaReportDTO))
            )
            .andExpect(status().isBadRequest());

        List<FarmicaReport> farmicaReportList = farmicaReportRepository.findAll();
        assertThat(farmicaReportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalItemsInWarehouseIsRequired() throws Exception {
        int databaseSizeBeforeTest = farmicaReportRepository.findAll().size();
        // set the field null
        farmicaReport.setTotalItemsInWarehouse(null);

        // Create the FarmicaReport, which fails.
        FarmicaReportDTO farmicaReportDTO = farmicaReportMapper.toDto(farmicaReport);

        restFarmicaReportMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(farmicaReportDTO))
            )
            .andExpect(status().isBadRequest());

        List<FarmicaReport> farmicaReportList = farmicaReportRepository.findAll();
        assertThat(farmicaReportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalItemsInSalesIsRequired() throws Exception {
        int databaseSizeBeforeTest = farmicaReportRepository.findAll().size();
        // set the field null
        farmicaReport.setTotalItemsInSales(null);

        // Create the FarmicaReport, which fails.
        FarmicaReportDTO farmicaReportDTO = farmicaReportMapper.toDto(farmicaReport);

        restFarmicaReportMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(farmicaReportDTO))
            )
            .andExpect(status().isBadRequest());

        List<FarmicaReport> farmicaReportList = farmicaReportRepository.findAll();
        assertThat(farmicaReportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalItemsInReworkIsRequired() throws Exception {
        int databaseSizeBeforeTest = farmicaReportRepository.findAll().size();
        // set the field null
        farmicaReport.setTotalItemsInRework(null);

        // Create the FarmicaReport, which fails.
        FarmicaReportDTO farmicaReportDTO = farmicaReportMapper.toDto(farmicaReport);

        restFarmicaReportMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(farmicaReportDTO))
            )
            .andExpect(status().isBadRequest());

        List<FarmicaReport> farmicaReportList = farmicaReportRepository.findAll();
        assertThat(farmicaReportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalItemsInPackingIsRequired() throws Exception {
        int databaseSizeBeforeTest = farmicaReportRepository.findAll().size();
        // set the field null
        farmicaReport.setTotalItemsInPacking(null);

        // Create the FarmicaReport, which fails.
        FarmicaReportDTO farmicaReportDTO = farmicaReportMapper.toDto(farmicaReport);

        restFarmicaReportMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(farmicaReportDTO))
            )
            .andExpect(status().isBadRequest());

        List<FarmicaReport> farmicaReportList = farmicaReportRepository.findAll();
        assertThat(farmicaReportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalItemsIsRequired() throws Exception {
        int databaseSizeBeforeTest = farmicaReportRepository.findAll().size();
        // set the field null
        farmicaReport.setTotalItems(null);

        // Create the FarmicaReport, which fails.
        FarmicaReportDTO farmicaReportDTO = farmicaReportMapper.toDto(farmicaReport);

        restFarmicaReportMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(farmicaReportDTO))
            )
            .andExpect(status().isBadRequest());

        List<FarmicaReport> farmicaReportList = farmicaReportRepository.findAll();
        assertThat(farmicaReportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFarmicaReports() throws Exception {
        // Initialize the database
        farmicaReportRepository.saveAndFlush(farmicaReport);

        // Get all the farmicaReportList
        restFarmicaReportMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(farmicaReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].totalItemsInWarehouse").value(hasItem(DEFAULT_TOTAL_ITEMS_IN_WAREHOUSE)))
            .andExpect(jsonPath("$.[*].totalItemsInSales").value(hasItem(DEFAULT_TOTAL_ITEMS_IN_SALES)))
            .andExpect(jsonPath("$.[*].totalItemsInRework").value(hasItem(DEFAULT_TOTAL_ITEMS_IN_REWORK)))
            .andExpect(jsonPath("$.[*].totalItemsInPacking").value(hasItem(DEFAULT_TOTAL_ITEMS_IN_PACKING)))
            .andExpect(jsonPath("$.[*].totalItems").value(hasItem(DEFAULT_TOTAL_ITEMS)));
    }

    @Test
    @Transactional
    void getFarmicaReport() throws Exception {
        // Initialize the database
        farmicaReportRepository.saveAndFlush(farmicaReport);

        // Get the farmicaReport
        restFarmicaReportMockMvc
            .perform(get(ENTITY_API_URL_ID, farmicaReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(farmicaReport.getId().intValue()))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.totalItemsInWarehouse").value(DEFAULT_TOTAL_ITEMS_IN_WAREHOUSE))
            .andExpect(jsonPath("$.totalItemsInSales").value(DEFAULT_TOTAL_ITEMS_IN_SALES))
            .andExpect(jsonPath("$.totalItemsInRework").value(DEFAULT_TOTAL_ITEMS_IN_REWORK))
            .andExpect(jsonPath("$.totalItemsInPacking").value(DEFAULT_TOTAL_ITEMS_IN_PACKING))
            .andExpect(jsonPath("$.totalItems").value(DEFAULT_TOTAL_ITEMS));
    }

    @Test
    @Transactional
    void getNonExistingFarmicaReport() throws Exception {
        // Get the farmicaReport
        restFarmicaReportMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFarmicaReport() throws Exception {
        // Initialize the database
        farmicaReportRepository.saveAndFlush(farmicaReport);

        int databaseSizeBeforeUpdate = farmicaReportRepository.findAll().size();

        // Update the farmicaReport
        FarmicaReport updatedFarmicaReport = farmicaReportRepository.findById(farmicaReport.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFarmicaReport are not directly saved in db
        em.detach(updatedFarmicaReport);
        updatedFarmicaReport
            .createdAt(UPDATED_CREATED_AT)
            .totalItemsInWarehouse(UPDATED_TOTAL_ITEMS_IN_WAREHOUSE)
            .totalItemsInSales(UPDATED_TOTAL_ITEMS_IN_SALES)
            .totalItemsInRework(UPDATED_TOTAL_ITEMS_IN_REWORK)
            .totalItemsInPacking(UPDATED_TOTAL_ITEMS_IN_PACKING)
            .totalItems(UPDATED_TOTAL_ITEMS);
        FarmicaReportDTO farmicaReportDTO = farmicaReportMapper.toDto(updatedFarmicaReport);

        restFarmicaReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, farmicaReportDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(farmicaReportDTO))
            )
            .andExpect(status().isOk());

        // Validate the FarmicaReport in the database
        List<FarmicaReport> farmicaReportList = farmicaReportRepository.findAll();
        assertThat(farmicaReportList).hasSize(databaseSizeBeforeUpdate);
        FarmicaReport testFarmicaReport = farmicaReportList.get(farmicaReportList.size() - 1);
        assertThat(testFarmicaReport.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testFarmicaReport.getTotalItemsInWarehouse()).isEqualTo(UPDATED_TOTAL_ITEMS_IN_WAREHOUSE);
        assertThat(testFarmicaReport.getTotalItemsInSales()).isEqualTo(UPDATED_TOTAL_ITEMS_IN_SALES);
        assertThat(testFarmicaReport.getTotalItemsInRework()).isEqualTo(UPDATED_TOTAL_ITEMS_IN_REWORK);
        assertThat(testFarmicaReport.getTotalItemsInPacking()).isEqualTo(UPDATED_TOTAL_ITEMS_IN_PACKING);
        assertThat(testFarmicaReport.getTotalItems()).isEqualTo(UPDATED_TOTAL_ITEMS);
    }

    @Test
    @Transactional
    void putNonExistingFarmicaReport() throws Exception {
        int databaseSizeBeforeUpdate = farmicaReportRepository.findAll().size();
        farmicaReport.setId(count.incrementAndGet());

        // Create the FarmicaReport
        FarmicaReportDTO farmicaReportDTO = farmicaReportMapper.toDto(farmicaReport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFarmicaReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, farmicaReportDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(farmicaReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FarmicaReport in the database
        List<FarmicaReport> farmicaReportList = farmicaReportRepository.findAll();
        assertThat(farmicaReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFarmicaReport() throws Exception {
        int databaseSizeBeforeUpdate = farmicaReportRepository.findAll().size();
        farmicaReport.setId(count.incrementAndGet());

        // Create the FarmicaReport
        FarmicaReportDTO farmicaReportDTO = farmicaReportMapper.toDto(farmicaReport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFarmicaReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(farmicaReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FarmicaReport in the database
        List<FarmicaReport> farmicaReportList = farmicaReportRepository.findAll();
        assertThat(farmicaReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFarmicaReport() throws Exception {
        int databaseSizeBeforeUpdate = farmicaReportRepository.findAll().size();
        farmicaReport.setId(count.incrementAndGet());

        // Create the FarmicaReport
        FarmicaReportDTO farmicaReportDTO = farmicaReportMapper.toDto(farmicaReport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFarmicaReportMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(farmicaReportDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FarmicaReport in the database
        List<FarmicaReport> farmicaReportList = farmicaReportRepository.findAll();
        assertThat(farmicaReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFarmicaReportWithPatch() throws Exception {
        // Initialize the database
        farmicaReportRepository.saveAndFlush(farmicaReport);

        int databaseSizeBeforeUpdate = farmicaReportRepository.findAll().size();

        // Update the farmicaReport using partial update
        FarmicaReport partialUpdatedFarmicaReport = new FarmicaReport();
        partialUpdatedFarmicaReport.setId(farmicaReport.getId());

        partialUpdatedFarmicaReport
            .createdAt(UPDATED_CREATED_AT)
            .totalItemsInWarehouse(UPDATED_TOTAL_ITEMS_IN_WAREHOUSE)
            .totalItemsInRework(UPDATED_TOTAL_ITEMS_IN_REWORK)
            .totalItemsInPacking(UPDATED_TOTAL_ITEMS_IN_PACKING)
            .totalItems(UPDATED_TOTAL_ITEMS);

        restFarmicaReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFarmicaReport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFarmicaReport))
            )
            .andExpect(status().isOk());

        // Validate the FarmicaReport in the database
        List<FarmicaReport> farmicaReportList = farmicaReportRepository.findAll();
        assertThat(farmicaReportList).hasSize(databaseSizeBeforeUpdate);
        FarmicaReport testFarmicaReport = farmicaReportList.get(farmicaReportList.size() - 1);
        assertThat(testFarmicaReport.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testFarmicaReport.getTotalItemsInWarehouse()).isEqualTo(UPDATED_TOTAL_ITEMS_IN_WAREHOUSE);
        assertThat(testFarmicaReport.getTotalItemsInSales()).isEqualTo(DEFAULT_TOTAL_ITEMS_IN_SALES);
        assertThat(testFarmicaReport.getTotalItemsInRework()).isEqualTo(UPDATED_TOTAL_ITEMS_IN_REWORK);
        assertThat(testFarmicaReport.getTotalItemsInPacking()).isEqualTo(UPDATED_TOTAL_ITEMS_IN_PACKING);
        assertThat(testFarmicaReport.getTotalItems()).isEqualTo(UPDATED_TOTAL_ITEMS);
    }

    @Test
    @Transactional
    void fullUpdateFarmicaReportWithPatch() throws Exception {
        // Initialize the database
        farmicaReportRepository.saveAndFlush(farmicaReport);

        int databaseSizeBeforeUpdate = farmicaReportRepository.findAll().size();

        // Update the farmicaReport using partial update
        FarmicaReport partialUpdatedFarmicaReport = new FarmicaReport();
        partialUpdatedFarmicaReport.setId(farmicaReport.getId());

        partialUpdatedFarmicaReport
            .createdAt(UPDATED_CREATED_AT)
            .totalItemsInWarehouse(UPDATED_TOTAL_ITEMS_IN_WAREHOUSE)
            .totalItemsInSales(UPDATED_TOTAL_ITEMS_IN_SALES)
            .totalItemsInRework(UPDATED_TOTAL_ITEMS_IN_REWORK)
            .totalItemsInPacking(UPDATED_TOTAL_ITEMS_IN_PACKING)
            .totalItems(UPDATED_TOTAL_ITEMS);

        restFarmicaReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFarmicaReport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFarmicaReport))
            )
            .andExpect(status().isOk());

        // Validate the FarmicaReport in the database
        List<FarmicaReport> farmicaReportList = farmicaReportRepository.findAll();
        assertThat(farmicaReportList).hasSize(databaseSizeBeforeUpdate);
        FarmicaReport testFarmicaReport = farmicaReportList.get(farmicaReportList.size() - 1);
        assertThat(testFarmicaReport.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testFarmicaReport.getTotalItemsInWarehouse()).isEqualTo(UPDATED_TOTAL_ITEMS_IN_WAREHOUSE);
        assertThat(testFarmicaReport.getTotalItemsInSales()).isEqualTo(UPDATED_TOTAL_ITEMS_IN_SALES);
        assertThat(testFarmicaReport.getTotalItemsInRework()).isEqualTo(UPDATED_TOTAL_ITEMS_IN_REWORK);
        assertThat(testFarmicaReport.getTotalItemsInPacking()).isEqualTo(UPDATED_TOTAL_ITEMS_IN_PACKING);
        assertThat(testFarmicaReport.getTotalItems()).isEqualTo(UPDATED_TOTAL_ITEMS);
    }

    @Test
    @Transactional
    void patchNonExistingFarmicaReport() throws Exception {
        int databaseSizeBeforeUpdate = farmicaReportRepository.findAll().size();
        farmicaReport.setId(count.incrementAndGet());

        // Create the FarmicaReport
        FarmicaReportDTO farmicaReportDTO = farmicaReportMapper.toDto(farmicaReport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFarmicaReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, farmicaReportDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(farmicaReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FarmicaReport in the database
        List<FarmicaReport> farmicaReportList = farmicaReportRepository.findAll();
        assertThat(farmicaReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFarmicaReport() throws Exception {
        int databaseSizeBeforeUpdate = farmicaReportRepository.findAll().size();
        farmicaReport.setId(count.incrementAndGet());

        // Create the FarmicaReport
        FarmicaReportDTO farmicaReportDTO = farmicaReportMapper.toDto(farmicaReport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFarmicaReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(farmicaReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FarmicaReport in the database
        List<FarmicaReport> farmicaReportList = farmicaReportRepository.findAll();
        assertThat(farmicaReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFarmicaReport() throws Exception {
        int databaseSizeBeforeUpdate = farmicaReportRepository.findAll().size();
        farmicaReport.setId(count.incrementAndGet());

        // Create the FarmicaReport
        FarmicaReportDTO farmicaReportDTO = farmicaReportMapper.toDto(farmicaReport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFarmicaReportMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(farmicaReportDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FarmicaReport in the database
        List<FarmicaReport> farmicaReportList = farmicaReportRepository.findAll();
        assertThat(farmicaReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFarmicaReport() throws Exception {
        // Initialize the database
        farmicaReportRepository.saveAndFlush(farmicaReport);

        int databaseSizeBeforeDelete = farmicaReportRepository.findAll().size();

        // Delete the farmicaReport
        restFarmicaReportMockMvc
            .perform(delete(ENTITY_API_URL_ID, farmicaReport.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FarmicaReport> farmicaReportList = farmicaReportRepository.findAll();
        assertThat(farmicaReportList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
