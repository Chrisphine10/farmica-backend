package com.bhachu.farmica.domain;

import com.bhachu.farmica.domain.enumeration.ReworkStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * A ReworkDetail.
 */
@Entity
@Table(name = "rework_detail")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReworkDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "uicode", unique = true)
    private String uicode;

    @NotNull
    @Column(name = "pdn_date", nullable = false)
    private LocalDate pdnDate;

    @NotNull
    @Column(name = "rework_date", nullable = false)
    private LocalDate reworkDate;

    @NotNull
    @Column(name = "number_of_ct_ns", nullable = false)
    private Integer numberOfCTNs;

    @Column(name = "start_ctn_number")
    private Integer startCTNNumber;

    @Column(name = "end_ctn_number")
    private Integer endCTNNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReworkStatus status;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "packingZoneDetail", "lotDetail", "style", "user" }, allowSetters = true)
    private WarehouseDetail warehouseDetail;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "batchDetail", "user" }, allowSetters = true)
    private LotDetail lotDetail;

    @ManyToOne(optional = false)
    @NotNull
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ReworkDetail id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUicode() {
        return this.uicode;
    }

    public ReworkDetail uicode(String uicode) {
        this.setUicode(uicode);
        return this;
    }

    public void setUicode(String uicode) {
        this.uicode = uicode;
    }

    public LocalDate getPdnDate() {
        return this.pdnDate;
    }

    public ReworkDetail pdnDate(LocalDate pdnDate) {
        this.setPdnDate(pdnDate);
        return this;
    }

    public void setPdnDate(LocalDate pdnDate) {
        this.pdnDate = pdnDate;
    }

    public LocalDate getReworkDate() {
        return this.reworkDate;
    }

    public ReworkDetail reworkDate(LocalDate reworkDate) {
        this.setReworkDate(reworkDate);
        return this;
    }

    public void setReworkDate(LocalDate reworkDate) {
        this.reworkDate = reworkDate;
    }

    public Integer getNumberOfCTNs() {
        return this.numberOfCTNs;
    }

    public ReworkDetail numberOfCTNs(Integer numberOfCTNs) {
        this.setNumberOfCTNs(numberOfCTNs);
        return this;
    }

    public void setNumberOfCTNs(Integer numberOfCTNs) {
        this.numberOfCTNs = numberOfCTNs;
    }

    public Integer getStartCTNNumber() {
        return this.startCTNNumber;
    }

    public ReworkDetail startCTNNumber(Integer startCTNNumber) {
        this.setStartCTNNumber(startCTNNumber);
        return this;
    }

    public void setStartCTNNumber(Integer startCTNNumber) {
        this.startCTNNumber = startCTNNumber;
    }

    public Integer getEndCTNNumber() {
        return this.endCTNNumber;
    }

    public ReworkDetail endCTNNumber(Integer endCTNNumber) {
        this.setEndCTNNumber(endCTNNumber);
        return this;
    }

    public void setEndCTNNumber(Integer endCTNNumber) {
        this.endCTNNumber = endCTNNumber;
    }

    public ReworkStatus getStatus() {
        return this.status;
    }

    public ReworkDetail status(ReworkStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(ReworkStatus status) {
        this.status = status;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public ReworkDetail createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public WarehouseDetail getWarehouseDetail() {
        return this.warehouseDetail;
    }

    public void setWarehouseDetail(WarehouseDetail warehouseDetail) {
        this.warehouseDetail = warehouseDetail;
    }

    public ReworkDetail warehouseDetail(WarehouseDetail warehouseDetail) {
        this.setWarehouseDetail(warehouseDetail);
        return this;
    }

    public LotDetail getLotDetail() {
        return this.lotDetail;
    }

    public void setLotDetail(LotDetail lotDetail) {
        this.lotDetail = lotDetail;
    }

    public ReworkDetail lotDetail(LotDetail lotDetail) {
        this.setLotDetail(lotDetail);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ReworkDetail user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReworkDetail)) {
            return false;
        }
        return getId() != null && getId().equals(((ReworkDetail) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReworkDetail{" +
            "id=" + getId() +
            ", uicode='" + getUicode() + "'" +
            ", pdnDate='" + getPdnDate() + "'" +
            ", reworkDate='" + getReworkDate() + "'" +
            ", numberOfCTNs=" + getNumberOfCTNs() +
            ", startCTNNumber=" + getStartCTNNumber() +
            ", endCTNNumber=" + getEndCTNNumber() +
            ", status='" + getStatus() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
