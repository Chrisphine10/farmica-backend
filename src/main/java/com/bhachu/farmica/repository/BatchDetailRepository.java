package com.bhachu.farmica.repository;

import com.bhachu.farmica.domain.BatchDetail;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BatchDetail entity.
 */
@Repository
public interface BatchDetailRepository extends JpaRepository<BatchDetail, Long> {
    @Query("select batchDetail from BatchDetail batchDetail where batchDetail.user.login = ?#{principal.username}")
    List<BatchDetail> findByUserIsCurrentUser();

    default Optional<BatchDetail> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<BatchDetail> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<BatchDetail> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select batchDetail from BatchDetail batchDetail left join fetch batchDetail.user",
        countQuery = "select count(batchDetail) from BatchDetail batchDetail"
    )
    Page<BatchDetail> findAllWithToOneRelationships(Pageable pageable);

    @Query("select batchDetail from BatchDetail batchDetail left join fetch batchDetail.user")
    List<BatchDetail> findAllWithToOneRelationships();

    @Query("select batchDetail from BatchDetail batchDetail left join fetch batchDetail.user where batchDetail.id =:id")
    Optional<BatchDetail> findOneWithToOneRelationships(@Param("id") Long id);
}
