package com.bhachu.farmica.service;

import com.bhachu.farmica.domain.WarehouseDetail;
import com.bhachu.farmica.repository.WarehouseDetailRepository;
import com.bhachu.farmica.service.dto.WarehouseDetailDTO;
import com.bhachu.farmica.service.mapper.WarehouseDetailMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.bhachu.farmica.domain.WarehouseDetail}.
 */
@Service
@Transactional
public class WarehouseDetailService {

    private final Logger log = LoggerFactory.getLogger(WarehouseDetailService.class);

    private final WarehouseDetailRepository warehouseDetailRepository;

    private final WarehouseDetailMapper warehouseDetailMapper;

    public WarehouseDetailService(WarehouseDetailRepository warehouseDetailRepository, WarehouseDetailMapper warehouseDetailMapper) {
        this.warehouseDetailRepository = warehouseDetailRepository;
        this.warehouseDetailMapper = warehouseDetailMapper;
    }

    /**
     * Save a warehouseDetail.
     *
     * @param warehouseDetailDTO the entity to save.
     * @return the persisted entity.
     */
    public WarehouseDetailDTO save(WarehouseDetailDTO warehouseDetailDTO) {
        log.debug("Request to save WarehouseDetail : {}", warehouseDetailDTO);
        WarehouseDetail warehouseDetail = warehouseDetailMapper.toEntity(warehouseDetailDTO);
        warehouseDetail = warehouseDetailRepository.save(warehouseDetail);
        return warehouseDetailMapper.toDto(warehouseDetail);
    }

    /**
     * Update a warehouseDetail.
     *
     * @param warehouseDetailDTO the entity to save.
     * @return the persisted entity.
     */
    public WarehouseDetailDTO update(WarehouseDetailDTO warehouseDetailDTO) {
        log.debug("Request to update WarehouseDetail : {}", warehouseDetailDTO);
        WarehouseDetail warehouseDetail = warehouseDetailMapper.toEntity(warehouseDetailDTO);
        warehouseDetail = warehouseDetailRepository.save(warehouseDetail);
        return warehouseDetailMapper.toDto(warehouseDetail);
    }

    /**
     * Partially update a warehouseDetail.
     *
     * @param warehouseDetailDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<WarehouseDetailDTO> partialUpdate(WarehouseDetailDTO warehouseDetailDTO) {
        log.debug("Request to partially update WarehouseDetail : {}", warehouseDetailDTO);

        return warehouseDetailRepository
            .findById(warehouseDetailDTO.getId())
            .map(existingWarehouseDetail -> {
                warehouseDetailMapper.partialUpdate(existingWarehouseDetail, warehouseDetailDTO);

                return existingWarehouseDetail;
            })
            .map(warehouseDetailRepository::save)
            .map(warehouseDetailMapper::toDto);
    }

    /**
     * Get all the warehouseDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<WarehouseDetailDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WarehouseDetails");
        return warehouseDetailRepository.findAll(pageable).map(warehouseDetailMapper::toDto);
    }

    /**
     * Get all the warehouseDetails with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<WarehouseDetailDTO> findAllWithEagerRelationships(Pageable pageable) {
        return warehouseDetailRepository.findAllWithEagerRelationships(pageable).map(warehouseDetailMapper::toDto);
    }

    /**
     * Get one warehouseDetail by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WarehouseDetailDTO> findOne(Long id) {
        log.debug("Request to get WarehouseDetail : {}", id);
        return warehouseDetailRepository.findOneWithEagerRelationships(id).map(warehouseDetailMapper::toDto);
    }

    /**
     * Delete the warehouseDetail by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete WarehouseDetail : {}", id);
        warehouseDetailRepository.deleteById(id);
    }
}
