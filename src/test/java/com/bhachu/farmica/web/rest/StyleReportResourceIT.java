package com.bhachu.farmica.web.rest;

import static com.bhachu.farmica.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bhachu.farmica.IntegrationTest;
import com.bhachu.farmica.domain.Style;
import com.bhachu.farmica.domain.StyleReport;
import com.bhachu.farmica.repository.StyleReportRepository;
import com.bhachu.farmica.service.dto.StyleReportDTO;
import com.bhachu.farmica.service.mapper.StyleReportMapper;
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
 * Integration tests for the {@link StyleReportResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StyleReportResourceIT {

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_TOTAL_STYLE_IN_WAREHOUSE = 1;
    private static final Integer UPDATED_TOTAL_STYLE_IN_WAREHOUSE = 2;

    private static final Integer DEFAULT_TOTAL_STYLE_IN_SALES = 1;
    private static final Integer UPDATED_TOTAL_STYLE_IN_SALES = 2;

    private static final Integer DEFAULT_TOTAL_STYLE_IN_REWORK = 1;
    private static final Integer UPDATED_TOTAL_STYLE_IN_REWORK = 2;

    private static final Integer DEFAULT_TOTAL_STYLE_IN_PACKING = 1;
    private static final Integer UPDATED_TOTAL_STYLE_IN_PACKING = 2;

    private static final Integer DEFAULT_TOTAL_STYLE = 1;
    private static final Integer UPDATED_TOTAL_STYLE = 2;

    private static final String ENTITY_API_URL = "/api/style-reports";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StyleReportRepository styleReportRepository;

    @Autowired
    private StyleReportMapper styleReportMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStyleReportMockMvc;

    private StyleReport styleReport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StyleReport createEntity(EntityManager em) {
        StyleReport styleReport = new StyleReport()
            .createdAt(DEFAULT_CREATED_AT)
            .totalStyleInWarehouse(DEFAULT_TOTAL_STYLE_IN_WAREHOUSE)
            .totalStyleInSales(DEFAULT_TOTAL_STYLE_IN_SALES)
            .totalStyleInRework(DEFAULT_TOTAL_STYLE_IN_REWORK)
            .totalStyleInPacking(DEFAULT_TOTAL_STYLE_IN_PACKING)
            .totalStyle(DEFAULT_TOTAL_STYLE);
        // Add required entity
        Style style;
        if (TestUtil.findAll(em, Style.class).isEmpty()) {
            style = StyleResourceIT.createEntity(em);
            em.persist(style);
            em.flush();
        } else {
            style = TestUtil.findAll(em, Style.class).get(0);
        }
        styleReport.setStyle(style);
        return styleReport;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StyleReport createUpdatedEntity(EntityManager em) {
        StyleReport styleReport = new StyleReport()
            .createdAt(UPDATED_CREATED_AT)
            .totalStyleInWarehouse(UPDATED_TOTAL_STYLE_IN_WAREHOUSE)
            .totalStyleInSales(UPDATED_TOTAL_STYLE_IN_SALES)
            .totalStyleInRework(UPDATED_TOTAL_STYLE_IN_REWORK)
            .totalStyleInPacking(UPDATED_TOTAL_STYLE_IN_PACKING)
            .totalStyle(UPDATED_TOTAL_STYLE);
        // Add required entity
        Style style;
        if (TestUtil.findAll(em, Style.class).isEmpty()) {
            style = StyleResourceIT.createUpdatedEntity(em);
            em.persist(style);
            em.flush();
        } else {
            style = TestUtil.findAll(em, Style.class).get(0);
        }
        styleReport.setStyle(style);
        return styleReport;
    }

    @BeforeEach
    public void initTest() {
        styleReport = createEntity(em);
    }

    @Test
    @Transactional
    void createStyleReport() throws Exception {
        int databaseSizeBeforeCreate = styleReportRepository.findAll().size();
        // Create the StyleReport
        StyleReportDTO styleReportDTO = styleReportMapper.toDto(styleReport);
        restStyleReportMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(styleReportDTO))
            )
            .andExpect(status().isCreated());

        // Validate the StyleReport in the database
        List<StyleReport> styleReportList = styleReportRepository.findAll();
        assertThat(styleReportList).hasSize(databaseSizeBeforeCreate + 1);
        StyleReport testStyleReport = styleReportList.get(styleReportList.size() - 1);
        assertThat(testStyleReport.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testStyleReport.getTotalStyleInWarehouse()).isEqualTo(DEFAULT_TOTAL_STYLE_IN_WAREHOUSE);
        assertThat(testStyleReport.getTotalStyleInSales()).isEqualTo(DEFAULT_TOTAL_STYLE_IN_SALES);
        assertThat(testStyleReport.getTotalStyleInRework()).isEqualTo(DEFAULT_TOTAL_STYLE_IN_REWORK);
        assertThat(testStyleReport.getTotalStyleInPacking()).isEqualTo(DEFAULT_TOTAL_STYLE_IN_PACKING);
        assertThat(testStyleReport.getTotalStyle()).isEqualTo(DEFAULT_TOTAL_STYLE);
    }

    @Test
    @Transactional
    void createStyleReportWithExistingId() throws Exception {
        // Create the StyleReport with an existing ID
        styleReport.setId(1L);
        StyleReportDTO styleReportDTO = styleReportMapper.toDto(styleReport);

        int databaseSizeBeforeCreate = styleReportRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStyleReportMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(styleReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StyleReport in the database
        List<StyleReport> styleReportList = styleReportRepository.findAll();
        assertThat(styleReportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = styleReportRepository.findAll().size();
        // set the field null
        styleReport.setCreatedAt(null);

        // Create the StyleReport, which fails.
        StyleReportDTO styleReportDTO = styleReportMapper.toDto(styleReport);

        restStyleReportMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(styleReportDTO))
            )
            .andExpect(status().isBadRequest());

        List<StyleReport> styleReportList = styleReportRepository.findAll();
        assertThat(styleReportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStyleReports() throws Exception {
        // Initialize the database
        styleReportRepository.saveAndFlush(styleReport);

        // Get all the styleReportList
        restStyleReportMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(styleReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].totalStyleInWarehouse").value(hasItem(DEFAULT_TOTAL_STYLE_IN_WAREHOUSE)))
            .andExpect(jsonPath("$.[*].totalStyleInSales").value(hasItem(DEFAULT_TOTAL_STYLE_IN_SALES)))
            .andExpect(jsonPath("$.[*].totalStyleInRework").value(hasItem(DEFAULT_TOTAL_STYLE_IN_REWORK)))
            .andExpect(jsonPath("$.[*].totalStyleInPacking").value(hasItem(DEFAULT_TOTAL_STYLE_IN_PACKING)))
            .andExpect(jsonPath("$.[*].totalStyle").value(hasItem(DEFAULT_TOTAL_STYLE)));
    }

    @Test
    @Transactional
    void getStyleReport() throws Exception {
        // Initialize the database
        styleReportRepository.saveAndFlush(styleReport);

        // Get the styleReport
        restStyleReportMockMvc
            .perform(get(ENTITY_API_URL_ID, styleReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(styleReport.getId().intValue()))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.totalStyleInWarehouse").value(DEFAULT_TOTAL_STYLE_IN_WAREHOUSE))
            .andExpect(jsonPath("$.totalStyleInSales").value(DEFAULT_TOTAL_STYLE_IN_SALES))
            .andExpect(jsonPath("$.totalStyleInRework").value(DEFAULT_TOTAL_STYLE_IN_REWORK))
            .andExpect(jsonPath("$.totalStyleInPacking").value(DEFAULT_TOTAL_STYLE_IN_PACKING))
            .andExpect(jsonPath("$.totalStyle").value(DEFAULT_TOTAL_STYLE));
    }

    @Test
    @Transactional
    void getNonExistingStyleReport() throws Exception {
        // Get the styleReport
        restStyleReportMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStyleReport() throws Exception {
        // Initialize the database
        styleReportRepository.saveAndFlush(styleReport);

        int databaseSizeBeforeUpdate = styleReportRepository.findAll().size();

        // Update the styleReport
        StyleReport updatedStyleReport = styleReportRepository.findById(styleReport.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedStyleReport are not directly saved in db
        em.detach(updatedStyleReport);
        updatedStyleReport
            .createdAt(UPDATED_CREATED_AT)
            .totalStyleInWarehouse(UPDATED_TOTAL_STYLE_IN_WAREHOUSE)
            .totalStyleInSales(UPDATED_TOTAL_STYLE_IN_SALES)
            .totalStyleInRework(UPDATED_TOTAL_STYLE_IN_REWORK)
            .totalStyleInPacking(UPDATED_TOTAL_STYLE_IN_PACKING)
            .totalStyle(UPDATED_TOTAL_STYLE);
        StyleReportDTO styleReportDTO = styleReportMapper.toDto(updatedStyleReport);

        restStyleReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, styleReportDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(styleReportDTO))
            )
            .andExpect(status().isOk());

        // Validate the StyleReport in the database
        List<StyleReport> styleReportList = styleReportRepository.findAll();
        assertThat(styleReportList).hasSize(databaseSizeBeforeUpdate);
        StyleReport testStyleReport = styleReportList.get(styleReportList.size() - 1);
        assertThat(testStyleReport.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testStyleReport.getTotalStyleInWarehouse()).isEqualTo(UPDATED_TOTAL_STYLE_IN_WAREHOUSE);
        assertThat(testStyleReport.getTotalStyleInSales()).isEqualTo(UPDATED_TOTAL_STYLE_IN_SALES);
        assertThat(testStyleReport.getTotalStyleInRework()).isEqualTo(UPDATED_TOTAL_STYLE_IN_REWORK);
        assertThat(testStyleReport.getTotalStyleInPacking()).isEqualTo(UPDATED_TOTAL_STYLE_IN_PACKING);
        assertThat(testStyleReport.getTotalStyle()).isEqualTo(UPDATED_TOTAL_STYLE);
    }

    @Test
    @Transactional
    void putNonExistingStyleReport() throws Exception {
        int databaseSizeBeforeUpdate = styleReportRepository.findAll().size();
        styleReport.setId(count.incrementAndGet());

        // Create the StyleReport
        StyleReportDTO styleReportDTO = styleReportMapper.toDto(styleReport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStyleReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, styleReportDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(styleReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StyleReport in the database
        List<StyleReport> styleReportList = styleReportRepository.findAll();
        assertThat(styleReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStyleReport() throws Exception {
        int databaseSizeBeforeUpdate = styleReportRepository.findAll().size();
        styleReport.setId(count.incrementAndGet());

        // Create the StyleReport
        StyleReportDTO styleReportDTO = styleReportMapper.toDto(styleReport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStyleReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(styleReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StyleReport in the database
        List<StyleReport> styleReportList = styleReportRepository.findAll();
        assertThat(styleReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStyleReport() throws Exception {
        int databaseSizeBeforeUpdate = styleReportRepository.findAll().size();
        styleReport.setId(count.incrementAndGet());

        // Create the StyleReport
        StyleReportDTO styleReportDTO = styleReportMapper.toDto(styleReport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStyleReportMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(styleReportDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the StyleReport in the database
        List<StyleReport> styleReportList = styleReportRepository.findAll();
        assertThat(styleReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStyleReportWithPatch() throws Exception {
        // Initialize the database
        styleReportRepository.saveAndFlush(styleReport);

        int databaseSizeBeforeUpdate = styleReportRepository.findAll().size();

        // Update the styleReport using partial update
        StyleReport partialUpdatedStyleReport = new StyleReport();
        partialUpdatedStyleReport.setId(styleReport.getId());

        partialUpdatedStyleReport
            .totalStyleInSales(UPDATED_TOTAL_STYLE_IN_SALES)
            .totalStyleInRework(UPDATED_TOTAL_STYLE_IN_REWORK)
            .totalStyleInPacking(UPDATED_TOTAL_STYLE_IN_PACKING);

        restStyleReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStyleReport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStyleReport))
            )
            .andExpect(status().isOk());

        // Validate the StyleReport in the database
        List<StyleReport> styleReportList = styleReportRepository.findAll();
        assertThat(styleReportList).hasSize(databaseSizeBeforeUpdate);
        StyleReport testStyleReport = styleReportList.get(styleReportList.size() - 1);
        assertThat(testStyleReport.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testStyleReport.getTotalStyleInWarehouse()).isEqualTo(DEFAULT_TOTAL_STYLE_IN_WAREHOUSE);
        assertThat(testStyleReport.getTotalStyleInSales()).isEqualTo(UPDATED_TOTAL_STYLE_IN_SALES);
        assertThat(testStyleReport.getTotalStyleInRework()).isEqualTo(UPDATED_TOTAL_STYLE_IN_REWORK);
        assertThat(testStyleReport.getTotalStyleInPacking()).isEqualTo(UPDATED_TOTAL_STYLE_IN_PACKING);
        assertThat(testStyleReport.getTotalStyle()).isEqualTo(DEFAULT_TOTAL_STYLE);
    }

    @Test
    @Transactional
    void fullUpdateStyleReportWithPatch() throws Exception {
        // Initialize the database
        styleReportRepository.saveAndFlush(styleReport);

        int databaseSizeBeforeUpdate = styleReportRepository.findAll().size();

        // Update the styleReport using partial update
        StyleReport partialUpdatedStyleReport = new StyleReport();
        partialUpdatedStyleReport.setId(styleReport.getId());

        partialUpdatedStyleReport
            .createdAt(UPDATED_CREATED_AT)
            .totalStyleInWarehouse(UPDATED_TOTAL_STYLE_IN_WAREHOUSE)
            .totalStyleInSales(UPDATED_TOTAL_STYLE_IN_SALES)
            .totalStyleInRework(UPDATED_TOTAL_STYLE_IN_REWORK)
            .totalStyleInPacking(UPDATED_TOTAL_STYLE_IN_PACKING)
            .totalStyle(UPDATED_TOTAL_STYLE);

        restStyleReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStyleReport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStyleReport))
            )
            .andExpect(status().isOk());

        // Validate the StyleReport in the database
        List<StyleReport> styleReportList = styleReportRepository.findAll();
        assertThat(styleReportList).hasSize(databaseSizeBeforeUpdate);
        StyleReport testStyleReport = styleReportList.get(styleReportList.size() - 1);
        assertThat(testStyleReport.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testStyleReport.getTotalStyleInWarehouse()).isEqualTo(UPDATED_TOTAL_STYLE_IN_WAREHOUSE);
        assertThat(testStyleReport.getTotalStyleInSales()).isEqualTo(UPDATED_TOTAL_STYLE_IN_SALES);
        assertThat(testStyleReport.getTotalStyleInRework()).isEqualTo(UPDATED_TOTAL_STYLE_IN_REWORK);
        assertThat(testStyleReport.getTotalStyleInPacking()).isEqualTo(UPDATED_TOTAL_STYLE_IN_PACKING);
        assertThat(testStyleReport.getTotalStyle()).isEqualTo(UPDATED_TOTAL_STYLE);
    }

    @Test
    @Transactional
    void patchNonExistingStyleReport() throws Exception {
        int databaseSizeBeforeUpdate = styleReportRepository.findAll().size();
        styleReport.setId(count.incrementAndGet());

        // Create the StyleReport
        StyleReportDTO styleReportDTO = styleReportMapper.toDto(styleReport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStyleReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, styleReportDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(styleReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StyleReport in the database
        List<StyleReport> styleReportList = styleReportRepository.findAll();
        assertThat(styleReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStyleReport() throws Exception {
        int databaseSizeBeforeUpdate = styleReportRepository.findAll().size();
        styleReport.setId(count.incrementAndGet());

        // Create the StyleReport
        StyleReportDTO styleReportDTO = styleReportMapper.toDto(styleReport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStyleReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(styleReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StyleReport in the database
        List<StyleReport> styleReportList = styleReportRepository.findAll();
        assertThat(styleReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStyleReport() throws Exception {
        int databaseSizeBeforeUpdate = styleReportRepository.findAll().size();
        styleReport.setId(count.incrementAndGet());

        // Create the StyleReport
        StyleReportDTO styleReportDTO = styleReportMapper.toDto(styleReport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStyleReportMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(styleReportDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StyleReport in the database
        List<StyleReport> styleReportList = styleReportRepository.findAll();
        assertThat(styleReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStyleReport() throws Exception {
        // Initialize the database
        styleReportRepository.saveAndFlush(styleReport);

        int databaseSizeBeforeDelete = styleReportRepository.findAll().size();

        // Delete the styleReport
        restStyleReportMockMvc
            .perform(delete(ENTITY_API_URL_ID, styleReport.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StyleReport> styleReportList = styleReportRepository.findAll();
        assertThat(styleReportList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
