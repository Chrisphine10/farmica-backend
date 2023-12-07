package com.bhachu.farmica.web.rest;

import com.bhachu.farmica.repository.FarmicaReportRepository;
import com.bhachu.farmica.service.FarmicaReportService;
import com.bhachu.farmica.service.dto.FarmicaReportDTO;
import com.bhachu.farmica.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.bhachu.farmica.domain.FarmicaReport}.
 */
@RestController
@RequestMapping("/api")
public class FarmicaReportResource {

    private final Logger log = LoggerFactory.getLogger(FarmicaReportResource.class);

    private static final String ENTITY_NAME = "farmicaReport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FarmicaReportService farmicaReportService;

    private final FarmicaReportRepository farmicaReportRepository;

    public FarmicaReportResource(FarmicaReportService farmicaReportService, FarmicaReportRepository farmicaReportRepository) {
        this.farmicaReportService = farmicaReportService;
        this.farmicaReportRepository = farmicaReportRepository;
    }

    /**
     * {@code POST  /farmica-reports} : Create a new farmicaReport.
     *
     * @param farmicaReportDTO the farmicaReportDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new farmicaReportDTO, or with status {@code 400 (Bad Request)} if the farmicaReport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/farmica-reports")
    public ResponseEntity<FarmicaReportDTO> createFarmicaReport(@Valid @RequestBody FarmicaReportDTO farmicaReportDTO)
        throws URISyntaxException {
        log.debug("REST request to save FarmicaReport : {}", farmicaReportDTO);
        if (farmicaReportDTO.getId() != null) {
            throw new BadRequestAlertException("A new farmicaReport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FarmicaReportDTO result = farmicaReportService.save(farmicaReportDTO);
        return ResponseEntity
            .created(new URI("/api/farmica-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /farmica-reports/:id} : Updates an existing farmicaReport.
     *
     * @param id the id of the farmicaReportDTO to save.
     * @param farmicaReportDTO the farmicaReportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated farmicaReportDTO,
     * or with status {@code 400 (Bad Request)} if the farmicaReportDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the farmicaReportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/farmica-reports/{id}")
    public ResponseEntity<FarmicaReportDTO> updateFarmicaReport(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FarmicaReportDTO farmicaReportDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FarmicaReport : {}, {}", id, farmicaReportDTO);
        if (farmicaReportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, farmicaReportDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!farmicaReportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FarmicaReportDTO result = farmicaReportService.update(farmicaReportDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, farmicaReportDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /farmica-reports/:id} : Partial updates given fields of an existing farmicaReport, field will ignore if it is null
     *
     * @param id the id of the farmicaReportDTO to save.
     * @param farmicaReportDTO the farmicaReportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated farmicaReportDTO,
     * or with status {@code 400 (Bad Request)} if the farmicaReportDTO is not valid,
     * or with status {@code 404 (Not Found)} if the farmicaReportDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the farmicaReportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/farmica-reports/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FarmicaReportDTO> partialUpdateFarmicaReport(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FarmicaReportDTO farmicaReportDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FarmicaReport partially : {}, {}", id, farmicaReportDTO);
        if (farmicaReportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, farmicaReportDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!farmicaReportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FarmicaReportDTO> result = farmicaReportService.partialUpdate(farmicaReportDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, farmicaReportDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /farmica-reports} : get all the farmicaReports.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of farmicaReports in body.
     */
    @GetMapping("/farmica-reports")
    public List<FarmicaReportDTO> getAllFarmicaReports() {
        log.debug("REST request to get all FarmicaReports");
        return farmicaReportService.findAll();
    }

    /**
     * {@code GET  /farmica-reports/:id} : get the "id" farmicaReport.
     *
     * @param id the id of the farmicaReportDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the farmicaReportDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/farmica-reports/{id}")
    public ResponseEntity<FarmicaReportDTO> getFarmicaReport(@PathVariable Long id) {
        log.debug("REST request to get FarmicaReport : {}", id);
        Optional<FarmicaReportDTO> farmicaReportDTO = farmicaReportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(farmicaReportDTO);
    }

    /**
     * {@code DELETE  /farmica-reports/:id} : delete the "id" farmicaReport.
     *
     * @param id the id of the farmicaReportDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/farmica-reports/{id}")
    public ResponseEntity<Void> deleteFarmicaReport(@PathVariable Long id) {
        log.debug("REST request to delete FarmicaReport : {}", id);
        farmicaReportService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
