package com.bhachu.farmica.web.rest;

import com.bhachu.farmica.repository.WarehouseDetailRepository;
import com.bhachu.farmica.service.WarehouseDetailService;
import com.bhachu.farmica.service.dto.WarehouseDetailDTO;
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
 * REST controller for managing {@link com.bhachu.farmica.domain.WarehouseDetail}.
 */
@RestController
@RequestMapping("/api")
public class WarehouseDetailResource {

    private final Logger log = LoggerFactory.getLogger(WarehouseDetailResource.class);

    private static final String ENTITY_NAME = "warehouseDetail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WarehouseDetailService warehouseDetailService;

    private final WarehouseDetailRepository warehouseDetailRepository;

    public WarehouseDetailResource(WarehouseDetailService warehouseDetailService, WarehouseDetailRepository warehouseDetailRepository) {
        this.warehouseDetailService = warehouseDetailService;
        this.warehouseDetailRepository = warehouseDetailRepository;
    }

    /**
     * {@code POST  /warehouse-details} : Create a new warehouseDetail.
     *
     * @param warehouseDetailDTO the warehouseDetailDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new warehouseDetailDTO, or with status {@code 400 (Bad Request)} if the warehouseDetail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/warehouse-details")
    public ResponseEntity<WarehouseDetailDTO> createWarehouseDetail(@Valid @RequestBody WarehouseDetailDTO warehouseDetailDTO)
        throws URISyntaxException {
        log.debug("REST request to save WarehouseDetail : {}", warehouseDetailDTO);
        if (warehouseDetailDTO.getId() != null) {
            throw new BadRequestAlertException("A new warehouseDetail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WarehouseDetailDTO result = warehouseDetailService.save(warehouseDetailDTO);
        return ResponseEntity
            .created(new URI("/api/warehouse-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /warehouse-details/:id} : Updates an existing warehouseDetail.
     *
     * @param id the id of the warehouseDetailDTO to save.
     * @param warehouseDetailDTO the warehouseDetailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated warehouseDetailDTO,
     * or with status {@code 400 (Bad Request)} if the warehouseDetailDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the warehouseDetailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/warehouse-details/{id}")
    public ResponseEntity<WarehouseDetailDTO> updateWarehouseDetail(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody WarehouseDetailDTO warehouseDetailDTO
    ) throws URISyntaxException {
        log.debug("REST request to update WarehouseDetail : {}, {}", id, warehouseDetailDTO);
        if (warehouseDetailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, warehouseDetailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!warehouseDetailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WarehouseDetailDTO result = warehouseDetailService.update(warehouseDetailDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, warehouseDetailDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /warehouse-details/:id} : Partial updates given fields of an existing warehouseDetail, field will ignore if it is null
     *
     * @param id the id of the warehouseDetailDTO to save.
     * @param warehouseDetailDTO the warehouseDetailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated warehouseDetailDTO,
     * or with status {@code 400 (Bad Request)} if the warehouseDetailDTO is not valid,
     * or with status {@code 404 (Not Found)} if the warehouseDetailDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the warehouseDetailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/warehouse-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WarehouseDetailDTO> partialUpdateWarehouseDetail(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody WarehouseDetailDTO warehouseDetailDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update WarehouseDetail partially : {}, {}", id, warehouseDetailDTO);
        if (warehouseDetailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, warehouseDetailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!warehouseDetailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WarehouseDetailDTO> result = warehouseDetailService.partialUpdate(warehouseDetailDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, warehouseDetailDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /warehouse-details} : get all the warehouseDetails.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of warehouseDetails in body.
     */
    @GetMapping("/warehouse-details")
    public ResponseEntity<List<WarehouseDetailDTO>> getAllWarehouseDetails(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of WarehouseDetails");
        Page<WarehouseDetailDTO> page;
        if (eagerload) {
            page = warehouseDetailService.findAllWithEagerRelationships(pageable);
        } else {
            page = warehouseDetailService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /warehouse-details/:id} : get the "id" warehouseDetail.
     *
     * @param id the id of the warehouseDetailDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the warehouseDetailDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/warehouse-details/{id}")
    public ResponseEntity<WarehouseDetailDTO> getWarehouseDetail(@PathVariable Long id) {
        log.debug("REST request to get WarehouseDetail : {}", id);
        Optional<WarehouseDetailDTO> warehouseDetailDTO = warehouseDetailService.findOne(id);
        return ResponseUtil.wrapOrNotFound(warehouseDetailDTO);
    }

    /**
     * {@code DELETE  /warehouse-details/:id} : delete the "id" warehouseDetail.
     *
     * @param id the id of the warehouseDetailDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/warehouse-details/{id}")
    public ResponseEntity<Void> deleteWarehouseDetail(@PathVariable Long id) {
        log.debug("REST request to delete WarehouseDetail : {}", id);
        warehouseDetailService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
