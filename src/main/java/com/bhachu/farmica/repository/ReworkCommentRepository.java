package com.bhachu.farmica.repository;

import com.bhachu.farmica.domain.ReworkComment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ReworkComment entity.
 */
@Repository
public interface ReworkCommentRepository extends JpaRepository<ReworkComment, Long> {
    @Query("select reworkComment from ReworkComment reworkComment where reworkComment.user.login = ?#{principal.username}")
    List<ReworkComment> findByUserIsCurrentUser();

    default Optional<ReworkComment> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ReworkComment> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ReworkComment> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select reworkComment from ReworkComment reworkComment left join fetch reworkComment.user",
        countQuery = "select count(reworkComment) from ReworkComment reworkComment"
    )
    Page<ReworkComment> findAllWithToOneRelationships(Pageable pageable);

    @Query("select reworkComment from ReworkComment reworkComment left join fetch reworkComment.user")
    List<ReworkComment> findAllWithToOneRelationships();

    @Query("select reworkComment from ReworkComment reworkComment left join fetch reworkComment.user where reworkComment.id =:id")
    Optional<ReworkComment> findOneWithToOneRelationships(@Param("id") Long id);
}
