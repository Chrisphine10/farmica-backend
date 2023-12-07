package com.bhachu.farmica.web.rest;

import com.bhachu.farmica.repository.SalesDetailRepository;
import com.bhachu.farmica.service.SalesDetailService;
import com.bhachu.farmica.service.dto.SalesDetailDTO;
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
 * REST controller for managing {@link com.bhachu.farmica.domain.SalesDetail}.
 */
@RestController
@RequestMapping("/api")
public class SalesDetailResource {

    private final Logger log = LoggerFactory.getLogger(SalesDetailResource.class);

    private static final String ENTITY_NAME = "salesDetail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SalesDetailService salesDetailService;

    private final SalesDetailRepository salesDetailRepository;

    public SalesDetailResource(SalesDetailService salesDetailService, SalesDetailRepository salesDetailRepository) {
        this.salesDetailService = salesDetailService;
        this.salesDetailRepository = salesDetailRepository;
    }

    /**
     * {@code POST  /sales-details} : Create a new salesDetail.
     *
     * @param salesDetailDTO the salesDetailDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new salesDetailDTO, or with status {@code 400 (Bad Request)} if the salesDetail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sales-details")
    public ResponseEntity<SalesDetailDTO> createSalesDetail(@Valid @RequestBody SalesDetailDTO salesDetailDTO) throws URISyntaxException {
        log.debug("REST request to save SalesDetail : {}", salesDetailDTO);
        if (salesDetailDTO.getId() != null) {
            throw new BadRequestAlertException("A new salesDetail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SalesDetailDTO result = salesDetailService.save(salesDetailDTO);
        return ResponseEntity
            .created(new URI("/api/sales-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sales-details/:id} : Updates an existing salesDetail.
     *
     * @param id the id of the salesDetailDTO to save.
     * @param salesDetailDTO the salesDetailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salesDetailDTO,
     * or with status {@code 400 (Bad Request)} if the salesDetailDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the salesDetailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sales-details/{id}")
    public ResponseEntity<SalesDetailDTO> updateSalesDetail(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SalesDetailDTO salesDetailDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SalesDetail : {}, {}", id, salesDetailDTO);
        if (salesDetailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, salesDetailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!salesDetailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SalesDetailDTO result = salesDetailService.update(salesDetailDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, salesDetailDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sales-details/:id} : Partial updates given fields of an existing salesDetail, field will ignore if it is null
     *
     * @param id the id of the salesDetailDTO to save.
     * @param salesDetailDTO the salesDetailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salesDetailDTO,
     * or with status {@code 400 (Bad Request)} if the salesDetailDTO is not valid,
     * or with status {@code 404 (Not Found)} if the salesDetailDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the salesDetailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sales-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SalesDetailDTO> partialUpdateSalesDetail(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SalesDetailDTO salesDetailDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SalesDetail partially : {}, {}", id, salesDetailDTO);
        if (salesDetailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, salesDetailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!salesDetailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SalesDetailDTO> result = salesDetailService.partialUpdate(salesDetailDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, salesDetailDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /sales-details} : get all the salesDetails.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of salesDetails in body.
     */
    @GetMapping("/sales-details")
    public List<SalesDetailDTO> getAllSalesDetails(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all SalesDetails");
        return salesDetailService.findAll();
    }

    /**
     * {@code GET  /sales-details/:id} : get the "id" salesDetail.
     *
     * @param id the id of the salesDetailDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the salesDetailDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sales-details/{id}")
    public ResponseEntity<SalesDetailDTO> getSalesDetail(@PathVariable Long id) {
        log.debug("REST request to get SalesDetail : {}", id);
        Optional<SalesDetailDTO> salesDetailDTO = salesDetailService.findOne(id);
        return ResponseUtil.wrapOrNotFound(salesDetailDTO);
    }

    /**
     * {@code DELETE  /sales-details/:id} : delete the "id" salesDetail.
     *
     * @param id the id of the salesDetailDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sales-details/{id}")
    public ResponseEntity<Void> deleteSalesDetail(@PathVariable Long id) {
        log.debug("REST request to delete SalesDetail : {}", id);
        salesDetailService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
