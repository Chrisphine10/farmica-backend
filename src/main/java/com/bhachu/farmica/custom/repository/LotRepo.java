package com.bhachu.farmica.custom.repository;

import com.bhachu.farmica.domain.LotDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface LotRepo extends JpaRepository<LotDetail, Long> {
    default List<LotDetail> findAllByBatchDetail(Long batchId) {
        return this.queryAllByBatchDetail(batchId);
    }

    @Query("select lotDetail from LotDetail lotDetail where lotDetail.batchDetail.id = :batchId")
    List<LotDetail> queryAllByBatchDetail(@Param("batchId") Long batchId);
}
