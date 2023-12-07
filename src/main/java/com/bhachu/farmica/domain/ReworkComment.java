package com.bhachu.farmica.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A ReworkComment.
 */
@Entity
@Table(name = "rework_comment")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReworkComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "comment", nullable = false)
    private String comment;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @ManyToOne(optional = false)
    @NotNull
    private User user;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "lotDetail", "user" }, allowSetters = true)
    private ReworkDetail reworkDetail;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ReworkComment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return this.comment;
    }

    public ReworkComment comment(String comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public ReworkComment createdAt(ZonedDateTime createdAt) {
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

    public ReworkComment user(User user) {
        this.setUser(user);
        return this;
    }

    public ReworkDetail getReworkDetail() {
        return this.reworkDetail;
    }

    public void setReworkDetail(ReworkDetail reworkDetail) {
        this.reworkDetail = reworkDetail;
    }

    public ReworkComment reworkDetail(ReworkDetail reworkDetail) {
        this.setReworkDetail(reworkDetail);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReworkComment)) {
            return false;
        }
        return getId() != null && getId().equals(((ReworkComment) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReworkComment{" +
            "id=" + getId() +
            ", comment='" + getComment() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
