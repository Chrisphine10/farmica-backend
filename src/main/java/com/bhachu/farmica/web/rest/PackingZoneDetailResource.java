package com.bhachu.farmica.web.rest;

import com.bhachu.farmica.repository.PackingZoneDetailRepository;
import com.bhachu.farmica.service.PackingZoneDetailService;
import com.bhachu.farmica.service.dto.PackingZoneDetailDTO;
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
 * REST controller for managing {@link com.bhachu.farmica.domain.PackingZoneDetail}.
 */
@RestController
@RequestMapping("/api")
public class PackingZoneDetailResource {

    private final Logger log = LoggerFactory.getLogger(PackingZoneDetailResource.class);

    private static final String ENTITY_NAME = "packingZoneDetail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PackingZoneDetailService packingZoneDetailService;

    private final PackingZoneDetailRepository packingZoneDetailRepository;

    public PackingZoneDetailResource(
        PackingZoneDetailService packingZoneDetailService,
        PackingZoneDetailRepository packingZoneDetailRepository
    ) {
        this.packingZoneDetailService = packingZoneDetailService;
        this.packingZoneDetailRepository = packingZoneDetailRepository;
    }

    /**
     * {@code POST  /packing-zone-details} : Create a new packingZoneDetail.
     *
     * @param packingZoneDetailDTO the packingZoneDetailDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new packingZoneDetailDTO, or with status {@code 400 (Bad Request)} if the packingZoneDetail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/packing-zone-details")
    public ResponseEntity<PackingZoneDetailDTO> createPackingZoneDetail(@Valid @RequestBody PackingZoneDetailDTO packingZoneDetailDTO)
        throws URISyntaxException {
        log.debug("REST request to save PackingZoneDetail : {}", packingZoneDetailDTO);
        if (packingZoneDetailDTO.getId() != null) {
            throw new BadRequestAlertException("A new packingZoneDetail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PackingZoneDetailDTO result = packingZoneDetailService.save(packingZoneDetailDTO);
        return ResponseEntity
            .created(new URI("/api/packing-zone-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /packing-zone-details/:id} : Updates an existing packingZoneDetail.
     *
     * @param id the id of the packingZoneDetailDTO to save.
     * @param packingZoneDetailDTO the packingZoneDetailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated packingZoneDetailDTO,
     * or with status {@code 400 (Bad Request)} if the packingZoneDetailDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the packingZoneDetailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/packing-zone-details/{id}")
    public ResponseEntity<PackingZoneDetailDTO> updatePackingZoneDetail(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PackingZoneDetailDTO packingZoneDetailDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PackingZoneDetail : {}, {}", id, packingZoneDetailDTO);
        if (packingZoneDetailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, packingZoneDetailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!packingZoneDetailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PackingZoneDetailDTO result = packingZoneDetailService.update(packingZoneDetailDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, packingZoneDetailDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /packing-zone-details/:id} : Partial updates given fields of an existing packingZoneDetail, field will ignore if it is null
     *
     * @param id the id of the packingZoneDetailDTO to save.
     * @param packingZoneDetailDTO the packingZoneDetailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated packingZoneDetailDTO,
     * or with status {@code 400 (Bad Request)} if the packingZoneDetailDTO is not valid,
     * or with status {@code 404 (Not Found)} if the packingZoneDetailDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the packingZoneDetailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/packing-zone-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PackingZoneDetailDTO> partialUpdatePackingZoneDetail(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PackingZoneDetailDTO packingZoneDetailDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PackingZoneDetail partially : {}, {}", id, packingZoneDetailDTO);
        if (packingZoneDetailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, packingZoneDetailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!packingZoneDetailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PackingZoneDetailDTO> result = packingZoneDetailService.partialUpdate(packingZoneDetailDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, packingZoneDetailDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /packing-zone-details} : get all the packingZoneDetails.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of packingZoneDetails in body.
     */
    @GetMapping("/packing-zone-details")
    public List<PackingZoneDetailDTO> getAllPackingZoneDetails(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all PackingZoneDetails");
        return packingZoneDetailService.findAll();
    }

    /**
     * {@code GET  /packing-zone-details/:id} : get the "id" packingZoneDetail.
     *
     * @param id the id of the packingZoneDetailDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the packingZoneDetailDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/packing-zone-details/{id}")
    public ResponseEntity<PackingZoneDetailDTO> getPackingZoneDetail(@PathVariable Long id) {
        log.debug("REST request to get PackingZoneDetail : {}", id);
        Optional<PackingZoneDetailDTO> packingZoneDetailDTO = packingZoneDetailService.findOne(id);
        return ResponseUtil.wrapOrNotFound(packingZoneDetailDTO);
    }

    /**
     * {@code DELETE  /packing-zone-details/:id} : delete the "id" packingZoneDetail.
     *
     * @param id the id of the packingZoneDetailDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/packing-zone-details/{id}")
    public ResponseEntity<Void> deletePackingZoneDetail(@PathVariable Long id) {
        log.debug("REST request to delete PackingZoneDetail : {}", id);
        packingZoneDetailService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
