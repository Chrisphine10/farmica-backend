package com.bhachu.farmica.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A LotDetail.
 */
@Entity
@Table(name = "lot_detail")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LotDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "lot_no", nullable = false, unique = true)
    private Integer lotNo;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "region", "user" }, allowSetters = true)
    private BatchDetail batchDetail;

    @ManyToOne(optional = false)
    @NotNull
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LotDetail id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLotNo() {
        return this.lotNo;
    }

    public LotDetail lotNo(Integer lotNo) {
        this.setLotNo(lotNo);
        return this;
    }

    public void setLotNo(Integer lotNo) {
        this.lotNo = lotNo;
    }

    public BatchDetail getBatchDetail() {
        return this.batchDetail;
    }

    public void setBatchDetail(BatchDetail batchDetail) {
        this.batchDetail = batchDetail;
    }

    public LotDetail batchDetail(BatchDetail batchDetail) {
        this.setBatchDetail(batchDetail);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LotDetail user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LotDetail)) {
            return false;
        }
        return getId() != null && getId().equals(((LotDetail) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LotDetail{" +
            "id=" + getId() +
            ", lotNo=" + getLotNo() +
            "}";
    }
}
