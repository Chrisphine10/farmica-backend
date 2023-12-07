package com.bhachu.farmica.web.rest;

import com.bhachu.farmica.repository.VariableDataRepository;
import com.bhachu.farmica.service.VariableDataService;
import com.bhachu.farmica.service.dto.VariableDataDTO;
import com.bhachu.farmica.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.bhachu.farmica.domain.VariableData}.
 */
@RestController
@RequestMapping("/api")
public class VariableDataResource {

    private final Logger log = LoggerFactory.getLogger(VariableDataResource.class);

    private static final String ENTITY_NAME = "variableData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VariableDataService variableDataService;

    private final VariableDataRepository variableDataRepository;

    public VariableDataResource(VariableDataService variableDataService, VariableDataRepository variableDataRepository) {
        this.variableDataService = variableDataService;
        this.variableDataRepository = variableDataRepository;
    }

    /**
     * {@code POST  /variable-data} : Create a new variableData.
     *
     * @param variableDataDTO the variableDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new variableDataDTO, or with status {@code 400 (Bad Request)} if the variableData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/variable-data")
    public ResponseEntity<VariableDataDTO> createVariableData(@RequestBody VariableDataDTO variableDataDTO) throws URISyntaxException {
        log.debug("REST request to save VariableData : {}", variableDataDTO);
        if (variableDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new variableData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VariableDataDTO result = variableDataService.save(variableDataDTO);
        return ResponseEntity
            .created(new URI("/api/variable-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /variable-data/:id} : Updates an existing variableData.
     *
     * @param id the id of the variableDataDTO to save.
     * @param variableDataDTO the variableDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated variableDataDTO,
     * or with status {@code 400 (Bad Request)} if the variableDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the variableDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/variable-data/{id}")
    public ResponseEntity<VariableDataDTO> updateVariableData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VariableDataDTO variableDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to update VariableData : {}, {}", id, variableDataDTO);
        if (variableDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, variableDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!variableDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VariableDataDTO result = variableDataService.update(variableDataDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, variableDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /variable-data/:id} : Partial updates given fields of an existing variableData, field will ignore if it is null
     *
     * @param id the id of the variableDataDTO to save.
     * @param variableDataDTO the variableDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated variableDataDTO,
     * or with status {@code 400 (Bad Request)} if the variableDataDTO is not valid,
     * or with status {@code 404 (Not Found)} if the variableDataDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the variableDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/variable-data/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VariableDataDTO> partialUpdateVariableData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VariableDataDTO variableDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update VariableData partially : {}, {}", id, variableDataDTO);
        if (variableDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, variableDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!variableDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VariableDataDTO> result = variableDataService.partialUpdate(variableDataDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, variableDataDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /variable-data} : get all the variableData.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of variableData in body.
     */
    @GetMapping("/variable-data")
    public List<VariableDataDTO> getAllVariableData() {
        log.debug("REST request to get all VariableData");
        return variableDataService.findAll();
    }

    /**
     * {@code GET  /variable-data/:id} : get the "id" variableData.
     *
     * @param id the id of the variableDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the variableDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/variable-data/{id}")
    public ResponseEntity<VariableDataDTO> getVariableData(@PathVariable Long id) {
        log.debug("REST request to get VariableData : {}", id);
        Optional<VariableDataDTO> variableDataDTO = variableDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(variableDataDTO);
    }

    /**
     * {@code DELETE  /variable-data/:id} : delete the "id" variableData.
     *
     * @param id the id of the variableDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/variable-data/{id}")
    public ResponseEntity<Void> deleteVariableData(@PathVariable Long id) {
        log.debug("REST request to delete VariableData : {}", id);
        variableDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
