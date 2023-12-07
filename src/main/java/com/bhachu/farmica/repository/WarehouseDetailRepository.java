package com.bhachu.farmica.repository;

import com.bhachu.farmica.domain.WarehouseDetail;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the WarehouseDetail entity.
 */
@Repository
public interface WarehouseDetailRepository extends JpaRepository<WarehouseDetail, Long> {
    @Query("select warehouseDetail from WarehouseDetail warehouseDetail where warehouseDetail.user.login = ?#{principal.username}")
    List<WarehouseDetail> findByUserIsCurrentUser();

    default Optional<WarehouseDetail> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<WarehouseDetail> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<WarehouseDetail> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select warehouseDetail from WarehouseDetail warehouseDetail left join fetch warehouseDetail.user",
        countQuery = "select count(warehouseDetail) from WarehouseDetail warehouseDetail"
    )
    Page<WarehouseDetail> findAllWithToOneRelationships(Pageable pageable);

    @Query("select warehouseDetail from WarehouseDetail warehouseDetail left join fetch warehouseDetail.user")
    List<WarehouseDetail> findAllWithToOneRelationships();

    @Query("select warehouseDetail from WarehouseDetail warehouseDetail left join fetch warehouseDetail.user where warehouseDetail.id =:id")
    Optional<WarehouseDetail> findOneWithToOneRelationships(@Param("id") Long id);
}
