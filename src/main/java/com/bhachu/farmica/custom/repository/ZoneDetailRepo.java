package com.bhachu.farmica.custom.repository;

import com.bhachu.farmica.domain.PackingZoneDetail;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface ZoneDetailRepo extends JpaRepository<PackingZoneDetail, Long> {
    default List<PackingZoneDetail> findAllByLotDetail(Integer lotNo) {
        return this.queryAllByLotDetail(lotNo);
    }

    default List<PackingZoneDetail> findAllByLotId(Integer lotId) {
        return this.queryAllByLotId(lotId);
    }

    default List<PackingZoneDetail> findAllByStyle(Integer styleId) {
        return this.queryAllByStyle(styleId);
    }

    // find all zone details with a start date and end date and style]
    default List<PackingZoneDetail> findAllByStartDateAndEndDateAndStyle(ZonedDateTime startDate, ZonedDateTime endDate, Long styleId) {
        return this.queryAllByStartDateAndEndDateAndStyle(startDate, endDate, styleId);
    }

    // find total zone count from zone details with a start date and end date and

    // find all zone details with a start date and end date
    default List<PackingZoneDetail> findAllByStartDateAndEndDate(ZonedDateTime startDate, ZonedDateTime endDate) {
        return this.queryAllByStartDateAndEndDate(startDate, endDate);
    }

    // find total zone count from zone details with a start date and end date
    default Integer findTotalZoneCountByStartDateAndEndDate(ZonedDateTime startDate, ZonedDateTime endDate) {
        List<PackingZoneDetail> totalZoneDetails = findAllByStartDateAndEndDate(startDate, endDate);
        Integer totalZoneCount = 0;
        for (PackingZoneDetail zoneDetail : totalZoneDetails) {
            totalZoneCount += zoneDetail.getReceivedCTNs();
        }
        return totalZoneCount;
    }

    // find one by uicode
    default Optional<PackingZoneDetail> findOneByUicode(String uicode) {
        return this.queryAllByUicode(uicode);
    }

    // find all where number of ctns is greater than 0
    default List<PackingZoneDetail> findAllByNumberOfCTNsGreaterThanZero() {
        return this.queryAllByNumberOfCTNsGreaterThanZero();
    }

    // find all where number of ctns is greater than 0
    @Query("select packingZoneDetail from PackingZoneDetail packingZoneDetail where packingZoneDetail.numberOfCTNs > 0")
    List<PackingZoneDetail> queryAllByNumberOfCTNsGreaterThanZero();

    // query one zone details by uicode
    @Query(
        "select packingZoneDetail from PackingZoneDetail packingZoneDetail where packingZoneDetail.uicode = :uicode order by packingZoneDetail.createdAt desc limit 1"
    )
    Optional<PackingZoneDetail> queryAllByUicode(@Param("uicode") String uicode);

    @Query("select packingZoneDetail from PackingZoneDetail packingZoneDetail where packingZoneDetail.lotDetail.lotNo = :lotNo")
    List<PackingZoneDetail> queryAllByLotDetail(@Param("lotNo") Integer lotNo);

    @Query("select packingZoneDetail from PackingZoneDetail packingZoneDetail where packingZoneDetail.style.id = :styleId")
    List<PackingZoneDetail> queryAllByStyle(@Param("styleId") Integer styleId);

    @Query("select packingZoneDetail from PackingZoneDetail packingZoneDetail where packingZoneDetail.lotDetail.id = :lotId")
    List<PackingZoneDetail> queryAllByLotId(@Param("lotId") Integer lotId);

    @Query(
        "select packingZoneDetail from PackingZoneDetail packingZoneDetail where packingZoneDetail.createdAt >= :startDate and packingZoneDetail.createdAt <= :endDate"
    )
    List<PackingZoneDetail> queryAllByStartDateAndEndDate(
        @Param("startDate") ZonedDateTime startDate,
        @Param("endDate") ZonedDateTime endDate
    );

    @Query(
        "select packingZoneDetail from PackingZoneDetail packingZoneDetail where packingZoneDetail.createdAt >= :startDate and packingZoneDetail.createdAt <= :endDate and packingZoneDetail.style.id = :styleId"
    )
    List<PackingZoneDetail> queryAllByStartDateAndEndDateAndStyle(
        @Param("startDate") ZonedDateTime startDate,
        @Param("endDate") ZonedDateTime endDate,
        @Param("styleId") Long styleId
    );
}
