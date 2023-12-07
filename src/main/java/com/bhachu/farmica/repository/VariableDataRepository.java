package com.bhachu.farmica.repository;

import com.bhachu.farmica.domain.VariableData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the VariableData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VariableDataRepository extends JpaRepository<VariableData, Long> {}
