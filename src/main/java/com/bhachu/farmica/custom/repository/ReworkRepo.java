package com.bhachu.farmica.custom.repository;

import com.bhachu.farmica.domain.ReworkDetail;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface ReworkRepo extends JpaRepository<ReworkDetail, Long> {
    default List<ReworkDetail> findAllByLotDetail(Long lotNo) {
        return this.queryAllByLotDetail(lotNo);
    }

    // find all rework details with a start date and end date
    default List<ReworkDetail> findAllByStartDateAndEndDate(ZonedDateTime startDate, ZonedDateTime endDate) {
        return this.queryAllByStartDateAndEndDate(startDate, endDate);
    }

    // find total rework count from rework details with a start date and end date
    default Integer findTotalReworkCountByStartDateAndEndDate(ZonedDateTime startDate, ZonedDateTime endDate) {
        List<ReworkDetail> totalReworkDetails = findAllByStartDateAndEndDate(startDate, endDate);
        Integer totalReworkCount = 0;
        for (ReworkDetail reworkDetail : totalReworkDetails) {
            totalReworkCount += reworkDetail.getNumberOfCTNs();
        }
        return totalReworkCount;
    }

    // find one by uicode
    default Optional<ReworkDetail> findOneByUicode(String uicode) {
        return this.queryAllByUicode(uicode);
    }

    // query one rework details by uicode
    @Query(
        "select reworkDetail from ReworkDetail reworkDetail where reworkDetail.uicode = :uicode order by reworkDetail.createdAt desc limit 1"
    )
    Optional<ReworkDetail> queryAllByUicode(@Param("uicode") String uicode);

    @Query("select reworkDetail from ReworkDetail reworkDetail where reworkDetail.lotDetail.id = :lotNo")
    List<ReworkDetail> queryAllByLotDetail(@Param("lotNo") Long lotNo);

    @Query(
        "select reworkDetail from ReworkDetail reworkDetail where reworkDetail.createdAt >= :startDate and reworkDetail.createdAt <= :endDate"
    )
    List<ReworkDetail> queryAllByStartDateAndEndDate(@Param("startDate") ZonedDateTime startDate, @Param("endDate") ZonedDateTime endDate);
}
