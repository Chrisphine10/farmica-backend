package com.bhachu.farmica.domain;

import com.bhachu.farmica.domain.enumeration.CurrentZone;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Comment.
 */
@Entity
@Table(name = "comment")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "comment", nullable = false)
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CurrentZone status;

    @Column(name = "zone_id")
    private Integer zoneId;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @ManyToOne(optional = false)
    @NotNull
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Comment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return this.comment;
    }

    public Comment comment(String comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public CurrentZone getStatus() {
        return this.status;
    }

    public Comment status(CurrentZone status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(CurrentZone status) {
        this.status = status;
    }

    public Integer getZoneId() {
        return this.zoneId;
    }

    public Comment zoneId(Integer zoneId) {
        this.setZoneId(zoneId);
        return this;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public Comment createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Comment user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comment)) {
            return false;
        }
        return getId() != null && getId().equals(((Comment) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Comment{" +
            "id=" + getId() +
            ", comment='" + getComment() + "'" +
            ", status='" + getStatus() + "'" +
            ", zoneId=" + getZoneId() +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
