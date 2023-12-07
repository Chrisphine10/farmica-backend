package com.bhachu.farmica.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A BatchDetail.
 */
@Entity
@Table(name = "batch_detail")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BatchDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "batch_no", nullable = false, unique = true)
    private String batchNo;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @Column(name = "drier")
    private Integer drier;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "user" }, allowSetters = true)
    private Region region;

    @ManyToOne(optional = false)
    @NotNull
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BatchDetail id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBatchNo() {
        return this.batchNo;
    }

    public BatchDetail batchNo(String batchNo) {
        this.setBatchNo(batchNo);
        return this;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public BatchDetail createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getDrier() {
        return this.drier;
    }

    public BatchDetail drier(Integer drier) {
        this.setDrier(drier);
        return this;
    }

    public void setDrier(Integer drier) {
        this.drier = drier;
    }

    public Region getRegion() {
        return this.region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public BatchDetail region(Region region) {
        this.setRegion(region);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BatchDetail user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BatchDetail)) {
            return false;
        }
        return getId() != null && getId().equals(((BatchDetail) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BatchDetail{" +
            "id=" + getId() +
            ", batchNo='" + getBatchNo() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", drier=" + getDrier() +
            "}";
    }
}
