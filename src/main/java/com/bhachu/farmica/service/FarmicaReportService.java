package com.bhachu.farmica.service;

import com.bhachu.farmica.domain.FarmicaReport;
import com.bhachu.farmica.repository.FarmicaReportRepository;
import com.bhachu.farmica.service.dto.FarmicaReportDTO;
import com.bhachu.farmica.service.mapper.FarmicaReportMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.bhachu.farmica.domain.FarmicaReport}.
 */
@Service
@Transactional
public class FarmicaReportService {

    private final Logger log = LoggerFactory.getLogger(FarmicaReportService.class);

    private final FarmicaReportRepository farmicaReportRepository;

    private final FarmicaReportMapper farmicaReportMapper;

    public FarmicaReportService(FarmicaReportRepository farmicaReportRepository, FarmicaReportMapper farmicaReportMapper) {
        this.farmicaReportRepository = farmicaReportRepository;
        this.farmicaReportMapper = farmicaReportMapper;
    }

    /**
     * Save a farmicaReport.
     *
     * @param farmicaReportDTO the entity to save.
     * @return the persisted entity.
     */
    public FarmicaReportDTO save(FarmicaReportDTO farmicaReportDTO) {
        log.debug("Request to save FarmicaReport : {}", farmicaReportDTO);
        FarmicaReport farmicaReport = farmicaReportMapper.toEntity(farmicaReportDTO);
        farmicaReport = farmicaReportRepository.save(farmicaReport);
        return farmicaReportMapper.toDto(farmicaReport);
    }

    /**
     * Update a farmicaReport.
     *
     * @param farmicaReportDTO the entity to save.
     * @return the persisted entity.
     */
    public FarmicaReportDTO update(FarmicaReportDTO farmicaReportDTO) {
        log.debug("Request to update FarmicaReport : {}", farmicaReportDTO);
        FarmicaReport farmicaReport = farmicaReportMapper.toEntity(farmicaReportDTO);
        farmicaReport = farmicaReportRepository.save(farmicaReport);
        return farmicaReportMapper.toDto(farmicaReport);
    }

    /**
     * Partially update a farmicaReport.
     *
     * @param farmicaReportDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FarmicaReportDTO> partialUpdate(FarmicaReportDTO farmicaReportDTO) {
        log.debug("Request to partially update FarmicaReport : {}", farmicaReportDTO);

        return farmicaReportRepository
            .findById(farmicaReportDTO.getId())
            .map(existingFarmicaReport -> {
                farmicaReportMapper.partialUpdate(existingFarmicaReport, farmicaReportDTO);

                return existingFarmicaReport;
            })
            .map(farmicaReportRepository::save)
            .map(farmicaReportMapper::toDto);
    }

    /**
     * Get all the farmicaReports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FarmicaReportDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FarmicaReports");
        return farmicaReportRepository.findAll(pageable).map(farmicaReportMapper::toDto);
    }

    /**
     * Get one farmicaReport by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FarmicaReportDTO> findOne(Long id) {
        log.debug("Request to get FarmicaReport : {}", id);
        return farmicaReportRepository.findById(id).map(farmicaReportMapper::toDto);
    }

    /**
     * Delete the farmicaReport by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FarmicaReport : {}", id);
        farmicaReportRepository.deleteById(id);
    }
}
