package com.bhachu.farmica.repository;

import com.bhachu.farmica.domain.ReworkDetail;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ReworkDetail entity.
 */
@Repository
public interface ReworkDetailRepository extends JpaRepository<ReworkDetail, Long> {
    @Query("select reworkDetail from ReworkDetail reworkDetail where reworkDetail.user.login = ?#{principal.username}")
    List<ReworkDetail> findByUserIsCurrentUser();

    default Optional<ReworkDetail> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ReworkDetail> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ReworkDetail> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select reworkDetail from ReworkDetail reworkDetail left join fetch reworkDetail.user",
        countQuery = "select count(reworkDetail) from ReworkDetail reworkDetail"
    )
    Page<ReworkDetail> findAllWithToOneRelationships(Pageable pageable);

    @Query("select reworkDetail from ReworkDetail reworkDetail left join fetch reworkDetail.user")
    List<ReworkDetail> findAllWithToOneRelationships();

    @Query("select reworkDetail from ReworkDetail reworkDetail left join fetch reworkDetail.user where reworkDetail.id =:id")
    Optional<ReworkDetail> findOneWithToOneRelationships(@Param("id") Long id);
}
