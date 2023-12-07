package com.bhachu.farmica.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * A SalesDetail.
 */
@Entity
@Table(name = "sales_detail")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SalesDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "uicode")
    private String uicode;

    @NotNull
    @Column(name = "sales_date", nullable = false)
    private LocalDate salesDate;

    @NotNull
    @Column(name = "number_of_ct_ns", nullable = false)
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
    @JsonIgnoreProperties(value = { "packingZoneDetail", "lotDetail", "style", "user" }, allowSetters = true)
    private WarehouseDetail warehouseDetail;

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

    public SalesDetail id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUicode() {
        return this.uicode;
    }

    public SalesDetail uicode(String uicode) {
        this.setUicode(uicode);
        return this;
    }

    public void setUicode(String uicode) {
        this.uicode = uicode;
    }

    public LocalDate getSalesDate() {
        return this.salesDate;
    }

    public SalesDetail salesDate(LocalDate salesDate) {
        this.setSalesDate(salesDate);
        return this;
    }

    public void setSalesDate(LocalDate salesDate) {
        this.salesDate = salesDate;
    }

    public Integer getNumberOfCTNs() {
        return this.numberOfCTNs;
    }

    public SalesDetail numberOfCTNs(Integer numberOfCTNs) {
        this.setNumberOfCTNs(numberOfCTNs);
        return this;
    }

    public void setNumberOfCTNs(Integer numberOfCTNs) {
        this.numberOfCTNs = numberOfCTNs;
    }

    public Integer getReceivedCTNs() {
        return this.receivedCTNs;
    }

    public SalesDetail receivedCTNs(Integer receivedCTNs) {
        this.setReceivedCTNs(receivedCTNs);
        return this;
    }

    public void setReceivedCTNs(Integer receivedCTNs) {
        this.receivedCTNs = receivedCTNs;
    }

    public Integer getStartCTNNumber() {
        return this.startCTNNumber;
    }

    public SalesDetail startCTNNumber(Integer startCTNNumber) {
        this.setStartCTNNumber(startCTNNumber);
        return this;
    }

    public void setStartCTNNumber(Integer startCTNNumber) {
        this.startCTNNumber = startCTNNumber;
    }

    public Integer getEndCTNNumber() {
        return this.endCTNNumber;
    }

    public SalesDetail endCTNNumber(Integer endCTNNumber) {
        this.setEndCTNNumber(endCTNNumber);
        return this;
    }

    public void setEndCTNNumber(Integer endCTNNumber) {
        this.endCTNNumber = endCTNNumber;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public SalesDetail createdAt(ZonedDateTime createdAt) {
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

    public SalesDetail warehouseDetail(WarehouseDetail warehouseDetail) {
        this.setWarehouseDetail(warehouseDetail);
        return this;
    }

    public LotDetail getLotDetail() {
        return this.lotDetail;
    }

    public void setLotDetail(LotDetail lotDetail) {
        this.lotDetail = lotDetail;
    }

    public SalesDetail lotDetail(LotDetail lotDetail) {
        this.setLotDetail(lotDetail);
        return this;
    }

    public Style getStyle() {
        return this.style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public SalesDetail style(Style style) {
        this.setStyle(style);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SalesDetail user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SalesDetail)) {
            return false;
        }
        return getId() != null && getId().equals(((SalesDetail) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalesDetail{" +
            "id=" + getId() +
            ", uicode='" + getUicode() + "'" +
            ", salesDate='" + getSalesDate() + "'" +
            ", numberOfCTNs=" + getNumberOfCTNs() +
            ", receivedCTNs=" + getReceivedCTNs() +
            ", startCTNNumber=" + getStartCTNNumber() +
            ", endCTNNumber=" + getEndCTNNumber() +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
