package com.bhachu.farmica.repository;

import com.bhachu.farmica.domain.PackingZoneDetail;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PackingZoneDetail entity.
 */
@Repository
public interface PackingZoneDetailRepository extends JpaRepository<PackingZoneDetail, Long> {
    @Query("select packingZoneDetail from PackingZoneDetail packingZoneDetail where packingZoneDetail.user.login = ?#{principal.username}")
    List<PackingZoneDetail> findByUserIsCurrentUser();

    default Optional<PackingZoneDetail> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<PackingZoneDetail> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<PackingZoneDetail> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select packingZoneDetail from PackingZoneDetail packingZoneDetail left join fetch packingZoneDetail.user",
        countQuery = "select count(packingZoneDetail) from PackingZoneDetail packingZoneDetail"
    )
    Page<PackingZoneDetail> findAllWithToOneRelationships(Pageable pageable);

    @Query("select packingZoneDetail from PackingZoneDetail packingZoneDetail left join fetch packingZoneDetail.user")
    List<PackingZoneDetail> findAllWithToOneRelationships();

    @Query(
        "select packingZoneDetail from PackingZoneDetail packingZoneDetail left join fetch packingZoneDetail.user where packingZoneDetail.id =:id"
    )
    Optional<PackingZoneDetail> findOneWithToOneRelationships(@Param("id") Long id);
}
