package com.bhachu.farmica.web.rest;

import com.bhachu.farmica.repository.LotDetailRepository;
import com.bhachu.farmica.service.LotDetailService;
import com.bhachu.farmica.service.dto.LotDetailDTO;
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
 * REST controller for managing {@link com.bhachu.farmica.domain.LotDetail}.
 */
@RestController
@RequestMapping("/api")
public class LotDetailResource {

    private final Logger log = LoggerFactory.getLogger(LotDetailResource.class);

    private static final String ENTITY_NAME = "lotDetail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LotDetailService lotDetailService;

    private final LotDetailRepository lotDetailRepository;

    public LotDetailResource(LotDetailService lotDetailService, LotDetailRepository lotDetailRepository) {
        this.lotDetailService = lotDetailService;
        this.lotDetailRepository = lotDetailRepository;
    }

    /**
     * {@code POST  /lot-details} : Create a new lotDetail.
     *
     * @param lotDetailDTO the lotDetailDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lotDetailDTO, or with status {@code 400 (Bad Request)} if the lotDetail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lot-details")
    public ResponseEntity<LotDetailDTO> createLotDetail(@Valid @RequestBody LotDetailDTO lotDetailDTO) throws URISyntaxException {
        log.debug("REST request to save LotDetail : {}", lotDetailDTO);
        if (lotDetailDTO.getId() != null) {
            throw new BadRequestAlertException("A new lotDetail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LotDetailDTO result = lotDetailService.save(lotDetailDTO);
        return ResponseEntity
            .created(new URI("/api/lot-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lot-details/:id} : Updates an existing lotDetail.
     *
     * @param id the id of the lotDetailDTO to save.
     * @param lotDetailDTO the lotDetailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lotDetailDTO,
     * or with status {@code 400 (Bad Request)} if the lotDetailDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lotDetailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lot-details/{id}")
    public ResponseEntity<LotDetailDTO> updateLotDetail(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LotDetailDTO lotDetailDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LotDetail : {}, {}", id, lotDetailDTO);
        if (lotDetailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lotDetailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lotDetailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LotDetailDTO result = lotDetailService.update(lotDetailDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lotDetailDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /lot-details/:id} : Partial updates given fields of an existing lotDetail, field will ignore if it is null
     *
     * @param id the id of the lotDetailDTO to save.
     * @param lotDetailDTO the lotDetailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lotDetailDTO,
     * or with status {@code 400 (Bad Request)} if the lotDetailDTO is not valid,
     * or with status {@code 404 (Not Found)} if the lotDetailDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the lotDetailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/lot-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LotDetailDTO> partialUpdateLotDetail(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LotDetailDTO lotDetailDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LotDetail partially : {}, {}", id, lotDetailDTO);
        if (lotDetailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lotDetailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lotDetailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LotDetailDTO> result = lotDetailService.partialUpdate(lotDetailDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lotDetailDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /lot-details} : get all the lotDetails.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lotDetails in body.
     */
    @GetMapping("/lot-details")
    public List<LotDetailDTO> getAllLotDetails(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all LotDetails");
        return lotDetailService.findAll();
    }

    /**
     * {@code GET  /lot-details/:id} : get the "id" lotDetail.
     *
     * @param id the id of the lotDetailDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lotDetailDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lot-details/{id}")
    public ResponseEntity<LotDetailDTO> getLotDetail(@PathVariable Long id) {
        log.debug("REST request to get LotDetail : {}", id);
        Optional<LotDetailDTO> lotDetailDTO = lotDetailService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lotDetailDTO);
    }

    /**
     * {@code DELETE  /lot-details/:id} : delete the "id" lotDetail.
     *
     * @param id the id of the lotDetailDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lot-details/{id}")
    public ResponseEntity<Void> deleteLotDetail(@PathVariable Long id) {
        log.debug("REST request to delete LotDetail : {}", id);
        lotDetailService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
