package com.bhachu.farmica.custom.repository;

import com.bhachu.farmica.domain.FarmicaReport;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface ReportRepo extends JpaRepository<FarmicaReport, Long> {
    // find the last report by created at
    default Optional<FarmicaReport> findFirstByOrderByCreatedAtDesc() {
        return this.queryFirstByOrderByCreatedAtDesc();
    }

    // find the last report by month and year
    default Optional<FarmicaReport> findReportByMonthAndYear(String month, String year) {
        return this.queryFirstByMonthAndYearOnCreatedAt(month, year);
    }

    // query the last report by month and year based on created at
    @Query(
        "select report from FarmicaReport report where month(report.createdAt) = :month and year(report.createdAt) = :year order by report.createdAt desc limit 1"
    )
    Optional<FarmicaReport> queryFirstByMonthAndYearOnCreatedAt(@Param("month") String month, @Param("year") String year);

    // query the last report by created at
    @Query("select report from FarmicaReport report order by report.createdAt desc limit 1")
    Optional<FarmicaReport> queryFirstByOrderByCreatedAtDesc();
}
