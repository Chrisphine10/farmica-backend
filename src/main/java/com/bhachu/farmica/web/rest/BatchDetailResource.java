package com.bhachu.farmica.web.rest;

import com.bhachu.farmica.repository.BatchDetailRepository;
import com.bhachu.farmica.service.BatchDetailService;
import com.bhachu.farmica.service.dto.BatchDetailDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.bhachu.farmica.domain.BatchDetail}.
 */
@RestController
@RequestMapping("/api")
public class BatchDetailResource {

    private final Logger log = LoggerFactory.getLogger(BatchDetailResource.class);

    private static final String ENTITY_NAME = "batchDetail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BatchDetailService batchDetailService;

    private final BatchDetailRepository batchDetailRepository;

    public BatchDetailResource(BatchDetailService batchDetailService, BatchDetailRepository batchDetailRepository) {
        this.batchDetailService = batchDetailService;
        this.batchDetailRepository = batchDetailRepository;
    }

    /**
     * {@code POST  /batch-details} : Create a new batchDetail.
     *
     * @param batchDetailDTO the batchDetailDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new batchDetailDTO, or with status {@code 400 (Bad Request)} if the batchDetail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/batch-details")
    public ResponseEntity<BatchDetailDTO> createBatchDetail(@Valid @RequestBody BatchDetailDTO batchDetailDTO) throws URISyntaxException {
        log.debug("REST request to save BatchDetail : {}", batchDetailDTO);
        if (batchDetailDTO.getId() != null) {
            throw new BadRequestAlertException("A new batchDetail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BatchDetailDTO result = batchDetailService.save(batchDetailDTO);
        return ResponseEntity
            .created(new URI("/api/batch-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /batch-details/:id} : Updates an existing batchDetail.
     *
     * @param id the id of the batchDetailDTO to save.
     * @param batchDetailDTO the batchDetailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated batchDetailDTO,
     * or with status {@code 400 (Bad Request)} if the batchDetailDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the batchDetailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/batch-details/{id}")
    public ResponseEntity<BatchDetailDTO> updateBatchDetail(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BatchDetailDTO batchDetailDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BatchDetail : {}, {}", id, batchDetailDTO);
        if (batchDetailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, batchDetailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!batchDetailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BatchDetailDTO result = batchDetailService.update(batchDetailDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, batchDetailDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /batch-details/:id} : Partial updates given fields of an existing batchDetail, field will ignore if it is null
     *
     * @param id the id of the batchDetailDTO to save.
     * @param batchDetailDTO the batchDetailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated batchDetailDTO,
     * or with status {@code 400 (Bad Request)} if the batchDetailDTO is not valid,
     * or with status {@code 404 (Not Found)} if the batchDetailDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the batchDetailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/batch-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BatchDetailDTO> partialUpdateBatchDetail(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BatchDetailDTO batchDetailDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BatchDetail partially : {}, {}", id, batchDetailDTO);
        if (batchDetailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, batchDetailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!batchDetailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BatchDetailDTO> result = batchDetailService.partialUpdate(batchDetailDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, batchDetailDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /batch-details} : get all the batchDetails.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of batchDetails in body.
     */
    @GetMapping("/batch-details")
    public ResponseEntity<List<BatchDetailDTO>> getAllBatchDetails(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of BatchDetails");
        Page<BatchDetailDTO> page;
        if (eagerload) {
            page = batchDetailService.findAllWithEagerRelationships(pageable);
        } else {
            page = batchDetailService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /batch-details/:id} : get the "id" batchDetail.
     *
     * @param id the id of the batchDetailDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the batchDetailDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/batch-details/{id}")
    public ResponseEntity<BatchDetailDTO> getBatchDetail(@PathVariable Long id) {
        log.debug("REST request to get BatchDetail : {}", id);
        Optional<BatchDetailDTO> batchDetailDTO = batchDetailService.findOne(id);
        return ResponseUtil.wrapOrNotFound(batchDetailDTO);
    }

    /**
     * {@code DELETE  /batch-details/:id} : delete the "id" batchDetail.
     *
     * @param id the id of the batchDetailDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/batch-details/{id}")
    public ResponseEntity<Void> deleteBatchDetail(@PathVariable Long id) {
        log.debug("REST request to delete BatchDetail : {}", id);
        batchDetailService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
