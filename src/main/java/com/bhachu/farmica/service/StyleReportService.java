package com.bhachu.farmica.service;

import com.bhachu.farmica.domain.StyleReport;
import com.bhachu.farmica.repository.StyleReportRepository;
import com.bhachu.farmica.service.dto.StyleReportDTO;
import com.bhachu.farmica.service.mapper.StyleReportMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.bhachu.farmica.domain.StyleReport}.
 */
@Service
@Transactional
public class StyleReportService {

    private final Logger log = LoggerFactory.getLogger(StyleReportService.class);

    private final StyleReportRepository styleReportRepository;

    private final StyleReportMapper styleReportMapper;

    public StyleReportService(StyleReportRepository styleReportRepository, StyleReportMapper styleReportMapper) {
        this.styleReportRepository = styleReportRepository;
        this.styleReportMapper = styleReportMapper;
    }

    /**
     * Save a styleReport.
     *
     * @param styleReportDTO the entity to save.
     * @return the persisted entity.
     */
    public StyleReportDTO save(StyleReportDTO styleReportDTO) {
        log.debug("Request to save StyleReport : {}", styleReportDTO);
        StyleReport styleReport = styleReportMapper.toEntity(styleReportDTO);
        styleReport = styleReportRepository.save(styleReport);
        return styleReportMapper.toDto(styleReport);
    }

    /**
     * Update a styleReport.
     *
     * @param styleReportDTO the entity to save.
     * @return the persisted entity.
     */
    public StyleReportDTO update(StyleReportDTO styleReportDTO) {
        log.debug("Request to update StyleReport : {}", styleReportDTO);
        StyleReport styleReport = styleReportMapper.toEntity(styleReportDTO);
        styleReport = styleReportRepository.save(styleReport);
        return styleReportMapper.toDto(styleReport);
    }

    /**
     * Partially update a styleReport.
     *
     * @param styleReportDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<StyleReportDTO> partialUpdate(StyleReportDTO styleReportDTO) {
        log.debug("Request to partially update StyleReport : {}", styleReportDTO);

        return styleReportRepository
            .findById(styleReportDTO.getId())
            .map(existingStyleReport -> {
                styleReportMapper.partialUpdate(existingStyleReport, styleReportDTO);

                return existingStyleReport;
            })
            .map(styleReportRepository::save)
            .map(styleReportMapper::toDto);
    }

    /**
     * Get all the styleReports.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<StyleReportDTO> findAll() {
        log.debug("Request to get all StyleReports");
        return styleReportRepository.findAll().stream().map(styleReportMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one styleReport by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StyleReportDTO> findOne(Long id) {
        log.debug("Request to get StyleReport : {}", id);
        return styleReportRepository.findById(id).map(styleReportMapper::toDto);
    }

    /**
     * Delete the styleReport by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete StyleReport : {}", id);
        styleReportRepository.deleteById(id);
    }
}
