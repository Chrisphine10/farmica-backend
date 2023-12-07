package com.bhachu.farmica.service;

import com.bhachu.farmica.domain.VariableData;
import com.bhachu.farmica.repository.VariableDataRepository;
import com.bhachu.farmica.service.dto.VariableDataDTO;
import com.bhachu.farmica.service.mapper.VariableDataMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.bhachu.farmica.domain.VariableData}.
 */
@Service
@Transactional
public class VariableDataService {

    private final Logger log = LoggerFactory.getLogger(VariableDataService.class);

    private final VariableDataRepository variableDataRepository;

    private final VariableDataMapper variableDataMapper;

    public VariableDataService(VariableDataRepository variableDataRepository, VariableDataMapper variableDataMapper) {
        this.variableDataRepository = variableDataRepository;
        this.variableDataMapper = variableDataMapper;
    }

    /**
     * Save a variableData.
     *
     * @param variableDataDTO the entity to save.
     * @return the persisted entity.
     */
    public VariableDataDTO save(VariableDataDTO variableDataDTO) {
        log.debug("Request to save VariableData : {}", variableDataDTO);
        VariableData variableData = variableDataMapper.toEntity(variableDataDTO);
        variableData = variableDataRepository.save(variableData);
        return variableDataMapper.toDto(variableData);
    }

    /**
     * Update a variableData.
     *
     * @param variableDataDTO the entity to save.
     * @return the persisted entity.
     */
    public VariableDataDTO update(VariableDataDTO variableDataDTO) {
        log.debug("Request to update VariableData : {}", variableDataDTO);
        VariableData variableData = variableDataMapper.toEntity(variableDataDTO);
        variableData = variableDataRepository.save(variableData);
        return variableDataMapper.toDto(variableData);
    }

    /**
     * Partially update a variableData.
     *
     * @param variableDataDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<VariableDataDTO> partialUpdate(VariableDataDTO variableDataDTO) {
        log.debug("Request to partially update VariableData : {}", variableDataDTO);

        return variableDataRepository
            .findById(variableDataDTO.getId())
            .map(existingVariableData -> {
                variableDataMapper.partialUpdate(existingVariableData, variableDataDTO);

                return existingVariableData;
            })
            .map(variableDataRepository::save)
            .map(variableDataMapper::toDto);
    }

    /**
     * Get all the variableData.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<VariableDataDTO> findAll() {
        log.debug("Request to get all VariableData");
        return variableDataRepository.findAll().stream().map(variableDataMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one variableData by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VariableDataDTO> findOne(Long id) {
        log.debug("Request to get VariableData : {}", id);
        return variableDataRepository.findById(id).map(variableDataMapper::toDto);
    }

    /**
     * Delete the variableData by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete VariableData : {}", id);
        variableDataRepository.deleteById(id);
    }
}
