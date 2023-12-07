package com.bhachu.farmica.repository;

import com.bhachu.farmica.domain.LotDetail;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LotDetail entity.
 */
@Repository
public interface LotDetailRepository extends JpaRepository<LotDetail, Long> {
    @Query("select lotDetail from LotDetail lotDetail where lotDetail.user.login = ?#{principal.username}")
    List<LotDetail> findByUserIsCurrentUser();

    default Optional<LotDetail> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<LotDetail> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<LotDetail> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select lotDetail from LotDetail lotDetail left join fetch lotDetail.user",
        countQuery = "select count(lotDetail) from LotDetail lotDetail"
    )
    Page<LotDetail> findAllWithToOneRelationships(Pageable pageable);

    @Query("select lotDetail from LotDetail lotDetail left join fetch lotDetail.user")
    List<LotDetail> findAllWithToOneRelationships();

    @Query("select lotDetail from LotDetail lotDetail left join fetch lotDetail.user where lotDetail.id =:id")
    Optional<LotDetail> findOneWithToOneRelationships(@Param("id") Long id);
}
