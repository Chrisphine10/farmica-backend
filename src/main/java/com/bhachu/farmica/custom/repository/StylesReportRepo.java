package com.bhachu.farmica.custom.repository;

import com.bhachu.farmica.domain.Style;
import com.bhachu.farmica.domain.StyleReport;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface StylesReportRepo extends JpaRepository<StyleReport, Long> {
    // find the last report by created at
    default Optional<StyleReport> findFirstByOrderByCreatedAtDesc() {
        return this.queryFirstByOrderByCreatedAtDesc();
    }

    // find the style report by style month and year
    default Optional<StyleReport> findStyleReportByMonthAndYear(String month, String year, Long styleId) {
        return this.queryFirstByStyleMonthAndYearOnCreatedAt(month, year, styleId);
    }

    // find the style report by style created at start date and end date
    default List<StyleReport> findStyleReportByDates(ZonedDateTime startDate, ZonedDateTime endDate, Long styleId) {
        return this.queryStyleReportByCreatedAt(startDate, endDate, styleId);
    }

    // query style report by style created at start date and end date
    @Query(
        "select report from StyleReport report where report.createdAt >= :startDate and report.createdAt <= :endDate and report.style.id = :styleId order by report.createdAt desc"
    )
    List<StyleReport> queryStyleReportByCreatedAt(
        @Param("startDate") ZonedDateTime startDate,
        @Param("endDate") ZonedDateTime endDate,
        @Param("styleId") Long styleId
    );

    // find the last report by created at
    default Optional<StyleReport> findFirstByStyleOrderByCreatedAtDesc(Style style) {
        return this.queryFirstByStyleOrderByCreatedAtDesc(style);
    }

    // query the last report by month and year based on created at
    @Query(
        "select report from StyleReport report where month(report.createdAt) = :month and year(report.createdAt) = :year and report.style.id = :styleId order by report.createdAt desc limit 1"
    )
    Optional<StyleReport> queryFirstByStyleMonthAndYearOnCreatedAt(
        @Param("month") String month,
        @Param("year") String year,
        @Param("styleId") Long styleId
    );

    // query the last report by created at
    @Query("select report from StyleReport report order by report.createdAt desc limit 1")
    Optional<StyleReport> queryFirstByOrderByCreatedAtDesc();

    // query the last report by created at
    @Query("select report from StyleReport report where report.style = :style order by report.createdAt desc limit 1")
    Optional<StyleReport> queryFirstByStyleOrderByCreatedAtDesc(@Param("style") Style style);
}
