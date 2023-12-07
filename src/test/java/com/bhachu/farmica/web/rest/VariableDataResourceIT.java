package com.bhachu.farmica.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bhachu.farmica.IntegrationTest;
import com.bhachu.farmica.domain.VariableData;
import com.bhachu.farmica.repository.VariableDataRepository;
import com.bhachu.farmica.service.dto.VariableDataDTO;
import com.bhachu.farmica.service.mapper.VariableDataMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link VariableDataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VariableDataResourceIT {

    private static final Integer DEFAULT_ACCUMULATION = 1;
    private static final Integer UPDATED_ACCUMULATION = 2;

    private static final String DEFAULT_AI_ACCESS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_AI_ACCESS_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/variable-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VariableDataRepository variableDataRepository;

    @Autowired
    private VariableDataMapper variableDataMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVariableDataMockMvc;

    private VariableData variableData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VariableData createEntity(EntityManager em) {
        VariableData variableData = new VariableData().accumulation(DEFAULT_ACCUMULATION).aiAccessCode(DEFAULT_AI_ACCESS_CODE);
        return variableData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VariableData createUpdatedEntity(EntityManager em) {
        VariableData variableData = new VariableData().accumulation(UPDATED_ACCUMULATION).aiAccessCode(UPDATED_AI_ACCESS_CODE);
        return variableData;
    }

    @BeforeEach
    public void initTest() {
        variableData = createEntity(em);
    }

    @Test
    @Transactional
    void createVariableData() throws Exception {
        int databaseSizeBeforeCreate = variableDataRepository.findAll().size();
        // Create the VariableData
        VariableDataDTO variableDataDTO = variableDataMapper.toDto(variableData);
        restVariableDataMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(variableDataDTO))
            )
            .andExpect(status().isCreated());

        // Validate the VariableData in the database
        List<VariableData> variableDataList = variableDataRepository.findAll();
        assertThat(variableDataList).hasSize(databaseSizeBeforeCreate + 1);
        VariableData testVariableData = variableDataList.get(variableDataList.size() - 1);
        assertThat(testVariableData.getAccumulation()).isEqualTo(DEFAULT_ACCUMULATION);
        assertThat(testVariableData.getAiAccessCode()).isEqualTo(DEFAULT_AI_ACCESS_CODE);
    }

    @Test
    @Transactional
    void createVariableDataWithExistingId() throws Exception {
        // Create the VariableData with an existing ID
        variableData.setId(1L);
        VariableDataDTO variableDataDTO = variableDataMapper.toDto(variableData);

        int databaseSizeBeforeCreate = variableDataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVariableDataMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(variableDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VariableData in the database
        List<VariableData> variableDataList = variableDataRepository.findAll();
        assertThat(variableDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVariableData() throws Exception {
        // Initialize the database
        variableDataRepository.saveAndFlush(variableData);

        // Get all the variableDataList
        restVariableDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(variableData.getId().intValue())))
            .andExpect(jsonPath("$.[*].accumulation").value(hasItem(DEFAULT_ACCUMULATION)))
            .andExpect(jsonPath("$.[*].aiAccessCode").value(hasItem(DEFAULT_AI_ACCESS_CODE)));
    }

    @Test
    @Transactional
    void getVariableData() throws Exception {
        // Initialize the database
        variableDataRepository.saveAndFlush(variableData);

        // Get the variableData
        restVariableDataMockMvc
            .perform(get(ENTITY_API_URL_ID, variableData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(variableData.getId().intValue()))
            .andExpect(jsonPath("$.accumulation").value(DEFAULT_ACCUMULATION))
            .andExpect(jsonPath("$.aiAccessCode").value(DEFAULT_AI_ACCESS_CODE));
    }

    @Test
    @Transactional
    void getNonExistingVariableData() throws Exception {
        // Get the variableData
        restVariableDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVariableData() throws Exception {
        // Initialize the database
        variableDataRepository.saveAndFlush(variableData);

        int databaseSizeBeforeUpdate = variableDataRepository.findAll().size();

        // Update the variableData
        VariableData updatedVariableData = variableDataRepository.findById(variableData.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVariableData are not directly saved in db
        em.detach(updatedVariableData);
        updatedVariableData.accumulation(UPDATED_ACCUMULATION).aiAccessCode(UPDATED_AI_ACCESS_CODE);
        VariableDataDTO variableDataDTO = variableDataMapper.toDto(updatedVariableData);

        restVariableDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, variableDataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(variableDataDTO))
            )
            .andExpect(status().isOk());

        // Validate the VariableData in the database
        List<VariableData> variableDataList = variableDataRepository.findAll();
        assertThat(variableDataList).hasSize(databaseSizeBeforeUpdate);
        VariableData testVariableData = variableDataList.get(variableDataList.size() - 1);
        assertThat(testVariableData.getAccumulation()).isEqualTo(UPDATED_ACCUMULATION);
        assertThat(testVariableData.getAiAccessCode()).isEqualTo(UPDATED_AI_ACCESS_CODE);
    }

    @Test
    @Transactional
    void putNonExistingVariableData() throws Exception {
        int databaseSizeBeforeUpdate = variableDataRepository.findAll().size();
        variableData.setId(count.incrementAndGet());

        // Create the VariableData
        VariableDataDTO variableDataDTO = variableDataMapper.toDto(variableData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVariableDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, variableDataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(variableDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VariableData in the database
        List<VariableData> variableDataList = variableDataRepository.findAll();
        assertThat(variableDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVariableData() throws Exception {
        int databaseSizeBeforeUpdate = variableDataRepository.findAll().size();
        variableData.setId(count.incrementAndGet());

        // Create the VariableData
        VariableDataDTO variableDataDTO = variableDataMapper.toDto(variableData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVariableDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(variableDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VariableData in the database
        List<VariableData> variableDataList = variableDataRepository.findAll();
        assertThat(variableDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVariableData() throws Exception {
        int databaseSizeBeforeUpdate = variableDataRepository.findAll().size();
        variableData.setId(count.incrementAndGet());

        // Create the VariableData
        VariableDataDTO variableDataDTO = variableDataMapper.toDto(variableData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVariableDataMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(variableDataDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VariableData in the database
        List<VariableData> variableDataList = variableDataRepository.findAll();
        assertThat(variableDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVariableDataWithPatch() throws Exception {
        // Initialize the database
        variableDataRepository.saveAndFlush(variableData);

        int databaseSizeBeforeUpdate = variableDataRepository.findAll().size();

        // Update the variableData using partial update
        VariableData partialUpdatedVariableData = new VariableData();
        partialUpdatedVariableData.setId(variableData.getId());

        partialUpdatedVariableData.accumulation(UPDATED_ACCUMULATION);

        restVariableDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVariableData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVariableData))
            )
            .andExpect(status().isOk());

        // Validate the VariableData in the database
        List<VariableData> variableDataList = variableDataRepository.findAll();
        assertThat(variableDataList).hasSize(databaseSizeBeforeUpdate);
        VariableData testVariableData = variableDataList.get(variableDataList.size() - 1);
        assertThat(testVariableData.getAccumulation()).isEqualTo(UPDATED_ACCUMULATION);
        assertThat(testVariableData.getAiAccessCode()).isEqualTo(DEFAULT_AI_ACCESS_CODE);
    }

    @Test
    @Transactional
    void fullUpdateVariableDataWithPatch() throws Exception {
        // Initialize the database
        variableDataRepository.saveAndFlush(variableData);

        int databaseSizeBeforeUpdate = variableDataRepository.findAll().size();

        // Update the variableData using partial update
        VariableData partialUpdatedVariableData = new VariableData();
        partialUpdatedVariableData.setId(variableData.getId());

        partialUpdatedVariableData.accumulation(UPDATED_ACCUMULATION).aiAccessCode(UPDATED_AI_ACCESS_CODE);

        restVariableDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVariableData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVariableData))
            )
            .andExpect(status().isOk());

        // Validate the VariableData in the database
        List<VariableData> variableDataList = variableDataRepository.findAll();
        assertThat(variableDataList).hasSize(databaseSizeBeforeUpdate);
        VariableData testVariableData = variableDataList.get(variableDataList.size() - 1);
        assertThat(testVariableData.getAccumulation()).isEqualTo(UPDATED_ACCUMULATION);
        assertThat(testVariableData.getAiAccessCode()).isEqualTo(UPDATED_AI_ACCESS_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingVariableData() throws Exception {
        int databaseSizeBeforeUpdate = variableDataRepository.findAll().size();
        variableData.setId(count.incrementAndGet());

        // Create the VariableData
        VariableDataDTO variableDataDTO = variableDataMapper.toDto(variableData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVariableDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, variableDataDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(variableDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VariableData in the database
        List<VariableData> variableDataList = variableDataRepository.findAll();
        assertThat(variableDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVariableData() throws Exception {
        int databaseSizeBeforeUpdate = variableDataRepository.findAll().size();
        variableData.setId(count.incrementAndGet());

        // Create the VariableData
        VariableDataDTO variableDataDTO = variableDataMapper.toDto(variableData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVariableDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(variableDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VariableData in the database
        List<VariableData> variableDataList = variableDataRepository.findAll();
        assertThat(variableDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVariableData() throws Exception {
        int databaseSizeBeforeUpdate = variableDataRepository.findAll().size();
        variableData.setId(count.incrementAndGet());

        // Create the VariableData
        VariableDataDTO variableDataDTO = variableDataMapper.toDto(variableData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVariableDataMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(variableDataDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VariableData in the database
        List<VariableData> variableDataList = variableDataRepository.findAll();
        assertThat(variableDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVariableData() throws Exception {
        // Initialize the database
        variableDataRepository.saveAndFlush(variableData);

        int databaseSizeBeforeDelete = variableDataRepository.findAll().size();

        // Delete the variableData
        restVariableDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, variableData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VariableData> variableDataList = variableDataRepository.findAll();
        assertThat(variableDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
