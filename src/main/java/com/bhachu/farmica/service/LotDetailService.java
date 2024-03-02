package com.bhachu.farmica.service;

import com.bhachu.farmica.domain.LotDetail;
import com.bhachu.farmica.repository.LotDetailRepository;
import com.bhachu.farmica.service.dto.LotDetailDTO;
import com.bhachu.farmica.service.mapper.LotDetailMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.bhachu.farmica.domain.LotDetail}.
 */
@Service
@Transactional
public class LotDetailService {

    private final Logger log = LoggerFactory.getLogger(LotDetailService.class);

    private final LotDetailRepository lotDetailRepository;

    private final LotDetailMapper lotDetailMapper;

    public LotDetailService(LotDetailRepository lotDetailRepository, LotDetailMapper lotDetailMapper) {
        this.lotDetailRepository = lotDetailRepository;
        this.lotDetailMapper = lotDetailMapper;
    }

    /**
     * Save a lotDetail.
     *
     * @param lotDetailDTO the entity to save.
     * @return the persisted entity.
     */
    public LotDetailDTO save(LotDetailDTO lotDetailDTO) {
        log.debug("Request to save LotDetail : {}", lotDetailDTO);
        LotDetail lotDetail = lotDetailMapper.toEntity(lotDetailDTO);
        lotDetail = lotDetailRepository.save(lotDetail);
        return lotDetailMapper.toDto(lotDetail);
    }

    /**
     * Update a lotDetail.
     *
     * @param lotDetailDTO the entity to save.
     * @return the persisted entity.
     */
    public LotDetailDTO update(LotDetailDTO lotDetailDTO) {
        log.debug("Request to update LotDetail : {}", lotDetailDTO);
        LotDetail lotDetail = lotDetailMapper.toEntity(lotDetailDTO);
        lotDetail = lotDetailRepository.save(lotDetail);
        return lotDetailMapper.toDto(lotDetail);
    }

    /**
     * Partially update a lotDetail.
     *
     * @param lotDetailDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LotDetailDTO> partialUpdate(LotDetailDTO lotDetailDTO) {
        log.debug("Request to partially update LotDetail : {}", lotDetailDTO);

        return lotDetailRepository
            .findById(lotDetailDTO.getId())
            .map(existingLotDetail -> {
                lotDetailMapper.partialUpdate(existingLotDetail, lotDetailDTO);

                return existingLotDetail;
            })
            .map(lotDetailRepository::save)
            .map(lotDetailMapper::toDto);
    }

    /**
     * Get all the lotDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LotDetailDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LotDetails");
        return lotDetailRepository.findAll(pageable).map(lotDetailMapper::toDto);
    }

    /**
     * Get all the lotDetails with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<LotDetailDTO> findAllWithEagerRelationships(Pageable pageable) {
        return lotDetailRepository.findAllWithEagerRelationships(pageable).map(lotDetailMapper::toDto);
    }

    /**
     * Get one lotDetail by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LotDetailDTO> findOne(Long id) {
        log.debug("Request to get LotDetail : {}", id);
        return lotDetailRepository.findOneWithEagerRelationships(id).map(lotDetailMapper::toDto);
    }

    /**
     * Delete the lotDetail by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LotDetail : {}", id);
        lotDetailRepository.deleteById(id);
    }
}
