package com.bhachu.farmica.service;

import com.bhachu.farmica.domain.SalesDetail;
import com.bhachu.farmica.repository.SalesDetailRepository;
import com.bhachu.farmica.service.dto.SalesDetailDTO;
import com.bhachu.farmica.service.mapper.SalesDetailMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.bhachu.farmica.domain.SalesDetail}.
 */
@Service
@Transactional
public class SalesDetailService {

    private final Logger log = LoggerFactory.getLogger(SalesDetailService.class);

    private final SalesDetailRepository salesDetailRepository;

    private final SalesDetailMapper salesDetailMapper;

    public SalesDetailService(SalesDetailRepository salesDetailRepository, SalesDetailMapper salesDetailMapper) {
        this.salesDetailRepository = salesDetailRepository;
        this.salesDetailMapper = salesDetailMapper;
    }

    /**
     * Save a salesDetail.
     *
     * @param salesDetailDTO the entity to save.
     * @return the persisted entity.
     */
    public SalesDetailDTO save(SalesDetailDTO salesDetailDTO) {
        log.debug("Request to save SalesDetail : {}", salesDetailDTO);
        SalesDetail salesDetail = salesDetailMapper.toEntity(salesDetailDTO);
        salesDetail = salesDetailRepository.save(salesDetail);
        return salesDetailMapper.toDto(salesDetail);
    }

    /**
     * Update a salesDetail.
     *
     * @param salesDetailDTO the entity to save.
     * @return the persisted entity.
     */
    public SalesDetailDTO update(SalesDetailDTO salesDetailDTO) {
        log.debug("Request to update SalesDetail : {}", salesDetailDTO);
        SalesDetail salesDetail = salesDetailMapper.toEntity(salesDetailDTO);
        salesDetail = salesDetailRepository.save(salesDetail);
        return salesDetailMapper.toDto(salesDetail);
    }

    /**
     * Partially update a salesDetail.
     *
     * @param salesDetailDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SalesDetailDTO> partialUpdate(SalesDetailDTO salesDetailDTO) {
        log.debug("Request to partially update SalesDetail : {}", salesDetailDTO);

        return salesDetailRepository
            .findById(salesDetailDTO.getId())
            .map(existingSalesDetail -> {
                salesDetailMapper.partialUpdate(existingSalesDetail, salesDetailDTO);

                return existingSalesDetail;
            })
            .map(salesDetailRepository::save)
            .map(salesDetailMapper::toDto);
    }

    /**
     * Get all the salesDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SalesDetailDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SalesDetails");
        return salesDetailRepository.findAll(pageable).map(salesDetailMapper::toDto);
    }

    /**
     * Get all the salesDetails with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<SalesDetailDTO> findAllWithEagerRelationships(Pageable pageable) {
        return salesDetailRepository.findAllWithEagerRelationships(pageable).map(salesDetailMapper::toDto);
    }

    /**
     * Get one salesDetail by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SalesDetailDTO> findOne(Long id) {
        log.debug("Request to get SalesDetail : {}", id);
        return salesDetailRepository.findOneWithEagerRelationships(id).map(salesDetailMapper::toDto);
    }

    /**
     * Delete the salesDetail by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SalesDetail : {}", id);
        salesDetailRepository.deleteById(id);
    }
}
