package com.bhachu.farmica.service;

import com.bhachu.farmica.domain.Style;
import com.bhachu.farmica.repository.StyleRepository;
import com.bhachu.farmica.service.dto.StyleDTO;
import com.bhachu.farmica.service.mapper.StyleMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.bhachu.farmica.domain.Style}.
 */
@Service
@Transactional
public class StyleService {

    private final Logger log = LoggerFactory.getLogger(StyleService.class);

    private final StyleRepository styleRepository;

    private final StyleMapper styleMapper;

    public StyleService(StyleRepository styleRepository, StyleMapper styleMapper) {
        this.styleRepository = styleRepository;
        this.styleMapper = styleMapper;
    }

    /**
     * Save a style.
     *
     * @param styleDTO the entity to save.
     * @return the persisted entity.
     */
    public StyleDTO save(StyleDTO styleDTO) {
        log.debug("Request to save Style : {}", styleDTO);
        Style style = styleMapper.toEntity(styleDTO);
        style = styleRepository.save(style);
        return styleMapper.toDto(style);
    }

    /**
     * Update a style.
     *
     * @param styleDTO the entity to save.
     * @return the persisted entity.
     */
    public StyleDTO update(StyleDTO styleDTO) {
        log.debug("Request to update Style : {}", styleDTO);
        Style style = styleMapper.toEntity(styleDTO);
        style = styleRepository.save(style);
        return styleMapper.toDto(style);
    }

    /**
     * Partially update a style.
     *
     * @param styleDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<StyleDTO> partialUpdate(StyleDTO styleDTO) {
        log.debug("Request to partially update Style : {}", styleDTO);

        return styleRepository
            .findById(styleDTO.getId())
            .map(existingStyle -> {
                styleMapper.partialUpdate(existingStyle, styleDTO);

                return existingStyle;
            })
            .map(styleRepository::save)
            .map(styleMapper::toDto);
    }

    /**
     * Get all the styles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<StyleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Styles");
        return styleRepository.findAll(pageable).map(styleMapper::toDto);
    }

    /**
     * Get all the styles with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<StyleDTO> findAllWithEagerRelationships(Pageable pageable) {
        return styleRepository.findAllWithEagerRelationships(pageable).map(styleMapper::toDto);
    }

    /**
     * Get one style by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StyleDTO> findOne(Long id) {
        log.debug("Request to get Style : {}", id);
        return styleRepository.findOneWithEagerRelationships(id).map(styleMapper::toDto);
    }

    /**
     * Delete the style by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Style : {}", id);
        styleRepository.deleteById(id);
    }
}
