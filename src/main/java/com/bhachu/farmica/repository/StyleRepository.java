package com.bhachu.farmica.repository;

import com.bhachu.farmica.domain.Style;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Style entity.
 */
@Repository
public interface StyleRepository extends JpaRepository<Style, Long> {
    @Query("select style from Style style where style.user.login = ?#{principal.username}")
    List<Style> findByUserIsCurrentUser();

    default Optional<Style> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Style> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Style> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(value = "select style from Style style left join fetch style.user", countQuery = "select count(style) from Style style")
    Page<Style> findAllWithToOneRelationships(Pageable pageable);

    @Query("select style from Style style left join fetch style.user")
    List<Style> findAllWithToOneRelationships();

    @Query("select style from Style style left join fetch style.user where style.id =:id")
    Optional<Style> findOneWithToOneRelationships(@Param("id") Long id);
}
