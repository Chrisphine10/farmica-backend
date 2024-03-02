package com.bhachu.farmica.service;

import com.bhachu.farmica.domain.BatchDetail;
import com.bhachu.farmica.repository.BatchDetailRepository;
import com.bhachu.farmica.service.dto.BatchDetailDTO;
import com.bhachu.farmica.service.mapper.BatchDetailMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.bhachu.farmica.domain.BatchDetail}.
 */
@Service
@Transactional
public class BatchDetailService {

    private final Logger log = LoggerFactory.getLogger(BatchDetailService.class);

    private final BatchDetailRepository batchDetailRepository;

    private final BatchDetailMapper batchDetailMapper;

    public BatchDetailService(BatchDetailRepository batchDetailRepository, BatchDetailMapper batchDetailMapper) {
        this.batchDetailRepository = batchDetailRepository;
        this.batchDetailMapper = batchDetailMapper;
    }

    /**
     * Save a batchDetail.
     *
     * @param batchDetailDTO the entity to save.
     * @return the persisted entity.
     */
    public BatchDetailDTO save(BatchDetailDTO batchDetailDTO) {
        log.debug("Request to save BatchDetail : {}", batchDetailDTO);
        BatchDetail batchDetail = batchDetailMapper.toEntity(batchDetailDTO);
        batchDetail = batchDetailRepository.save(batchDetail);
        return batchDetailMapper.toDto(batchDetail);
    }

    /**
     * Update a batchDetail.
     *
     * @param batchDetailDTO the entity to save.
     * @return the persisted entity.
     */
    public BatchDetailDTO update(BatchDetailDTO batchDetailDTO) {
        log.debug("Request to update BatchDetail : {}", batchDetailDTO);
        BatchDetail batchDetail = batchDetailMapper.toEntity(batchDetailDTO);
        batchDetail = batchDetailRepository.save(batchDetail);
        return batchDetailMapper.toDto(batchDetail);
    }

    /**
     * Partially update a batchDetail.
     *
     * @param batchDetailDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BatchDetailDTO> partialUpdate(BatchDetailDTO batchDetailDTO) {
        log.debug("Request to partially update BatchDetail : {}", batchDetailDTO);

        return batchDetailRepository
            .findById(batchDetailDTO.getId())
            .map(existingBatchDetail -> {
                batchDetailMapper.partialUpdate(existingBatchDetail, batchDetailDTO);

                return existingBatchDetail;
            })
            .map(batchDetailRepository::save)
            .map(batchDetailMapper::toDto);
    }

    /**
     * Get all the batchDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BatchDetailDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BatchDetails");
        return batchDetailRepository.findAll(pageable).map(batchDetailMapper::toDto);
    }

    /**
     * Get all the batchDetails with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<BatchDetailDTO> findAllWithEagerRelationships(Pageable pageable) {
        return batchDetailRepository.findAllWithEagerRelationships(pageable).map(batchDetailMapper::toDto);
    }

    /**
     * Get one batchDetail by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BatchDetailDTO> findOne(Long id) {
        log.debug("Request to get BatchDetail : {}", id);
        return batchDetailRepository.findOneWithEagerRelationships(id).map(batchDetailMapper::toDto);
    }

    /**
     * Delete the batchDetail by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BatchDetail : {}", id);
        batchDetailRepository.deleteById(id);
    }
}
