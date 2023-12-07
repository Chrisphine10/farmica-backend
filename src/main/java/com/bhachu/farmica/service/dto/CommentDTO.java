package com.bhachu.farmica.service.dto;

import com.bhachu.farmica.domain.enumeration.CurrentZone;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.bhachu.farmica.domain.Comment} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CommentDTO implements Serializable {

    private Long id;

    @Lob
    private String comment;

    private CurrentZone status;

    private Integer zoneId;

    @NotNull
    private ZonedDateTime createdAt;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public CurrentZone getStatus() {
        return status;
    }

    public void setStatus(CurrentZone status) {
        this.status = status;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommentDTO)) {
            return false;
        }

        CommentDTO commentDTO = (CommentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, commentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommentDTO{" +
            "id=" + getId() +
            ", comment='" + getComment() + "'" +
            ", status='" + getStatus() + "'" +
            ", zoneId=" + getZoneId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", user=" + getUser() +
            "}";
    }
}
