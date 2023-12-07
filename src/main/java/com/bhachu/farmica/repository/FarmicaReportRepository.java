package com.bhachu.farmica.repository;

import com.bhachu.farmica.domain.FarmicaReport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FarmicaReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FarmicaReportRepository extends JpaRepository<FarmicaReport, Long> {}
