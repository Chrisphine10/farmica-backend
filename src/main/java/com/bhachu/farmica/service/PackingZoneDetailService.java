package com.bhachu.farmica.service;

import com.bhachu.farmica.domain.PackingZoneDetail;
import com.bhachu.farmica.repository.PackingZoneDetailRepository;
import com.bhachu.farmica.service.dto.PackingZoneDetailDTO;
import com.bhachu.farmica.service.mapper.PackingZoneDetailMapper;
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
 * Service Implementation for managing {@link com.bhachu.farmica.domain.PackingZoneDetail}.
 */
@Service
@Transactional
public class PackingZoneDetailService {

    private final Logger log = LoggerFactory.getLogger(PackingZoneDetailService.class);

    private final PackingZoneDetailRepository packingZoneDetailRepository;

    private final PackingZoneDetailMapper packingZoneDetailMapper;

    public PackingZoneDetailService(
        PackingZoneDetailRepository packingZoneDetailRepository,
        PackingZoneDetailMapper packingZoneDetailMapper
    ) {
        this.packingZoneDetailRepository = packingZoneDetailRepository;
        this.packingZoneDetailMapper = packingZoneDetailMapper;
    }

    /**
     * Save a packingZoneDetail.
     *
     * @param packingZoneDetailDTO the entity to save.
     * @return the persisted entity.
     */
    public PackingZoneDetailDTO save(PackingZoneDetailDTO packingZoneDetailDTO) {
        log.debug("Request to save PackingZoneDetail : {}", packingZoneDetailDTO);
        PackingZoneDetail packingZoneDetail = packingZoneDetailMapper.toEntity(packingZoneDetailDTO);
        packingZoneDetail = packingZoneDetailRepository.save(packingZoneDetail);
        return packingZoneDetailMapper.toDto(packingZoneDetail);
    }

    /**
     * Update a packingZoneDetail.
     *
     * @param packingZoneDetailDTO the entity to save.
     * @return the persisted entity.
     */
    public PackingZoneDetailDTO update(PackingZoneDetailDTO packingZoneDetailDTO) {
        log.debug("Request to update PackingZoneDetail : {}", packingZoneDetailDTO);
        PackingZoneDetail packingZoneDetail = packingZoneDetailMapper.toEntity(packingZoneDetailDTO);
        packingZoneDetail = packingZoneDetailRepository.save(packingZoneDetail);
        return packingZoneDetailMapper.toDto(packingZoneDetail);
    }

    /**
     * Partially update a packingZoneDetail.
     *
     * @param packingZoneDetailDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PackingZoneDetailDTO> partialUpdate(PackingZoneDetailDTO packingZoneDetailDTO) {
        log.debug("Request to partially update PackingZoneDetail : {}", packingZoneDetailDTO);

        return packingZoneDetailRepository
            .findById(packingZoneDetailDTO.getId())
            .map(existingPackingZoneDetail -> {
                packingZoneDetailMapper.partialUpdate(existingPackingZoneDetail, packingZoneDetailDTO);

                return existingPackingZoneDetail;
            })
            .map(packingZoneDetailRepository::save)
            .map(packingZoneDetailMapper::toDto);
    }

    /**
     * Get all the packingZoneDetails.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PackingZoneDetailDTO> findAll() {
        log.debug("Request to get all PackingZoneDetails");
        return packingZoneDetailRepository
            .findAll()
            .stream()
            .map(packingZoneDetailMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the packingZoneDetails with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<PackingZoneDetailDTO> findAllWithEagerRelationships(Pageable pageable) {
        return packingZoneDetailRepository.findAllWithEagerRelationships(pageable).map(packingZoneDetailMapper::toDto);
    }

    /**
     * Get one packingZoneDetail by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PackingZoneDetailDTO> findOne(Long id) {
        log.debug("Request to get PackingZoneDetail : {}", id);
        return packingZoneDetailRepository.findOneWithEagerRelationships(id).map(packingZoneDetailMapper::toDto);
    }

    /**
     * Delete the packingZoneDetail by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PackingZoneDetail : {}", id);
        packingZoneDetailRepository.deleteById(id);
    }
}
