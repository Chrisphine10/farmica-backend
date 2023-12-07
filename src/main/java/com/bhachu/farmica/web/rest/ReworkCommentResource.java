package com.bhachu.farmica.web.rest;

import com.bhachu.farmica.repository.ReworkCommentRepository;
import com.bhachu.farmica.service.ReworkCommentService;
import com.bhachu.farmica.service.dto.ReworkCommentDTO;
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
 * REST controller for managing {@link com.bhachu.farmica.domain.ReworkComment}.
 */
@RestController
@RequestMapping("/api")
public class ReworkCommentResource {

    private final Logger log = LoggerFactory.getLogger(ReworkCommentResource.class);

    private static final String ENTITY_NAME = "reworkComment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReworkCommentService reworkCommentService;

    private final ReworkCommentRepository reworkCommentRepository;

    public ReworkCommentResource(ReworkCommentService reworkCommentService, ReworkCommentRepository reworkCommentRepository) {
        this.reworkCommentService = reworkCommentService;
        this.reworkCommentRepository = reworkCommentRepository;
    }

    /**
     * {@code POST  /rework-comments} : Create a new reworkComment.
     *
     * @param reworkCommentDTO the reworkCommentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reworkCommentDTO, or with status {@code 400 (Bad Request)} if the reworkComment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rework-comments")
    public ResponseEntity<ReworkCommentDTO> createReworkComment(@Valid @RequestBody ReworkCommentDTO reworkCommentDTO)
        throws URISyntaxException {
        log.debug("REST request to save ReworkComment : {}", reworkCommentDTO);
        if (reworkCommentDTO.getId() != null) {
            throw new BadRequestAlertException("A new reworkComment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReworkCommentDTO result = reworkCommentService.save(reworkCommentDTO);
        return ResponseEntity
            .created(new URI("/api/rework-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rework-comments/:id} : Updates an existing reworkComment.
     *
     * @param id the id of the reworkCommentDTO to save.
     * @param reworkCommentDTO the reworkCommentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reworkCommentDTO,
     * or with status {@code 400 (Bad Request)} if the reworkCommentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reworkCommentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rework-comments/{id}")
    public ResponseEntity<ReworkCommentDTO> updateReworkComment(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ReworkCommentDTO reworkCommentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ReworkComment : {}, {}", id, reworkCommentDTO);
        if (reworkCommentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reworkCommentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reworkCommentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ReworkCommentDTO result = reworkCommentService.update(reworkCommentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reworkCommentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rework-comments/:id} : Partial updates given fields of an existing reworkComment, field will ignore if it is null
     *
     * @param id the id of the reworkCommentDTO to save.
     * @param reworkCommentDTO the reworkCommentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reworkCommentDTO,
     * or with status {@code 400 (Bad Request)} if the reworkCommentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the reworkCommentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the reworkCommentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rework-comments/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ReworkCommentDTO> partialUpdateReworkComment(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ReworkCommentDTO reworkCommentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ReworkComment partially : {}, {}", id, reworkCommentDTO);
        if (reworkCommentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reworkCommentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reworkCommentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ReworkCommentDTO> result = reworkCommentService.partialUpdate(reworkCommentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reworkCommentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /rework-comments} : get all the reworkComments.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reworkComments in body.
     */
    @GetMapping("/rework-comments")
    public ResponseEntity<List<ReworkCommentDTO>> getAllReworkComments(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of ReworkComments");
        Page<ReworkCommentDTO> page;
        if (eagerload) {
            page = reworkCommentService.findAllWithEagerRelationships(pageable);
        } else {
            page = reworkCommentService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rework-comments/:id} : get the "id" reworkComment.
     *
     * @param id the id of the reworkCommentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reworkCommentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rework-comments/{id}")
    public ResponseEntity<ReworkCommentDTO> getReworkComment(@PathVariable Long id) {
        log.debug("REST request to get ReworkComment : {}", id);
        Optional<ReworkCommentDTO> reworkCommentDTO = reworkCommentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reworkCommentDTO);
    }

    /**
     * {@code DELETE  /rework-comments/:id} : delete the "id" reworkComment.
     *
     * @param id the id of the reworkCommentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rework-comments/{id}")
    public ResponseEntity<Void> deleteReworkComment(@PathVariable Long id) {
        log.debug("REST request to delete ReworkComment : {}", id);
        reworkCommentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
