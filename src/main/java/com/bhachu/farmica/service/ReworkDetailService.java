package com.bhachu.farmica.service;

import com.bhachu.farmica.domain.ReworkDetail;
import com.bhachu.farmica.repository.ReworkDetailRepository;
import com.bhachu.farmica.service.dto.ReworkDetailDTO;
import com.bhachu.farmica.service.mapper.ReworkDetailMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.bhachu.farmica.domain.ReworkDetail}.
 */
@Service
@Transactional
public class ReworkDetailService {

    private final Logger log = LoggerFactory.getLogger(ReworkDetailService.class);

    private final ReworkDetailRepository reworkDetailRepository;

    private final ReworkDetailMapper reworkDetailMapper;

    public ReworkDetailService(ReworkDetailRepository reworkDetailRepository, ReworkDetailMapper reworkDetailMapper) {
        this.reworkDetailRepository = reworkDetailRepository;
        this.reworkDetailMapper = reworkDetailMapper;
    }

    /**
     * Save a reworkDetail.
     *
     * @param reworkDetailDTO the entity to save.
     * @return the persisted entity.
     */
    public ReworkDetailDTO save(ReworkDetailDTO reworkDetailDTO) {
        log.debug("Request to save ReworkDetail : {}", reworkDetailDTO);
        ReworkDetail reworkDetail = reworkDetailMapper.toEntity(reworkDetailDTO);
        reworkDetail = reworkDetailRepository.save(reworkDetail);
        return reworkDetailMapper.toDto(reworkDetail);
    }

    /**
     * Update a reworkDetail.
     *
     * @param reworkDetailDTO the entity to save.
     * @return the persisted entity.
     */
    public ReworkDetailDTO update(ReworkDetailDTO reworkDetailDTO) {
        log.debug("Request to update ReworkDetail : {}", reworkDetailDTO);
        ReworkDetail reworkDetail = reworkDetailMapper.toEntity(reworkDetailDTO);
        reworkDetail = reworkDetailRepository.save(reworkDetail);
        return reworkDetailMapper.toDto(reworkDetail);
    }

    /**
     * Partially update a reworkDetail.
     *
     * @param reworkDetailDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ReworkDetailDTO> partialUpdate(ReworkDetailDTO reworkDetailDTO) {
        log.debug("Request to partially update ReworkDetail : {}", reworkDetailDTO);

        return reworkDetailRepository
            .findById(reworkDetailDTO.getId())
            .map(existingReworkDetail -> {
                reworkDetailMapper.partialUpdate(existingReworkDetail, reworkDetailDTO);

                return existingReworkDetail;
            })
            .map(reworkDetailRepository::save)
            .map(reworkDetailMapper::toDto);
    }

    /**
     * Get all the reworkDetails.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ReworkDetailDTO> findAll() {
        log.debug("Request to get all ReworkDetails");
        return reworkDetailRepository.findAll().stream().map(reworkDetailMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the reworkDetails with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ReworkDetailDTO> findAllWithEagerRelationships(Pageable pageable) {
        return reworkDetailRepository.findAllWithEagerRelationships(pageable).map(reworkDetailMapper::toDto);
    }

    /**
     * Get one reworkDetail by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ReworkDetailDTO> findOne(Long id) {
        log.debug("Request to get ReworkDetail : {}", id);
        return reworkDetailRepository.findOneWithEagerRelationships(id).map(reworkDetailMapper::toDto);
    }

    /**
     * Delete the reworkDetail by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ReworkDetail : {}", id);
        reworkDetailRepository.deleteById(id);
    }
}
