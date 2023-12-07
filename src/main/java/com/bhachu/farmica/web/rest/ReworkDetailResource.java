package com.bhachu.farmica.web.rest;

import com.bhachu.farmica.repository.ReworkDetailRepository;
import com.bhachu.farmica.service.ReworkDetailService;
import com.bhachu.farmica.service.dto.ReworkDetailDTO;
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
 * REST controller for managing {@link com.bhachu.farmica.domain.ReworkDetail}.
 */
@RestController
@RequestMapping("/api")
public class ReworkDetailResource {

    private final Logger log = LoggerFactory.getLogger(ReworkDetailResource.class);

    private static final String ENTITY_NAME = "reworkDetail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReworkDetailService reworkDetailService;

    private final ReworkDetailRepository reworkDetailRepository;

    public ReworkDetailResource(ReworkDetailService reworkDetailService, ReworkDetailRepository reworkDetailRepository) {
        this.reworkDetailService = reworkDetailService;
        this.reworkDetailRepository = reworkDetailRepository;
    }

    /**
     * {@code POST  /rework-details} : Create a new reworkDetail.
     *
     * @param reworkDetailDTO the reworkDetailDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reworkDetailDTO, or with status {@code 400 (Bad Request)} if the reworkDetail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rework-details")
    public ResponseEntity<ReworkDetailDTO> createReworkDetail(@Valid @RequestBody ReworkDetailDTO reworkDetailDTO)
        throws URISyntaxException {
        log.debug("REST request to save ReworkDetail : {}", reworkDetailDTO);
        if (reworkDetailDTO.getId() != null) {
            throw new BadRequestAlertException("A new reworkDetail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReworkDetailDTO result = reworkDetailService.save(reworkDetailDTO);
        return ResponseEntity
            .created(new URI("/api/rework-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rework-details/:id} : Updates an existing reworkDetail.
     *
     * @param id the id of the reworkDetailDTO to save.
     * @param reworkDetailDTO the reworkDetailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reworkDetailDTO,
     * or with status {@code 400 (Bad Request)} if the reworkDetailDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reworkDetailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rework-details/{id}")
    public ResponseEntity<ReworkDetailDTO> updateReworkDetail(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ReworkDetailDTO reworkDetailDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ReworkDetail : {}, {}", id, reworkDetailDTO);
        if (reworkDetailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reworkDetailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reworkDetailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ReworkDetailDTO result = reworkDetailService.update(reworkDetailDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reworkDetailDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rework-details/:id} : Partial updates given fields of an existing reworkDetail, field will ignore if it is null
     *
     * @param id the id of the reworkDetailDTO to save.
     * @param reworkDetailDTO the reworkDetailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reworkDetailDTO,
     * or with status {@code 400 (Bad Request)} if the reworkDetailDTO is not valid,
     * or with status {@code 404 (Not Found)} if the reworkDetailDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the reworkDetailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rework-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ReworkDetailDTO> partialUpdateReworkDetail(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ReworkDetailDTO reworkDetailDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ReworkDetail partially : {}, {}", id, reworkDetailDTO);
        if (reworkDetailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reworkDetailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reworkDetailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ReworkDetailDTO> result = reworkDetailService.partialUpdate(reworkDetailDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reworkDetailDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /rework-details} : get all the reworkDetails.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reworkDetails in body.
     */
    @GetMapping("/rework-details")
    public List<ReworkDetailDTO> getAllReworkDetails(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all ReworkDetails");
        return reworkDetailService.findAll();
    }

    /**
     * {@code GET  /rework-details/:id} : get the "id" reworkDetail.
     *
     * @param id the id of the reworkDetailDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reworkDetailDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rework-details/{id}")
    public ResponseEntity<ReworkDetailDTO> getReworkDetail(@PathVariable Long id) {
        log.debug("REST request to get ReworkDetail : {}", id);
        Optional<ReworkDetailDTO> reworkDetailDTO = reworkDetailService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reworkDetailDTO);
    }

    /**
     * {@code DELETE  /rework-details/:id} : delete the "id" reworkDetail.
     *
     * @param id the id of the reworkDetailDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rework-details/{id}")
    public ResponseEntity<Void> deleteReworkDetail(@PathVariable Long id) {
        log.debug("REST request to delete ReworkDetail : {}", id);
        reworkDetailService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
