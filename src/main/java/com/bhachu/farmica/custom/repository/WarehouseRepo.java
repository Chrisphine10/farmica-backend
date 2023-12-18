package com.bhachu.farmica.custom.repository;

import com.bhachu.farmica.domain.WarehouseDetail;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface WarehouseRepo extends JpaRepository<WarehouseDetail, Long> {
    default List<WarehouseDetail> findAllByLotDetail(Integer lotNo) {
        return this.queryAllByLotDetail(lotNo);
    }

    default List<WarehouseDetail> findAllByLotId(Integer lotId) {
        return this.queryAllByLotId(lotId);
    }

    default List<WarehouseDetail> findAllByStyle(Integer styleId) {
        return this.queryAllByStyle(styleId);
    }

    // find all warehouse details with a start date and end date
    default List<WarehouseDetail> findAllByStartDateAndEndDate(ZonedDateTime startDate, ZonedDateTime endDate) {
        return this.queryAllByStartDateAndEndDate(startDate, endDate);
    }

    // find one by uicode
    default Optional<WarehouseDetail> findOneByUicode(String uicode) {
        return this.queryAllByUicode(uicode);
    }

    // find total warehouse count from warehouse details with zone detail id
    default Integer findTotalWarehouseCountByZoneDetailId(Long zoneDetailId) {
        List<WarehouseDetail> totalWarehouseDetails = findAllByZoneDetailId(zoneDetailId);
        Integer totalWarehouseCount = 0;
        for (WarehouseDetail warehouseDetail : totalWarehouseDetails) {
            totalWarehouseCount += warehouseDetail.getNumberOfCTNs();
        }
        return totalWarehouseCount;
    }

    // find total warehouse count from warehouse details with a start date and end
    // date
    default Integer findTotalWarehouseCountByStartDateAndEndDate(ZonedDateTime startDate, ZonedDateTime endDate) {
        List<WarehouseDetail> totalWarehouseDetails = findAllByStartDateAndEndDate(startDate, endDate);
        Integer totalWarehouseCount = 0;
        for (WarehouseDetail warehouseDetail : totalWarehouseDetails) {
            totalWarehouseCount += warehouseDetail.getReceivedCTNs();
        }
        return totalWarehouseCount;
    }

    // query all warehouse details with zone detail id
    @Query("select warehouseDetail from WarehouseDetail warehouseDetail where warehouseDetail.packingZoneDetail.id = :zoneDetailId")
    List<WarehouseDetail> findAllByZoneDetailId(@Param("zoneDetailId") Long zoneDetailId);

    // query one warehouse details by uicode
    @Query(
        "select warehouseDetail from WarehouseDetail warehouseDetail where warehouseDetail.uicode = :uicode order by warehouseDetail.createdAt desc limit 1"
    )
    Optional<WarehouseDetail> queryAllByUicode(@Param("uicode") String uicode);

    @Query("select warehouseDetail from WarehouseDetail warehouseDetail where warehouseDetail.lotDetail.lotNo = :lotNo")
    List<WarehouseDetail> queryAllByLotDetail(@Param("lotNo") Integer lotNo);

    @Query("select warehouseDetail from WarehouseDetail warehouseDetail where warehouseDetail.style.id = :styleId")
    List<WarehouseDetail> queryAllByStyle(@Param("styleId") Integer styleId);

    @Query("select warehouseDetail from WarehouseDetail warehouseDetail where warehouseDetail.lotDetail.id = :lotId")
    List<WarehouseDetail> queryAllByLotId(@Param("lotId") Integer lotId);

    @Query(
        "select warehouseDetail from WarehouseDetail warehouseDetail where warehouseDetail.createdAt >= :startDate and warehouseDetail.createdAt <= :endDate"
    )
    List<WarehouseDetail> queryAllByStartDateAndEndDate(
        @Param("startDate") ZonedDateTime startDate,
        @Param("endDate") ZonedDateTime endDate
    );
}
