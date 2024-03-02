package com.bhachu.farmica.custom.repository;

import com.bhachu.farmica.domain.SalesDetail;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface SalesRepo extends JpaRepository<SalesDetail, Long> {
    default List<SalesDetail> findAllByLotDetail(Integer lotNo) {
        return this.queryAllByLotDetail(lotNo);
    }

    default List<SalesDetail> findAllByLotId(Integer lotId) {
        return this.queryAllByLotId(lotId);
    }

    default List<SalesDetail> findAllByStyle(Integer styleId) {
        return this.queryAllByStyle(styleId);
    }

    // find all sales details with a start date and end date
    default List<SalesDetail> findAllByStartDateAndEndDate(ZonedDateTime startDate, ZonedDateTime endDate) {
        List<SalesDetail> totalSalesDetails = this.queryAllByStartDateAndEndDate(startDate, endDate);
        return totalSalesDetails;
    }

    // find total sales count from sales details with a start date and end date
    default Integer findTotalSalesCountByStartDateAndEndDate(ZonedDateTime startDate, ZonedDateTime endDate) {
        List<SalesDetail> totalSalesDetails = findAllByStartDateAndEndDate(startDate, endDate);
        Integer totalSalesCount = 0;
        for (SalesDetail salesDetail : totalSalesDetails) {
            totalSalesCount = totalSalesCount + salesDetail.getNumberOfCTNs();
        }
        return totalSalesCount;
    }

    default Optional<SalesDetail> findOneByUicode(String uicode) {
        return this.queryAllByUicode(uicode);
    }

    // find total sales count from sales details with zone detail id
    default Integer findTotalSalesCountByWarehouseDetailId(Long warehouseId) {
        List<SalesDetail> totalSalesDetails = this.findAllByZoneDetailId(warehouseId);
        Integer totalSalesCount = 0;
        for (SalesDetail salesDetail : totalSalesDetails) {
            totalSalesCount += salesDetail.getNumberOfCTNs();
        }
        return totalSalesCount;
    }

    // query all sales details with zone detail id
    @Query("select salesDetail from SalesDetail salesDetail where salesDetail.warehouseDetail.id = :zoneDetailId")
    List<SalesDetail> findAllByZoneDetailId(@Param("zoneDetailId") Long zoneDetailId);

    // query one sales details by uicode
    @Query("select salesDetail from SalesDetail salesDetail where salesDetail.uicode = :uicode order by salesDetail.createdAt desc limit 1")
    Optional<SalesDetail> queryAllByUicode(@Param("uicode") String uicode);

    @Query("select salesDetail from SalesDetail salesDetail where salesDetail.lotDetail.lotNo = :lotNo")
    List<SalesDetail> queryAllByLotDetail(@Param("lotNo") Integer lotNo);

    @Query("select salesDetail from SalesDetail salesDetail where salesDetail.style.id = :styleId")
    List<SalesDetail> queryAllByStyle(@Param("styleId") Integer styleId);

    @Query("select salesDetail from SalesDetail salesDetail where salesDetail.lotDetail.id = :lotId")
    List<SalesDetail> queryAllByLotId(@Param("lotId") Integer lotId);

    @Query(
        "select salesDetail from SalesDetail salesDetail where salesDetail.createdAt >= :startDate and salesDetail.createdAt <= :endDate"
    )
    List<SalesDetail> queryAllByStartDateAndEndDate(@Param("startDate") ZonedDateTime startDate, @Param("endDate") ZonedDateTime endDate);
}
