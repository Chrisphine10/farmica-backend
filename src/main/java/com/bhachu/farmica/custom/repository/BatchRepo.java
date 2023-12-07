package com.bhachu.farmica.custom.repository;

import com.bhachu.farmica.domain.BatchDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface BatchRepo extends JpaRepository<BatchDetail, Long> {
    default List<BatchDetail> findAllByRegion(Long regionId) {
        return this.queryAllByRegion(regionId);
    }

    @Query("select batchDetail from BatchDetail batchDetail where batchDetail.region.id = :regionId")
    List<BatchDetail> queryAllByRegion(@Param("regionId") Long regionId);
}
