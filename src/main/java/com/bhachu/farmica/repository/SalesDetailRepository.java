package com.bhachu.farmica.repository;

import com.bhachu.farmica.domain.SalesDetail;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SalesDetail entity.
 */
@Repository
public interface SalesDetailRepository extends JpaRepository<SalesDetail, Long> {
    @Query("select salesDetail from SalesDetail salesDetail where salesDetail.user.login = ?#{principal.username}")
    List<SalesDetail> findByUserIsCurrentUser();

    default Optional<SalesDetail> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<SalesDetail> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<SalesDetail> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select salesDetail from SalesDetail salesDetail left join fetch salesDetail.user",
        countQuery = "select count(salesDetail) from SalesDetail salesDetail"
    )
    Page<SalesDetail> findAllWithToOneRelationships(Pageable pageable);

    @Query("select salesDetail from SalesDetail salesDetail left join fetch salesDetail.user")
    List<SalesDetail> findAllWithToOneRelationships();

    @Query("select salesDetail from SalesDetail salesDetail left join fetch salesDetail.user where salesDetail.id =:id")
    Optional<SalesDetail> findOneWithToOneRelationships(@Param("id") Long id);
}
