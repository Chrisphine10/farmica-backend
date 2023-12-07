package com.bhachu.farmica.custom.repository;

import com.bhachu.farmica.domain.Comment;
import com.bhachu.farmica.domain.enumeration.CurrentZone;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface CommentsRepo extends JpaRepository<Comment, Long> {
    // find all comments by zone id and status
    default List<Comment> findAllByZoneIdAndStatus(Integer zoneId, CurrentZone status) {
        return this.queryAllByZoneIdAndStatus(zoneId, status);
    }

    // query all comments by zone id and status
    @Query(
        "select comment from Comment comment where comment.zoneId = :zoneId and comment.status = :status order by comment.createdAt desc"
    )
    List<Comment> queryAllByZoneIdAndStatus(@Param("zoneId") Integer zoneId, @Param("status") CurrentZone status);
}
