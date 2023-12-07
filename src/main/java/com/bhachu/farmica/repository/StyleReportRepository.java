package com.bhachu.farmica.repository;

import com.bhachu.farmica.domain.StyleReport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the StyleReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StyleReportRepository extends JpaRepository<StyleReport, Long> {}
