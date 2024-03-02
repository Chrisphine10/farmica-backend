package com.bhachu.farmica.web.rest;

import com.bhachu.farmica.repository.StyleReportRepository;
import com.bhachu.farmica.service.StyleReportService;
import com.bhachu.farmica.service.dto.StyleReportDTO;
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
 * REST controller for managing {@link com.bhachu.farmica.domain.StyleReport}.
 */
@RestController
@RequestMapping("/api")
public class StyleReportResource {

    private final Logger log = LoggerFactory.getLogger(StyleReportResource.class);

    private static final String ENTITY_NAME = "styleReport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StyleReportService styleReportService;

    private final StyleReportRepository styleReportRepository;

    public StyleReportResource(StyleReportService styleReportService, StyleReportRepository styleReportRepository) {
        this.styleReportService = styleReportService;
        this.styleReportRepository = styleReportRepository;
    }

    /**
     * {@code POST  /style-reports} : Create a new styleReport.
     *
     * @param styleReportDTO the styleReportDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new styleReportDTO, or with status {@code 400 (Bad Request)} if the styleReport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/style-reports")
    public ResponseEntity<StyleReportDTO> createStyleReport(@Valid @RequestBody StyleReportDTO styleReportDTO) throws URISyntaxException {
        log.debug("REST request to save StyleReport : {}", styleReportDTO);
        if (styleReportDTO.getId() != null) {
            throw new BadRequestAlertException("A new styleReport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StyleReportDTO result = styleReportService.save(styleReportDTO);
        return ResponseEntity
            .created(new URI("/api/style-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /style-reports/:id} : Updates an existing styleReport.
     *
     * @param id the id of the styleReportDTO to save.
     * @param styleReportDTO the styleReportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated styleReportDTO,
     * or with status {@code 400 (Bad Request)} if the styleReportDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the styleReportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/style-reports/{id}")
    public ResponseEntity<StyleReportDTO> updateStyleReport(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody StyleReportDTO styleReportDTO
    ) throws URISyntaxException {
        log.debug("REST request to update StyleReport : {}, {}", id, styleReportDTO);
        if (styleReportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, styleReportDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!styleReportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StyleReportDTO result = styleReportService.update(styleReportDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, styleReportDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /style-reports/:id} : Partial updates given fields of an existing styleReport, field will ignore if it is null
     *
     * @param id the id of the styleReportDTO to save.
     * @param styleReportDTO the styleReportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated styleReportDTO,
     * or with status {@code 400 (Bad Request)} if the styleReportDTO is not valid,
     * or with status {@code 404 (Not Found)} if the styleReportDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the styleReportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/style-reports/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StyleReportDTO> partialUpdateStyleReport(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody StyleReportDTO styleReportDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update StyleReport partially : {}, {}", id, styleReportDTO);
        if (styleReportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, styleReportDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!styleReportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StyleReportDTO> result = styleReportService.partialUpdate(styleReportDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, styleReportDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /style-reports} : get all the styleReports.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of styleReports in body.
     */
    @GetMapping("/style-reports")
    public ResponseEntity<List<StyleReportDTO>> getAllStyleReports(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of StyleReports");
        Page<StyleReportDTO> page = styleReportService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /style-reports/:id} : get the "id" styleReport.
     *
     * @param id the id of the styleReportDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the styleReportDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/style-reports/{id}")
    public ResponseEntity<StyleReportDTO> getStyleReport(@PathVariable Long id) {
        log.debug("REST request to get StyleReport : {}", id);
        Optional<StyleReportDTO> styleReportDTO = styleReportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(styleReportDTO);
    }

    /**
     * {@code DELETE  /style-reports/:id} : delete the "id" styleReport.
     *
     * @param id the id of the styleReportDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/style-reports/{id}")
    public ResponseEntity<Void> deleteStyleReport(@PathVariable Long id) {
        log.debug("REST request to delete StyleReport : {}", id);
        styleReportService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
