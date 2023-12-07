package com.bhachu.farmica.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.bhachu.farmica.domain.ReworkComment} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReworkCommentDTO implements Serializable {

    private Long id;

    @Lob
    private String comment;

    @NotNull
    private ZonedDateTime createdAt;

    private UserDTO user;

    private ReworkDetailDTO reworkDetail;

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

    public ReworkDetailDTO getReworkDetail() {
        return reworkDetail;
    }

    public void setReworkDetail(ReworkDetailDTO reworkDetail) {
        this.reworkDetail = reworkDetail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReworkCommentDTO)) {
            return false;
        }

        ReworkCommentDTO reworkCommentDTO = (ReworkCommentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, reworkCommentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReworkCommentDTO{" +
            "id=" + getId() +
            ", comment='" + getComment() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", user=" + getUser() +
            ", reworkDetail=" + getReworkDetail() +
            "}";
    }
}
