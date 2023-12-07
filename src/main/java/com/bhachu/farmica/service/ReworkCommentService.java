package com.bhachu.farmica.service;

import com.bhachu.farmica.domain.ReworkComment;
import com.bhachu.farmica.repository.ReworkCommentRepository;
import com.bhachu.farmica.service.dto.ReworkCommentDTO;
import com.bhachu.farmica.service.mapper.ReworkCommentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.bhachu.farmica.domain.ReworkComment}.
 */
@Service
@Transactional
public class ReworkCommentService {

    private final Logger log = LoggerFactory.getLogger(ReworkCommentService.class);

    private final ReworkCommentRepository reworkCommentRepository;

    private final ReworkCommentMapper reworkCommentMapper;

    public ReworkCommentService(ReworkCommentRepository reworkCommentRepository, ReworkCommentMapper reworkCommentMapper) {
        this.reworkCommentRepository = reworkCommentRepository;
        this.reworkCommentMapper = reworkCommentMapper;
    }

    /**
     * Save a reworkComment.
     *
     * @param reworkCommentDTO the entity to save.
     * @return the persisted entity.
     */
    public ReworkCommentDTO save(ReworkCommentDTO reworkCommentDTO) {
        log.debug("Request to save ReworkComment : {}", reworkCommentDTO);
        ReworkComment reworkComment = reworkCommentMapper.toEntity(reworkCommentDTO);
        reworkComment = reworkCommentRepository.save(reworkComment);
        return reworkCommentMapper.toDto(reworkComment);
    }

    /**
     * Update a reworkComment.
     *
     * @param reworkCommentDTO the entity to save.
     * @return the persisted entity.
     */
    public ReworkCommentDTO update(ReworkCommentDTO reworkCommentDTO) {
        log.debug("Request to update ReworkComment : {}", reworkCommentDTO);
        ReworkComment reworkComment = reworkCommentMapper.toEntity(reworkCommentDTO);
        reworkComment = reworkCommentRepository.save(reworkComment);
        return reworkCommentMapper.toDto(reworkComment);
    }

    /**
     * Partially update a reworkComment.
     *
     * @param reworkCommentDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ReworkCommentDTO> partialUpdate(ReworkCommentDTO reworkCommentDTO) {
        log.debug("Request to partially update ReworkComment : {}", reworkCommentDTO);

        return reworkCommentRepository
            .findById(reworkCommentDTO.getId())
            .map(existingReworkComment -> {
                reworkCommentMapper.partialUpdate(existingReworkComment, reworkCommentDTO);

                return existingReworkComment;
            })
            .map(reworkCommentRepository::save)
            .map(reworkCommentMapper::toDto);
    }

    /**
     * Get all the reworkComments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ReworkCommentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ReworkComments");
        return reworkCommentRepository.findAll(pageable).map(reworkCommentMapper::toDto);
    }

    /**
     * Get all the reworkComments with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ReworkCommentDTO> findAllWithEagerRelationships(Pageable pageable) {
        return reworkCommentRepository.findAllWithEagerRelationships(pageable).map(reworkCommentMapper::toDto);
    }

    /**
     * Get one reworkComment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ReworkCommentDTO> findOne(Long id) {
        log.debug("Request to get ReworkComment : {}", id);
        return reworkCommentRepository.findOneWithEagerRelationships(id).map(reworkCommentMapper::toDto);
    }

    /**
     * Delete the reworkComment by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ReworkComment : {}", id);
        reworkCommentRepository.deleteById(id);
    }
}
