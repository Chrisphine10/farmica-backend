package com.bhachu.farmica.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * A WarehouseDetail.
 */
@Entity
@Table(name = "warehouse_detail")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WarehouseDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "uicode")
    private String uicode;

    @NotNull
    @Column(name = "warehouse_date", nullable = false)
    private LocalDate warehouseDate;

    @Column(name = "number_of_ct_ns")
    private Integer numberOfCTNs;

    @Column(name = "received_ct_ns")
    private Integer receivedCTNs;

    @Column(name = "start_ctn_number")
    private Integer startCTNNumber;

    @Column(name = "end_ctn_number")
    private Integer endCTNNumber;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "lotDetail", "style", "user" }, allowSetters = true)
    private PackingZoneDetail packingZoneDetail;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "batchDetail", "user" }, allowSetters = true)
    private LotDetail lotDetail;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "user" }, allowSetters = true)
    private Style style;

    @ManyToOne(optional = false)
    @NotNull
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public WarehouseDetail id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUicode() {
        return this.uicode;
    }

    public WarehouseDetail uicode(String uicode) {
        this.setUicode(uicode);
        return this;
    }

    public void setUicode(String uicode) {
        this.uicode = uicode;
    }

    public LocalDate getWarehouseDate() {
        return this.warehouseDate;
    }

    public WarehouseDetail warehouseDate(LocalDate warehouseDate) {
        this.setWarehouseDate(warehouseDate);
        return this;
    }

    public void setWarehouseDate(LocalDate warehouseDate) {
        this.warehouseDate = warehouseDate;
    }

    public Integer getNumberOfCTNs() {
        return this.numberOfCTNs;
    }

    public WarehouseDetail numberOfCTNs(Integer numberOfCTNs) {
        this.setNumberOfCTNs(numberOfCTNs);
        return this;
    }

    public void setNumberOfCTNs(Integer numberOfCTNs) {
        this.numberOfCTNs = numberOfCTNs;
    }

    public Integer getReceivedCTNs() {
        return this.receivedCTNs;
    }

    public WarehouseDetail receivedCTNs(Integer receivedCTNs) {
        this.setReceivedCTNs(receivedCTNs);
        return this;
    }

    public void setReceivedCTNs(Integer receivedCTNs) {
        this.receivedCTNs = receivedCTNs;
    }

    public Integer getStartCTNNumber() {
        return this.startCTNNumber;
    }

    public WarehouseDetail startCTNNumber(Integer startCTNNumber) {
        this.setStartCTNNumber(startCTNNumber);
        return this;
    }

    public void setStartCTNNumber(Integer startCTNNumber) {
        this.startCTNNumber = startCTNNumber;
    }

    public Integer getEndCTNNumber() {
        return this.endCTNNumber;
    }

    public WarehouseDetail endCTNNumber(Integer endCTNNumber) {
        this.setEndCTNNumber(endCTNNumber);
        return this;
    }

    public void setEndCTNNumber(Integer endCTNNumber) {
        this.endCTNNumber = endCTNNumber;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public WarehouseDetail createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public PackingZoneDetail getPackingZoneDetail() {
        return this.packingZoneDetail;
    }

    public void setPackingZoneDetail(PackingZoneDetail packingZoneDetail) {
        this.packingZoneDetail = packingZoneDetail;
    }

    public WarehouseDetail packingZoneDetail(PackingZoneDetail packingZoneDetail) {
        this.setPackingZoneDetail(packingZoneDetail);
        return this;
    }

    public LotDetail getLotDetail() {
        return this.lotDetail;
    }

    public void setLotDetail(LotDetail lotDetail) {
        this.lotDetail = lotDetail;
    }

    public WarehouseDetail lotDetail(LotDetail lotDetail) {
        this.setLotDetail(lotDetail);
        return this;
    }

    public Style getStyle() {
        return this.style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public WarehouseDetail style(Style style) {
        this.setStyle(style);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public WarehouseDetail user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WarehouseDetail)) {
            return false;
        }
        return getId() != null && getId().equals(((WarehouseDetail) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WarehouseDetail{" +
            "id=" + getId() +
            ", uicode='" + getUicode() + "'" +
            ", warehouseDate='" + getWarehouseDate() + "'" +
            ", numberOfCTNs=" + getNumberOfCTNs() +
            ", receivedCTNs=" + getReceivedCTNs() +
            ", startCTNNumber=" + getStartCTNNumber() +
            ", endCTNNumber=" + getEndCTNNumber() +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
