package com.bhachu.farmica.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * A PackingZoneDetail.
 */
@Entity
@Table(name = "packing_zone_detail")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PackingZoneDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "uicode")
    private String uicode;

    @NotNull
    @Column(name = "pdn_date", nullable = false)
    private LocalDate pdnDate;

    @NotNull
    @Column(name = "package_date", nullable = false)
    private LocalDate packageDate;

    @NotNull
    @Column(name = "weight_received", nullable = false)
    private Double weightReceived;

    @NotNull
    @Column(name = "weight_balance", nullable = false)
    private Double weightBalance;

    @NotNull
    @Column(name = "number_of_ct_ns", nullable = false)
    private Integer numberOfCTNs;

    @Column(name = "received_ct_ns")
    private Integer receivedCTNs;

    @Column(name = "start_ctn_number")
    private Integer startCTNNumber;

    @Column(name = "end_ctn_number")
    private Integer endCTNNumber;

    @Column(name = "number_of_ct_ns_reworked")
    private Integer numberOfCTNsReworked;

    @Column(name = "number_of_ct_ns_sold")
    private Integer numberOfCTNsSold;

    @Column(name = "number_of_ct_ns_packed")
    private Integer numberOfCTNsPacked;

    @Column(name = "number_of_ct_ns_in_warehouse")
    private Integer numberOfCTNsInWarehouse;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

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

    public PackingZoneDetail id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUicode() {
        return this.uicode;
    }

    public PackingZoneDetail uicode(String uicode) {
        this.setUicode(uicode);
        return this;
    }

    public void setUicode(String uicode) {
        this.uicode = uicode;
    }

    public LocalDate getPdnDate() {
        return this.pdnDate;
    }

    public PackingZoneDetail pdnDate(LocalDate pdnDate) {
        this.setPdnDate(pdnDate);
        return this;
    }

    public void setPdnDate(LocalDate pdnDate) {
        this.pdnDate = pdnDate;
    }

    public LocalDate getPackageDate() {
        return this.packageDate;
    }

    public PackingZoneDetail packageDate(LocalDate packageDate) {
        this.setPackageDate(packageDate);
        return this;
    }

    public void setPackageDate(LocalDate packageDate) {
        this.packageDate = packageDate;
    }

    public Double getWeightReceived() {
        return this.weightReceived;
    }

    public PackingZoneDetail weightReceived(Double weightReceived) {
        this.setWeightReceived(weightReceived);
        return this;
    }

    public void setWeightReceived(Double weightReceived) {
        this.weightReceived = weightReceived;
    }

    public Double getWeightBalance() {
        return this.weightBalance;
    }

    public PackingZoneDetail weightBalance(Double weightBalance) {
        this.setWeightBalance(weightBalance);
        return this;
    }

    public void setWeightBalance(Double weightBalance) {
        this.weightBalance = weightBalance;
    }

    public Integer getNumberOfCTNs() {
        return this.numberOfCTNs;
    }

    public PackingZoneDetail numberOfCTNs(Integer numberOfCTNs) {
        this.setNumberOfCTNs(numberOfCTNs);
        return this;
    }

    public void setNumberOfCTNs(Integer numberOfCTNs) {
        this.numberOfCTNs = numberOfCTNs;
    }

    public Integer getReceivedCTNs() {
        return this.receivedCTNs;
    }

    public PackingZoneDetail receivedCTNs(Integer receivedCTNs) {
        this.setReceivedCTNs(receivedCTNs);
        return this;
    }

    public void setReceivedCTNs(Integer receivedCTNs) {
        this.receivedCTNs = receivedCTNs;
    }

    public Integer getStartCTNNumber() {
        return this.startCTNNumber;
    }

    public PackingZoneDetail startCTNNumber(Integer startCTNNumber) {
        this.setStartCTNNumber(startCTNNumber);
        return this;
    }

    public void setStartCTNNumber(Integer startCTNNumber) {
        this.startCTNNumber = startCTNNumber;
    }

    public Integer getEndCTNNumber() {
        return this.endCTNNumber;
    }

    public PackingZoneDetail endCTNNumber(Integer endCTNNumber) {
        this.setEndCTNNumber(endCTNNumber);
        return this;
    }

    public void setEndCTNNumber(Integer endCTNNumber) {
        this.endCTNNumber = endCTNNumber;
    }

    public Integer getNumberOfCTNsReworked() {
        return this.numberOfCTNsReworked;
    }

    public PackingZoneDetail numberOfCTNsReworked(Integer numberOfCTNsReworked) {
        this.setNumberOfCTNsReworked(numberOfCTNsReworked);
        return this;
    }

    public void setNumberOfCTNsReworked(Integer numberOfCTNsReworked) {
        this.numberOfCTNsReworked = numberOfCTNsReworked;
    }

    public Integer getNumberOfCTNsSold() {
        return this.numberOfCTNsSold;
    }

    public PackingZoneDetail numberOfCTNsSold(Integer numberOfCTNsSold) {
        this.setNumberOfCTNsSold(numberOfCTNsSold);
        return this;
    }

    public void setNumberOfCTNsSold(Integer numberOfCTNsSold) {
        this.numberOfCTNsSold = numberOfCTNsSold;
    }

    public Integer getNumberOfCTNsPacked() {
        return this.numberOfCTNsPacked;
    }

    public PackingZoneDetail numberOfCTNsPacked(Integer numberOfCTNsPacked) {
        this.setNumberOfCTNsPacked(numberOfCTNsPacked);
        return this;
    }

    public void setNumberOfCTNsPacked(Integer numberOfCTNsPacked) {
        this.numberOfCTNsPacked = numberOfCTNsPacked;
    }

    public Integer getNumberOfCTNsInWarehouse() {
        return this.numberOfCTNsInWarehouse;
    }

    public PackingZoneDetail numberOfCTNsInWarehouse(Integer numberOfCTNsInWarehouse) {
        this.setNumberOfCTNsInWarehouse(numberOfCTNsInWarehouse);
        return this;
    }

    public void setNumberOfCTNsInWarehouse(Integer numberOfCTNsInWarehouse) {
        this.numberOfCTNsInWarehouse = numberOfCTNsInWarehouse;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public PackingZoneDetail createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LotDetail getLotDetail() {
        return this.lotDetail;
    }

    public void setLotDetail(LotDetail lotDetail) {
        this.lotDetail = lotDetail;
    }

    public PackingZoneDetail lotDetail(LotDetail lotDetail) {
        this.setLotDetail(lotDetail);
        return this;
    }

    public Style getStyle() {
        return this.style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public PackingZoneDetail style(Style style) {
        this.setStyle(style);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PackingZoneDetail user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PackingZoneDetail)) {
            return false;
        }
        return getId() != null && getId().equals(((PackingZoneDetail) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PackingZoneDetail{" +
            "id=" + getId() +
            ", uicode='" + getUicode() + "'" +
            ", pdnDate='" + getPdnDate() + "'" +
            ", packageDate='" + getPackageDate() + "'" +
            ", weightReceived=" + getWeightReceived() +
            ", weightBalance=" + getWeightBalance() +
            ", numberOfCTNs=" + getNumberOfCTNs() +
            ", receivedCTNs=" + getReceivedCTNs() +
            ", startCTNNumber=" + getStartCTNNumber() +
            ", endCTNNumber=" + getEndCTNNumber() +
            ", numberOfCTNsReworked=" + getNumberOfCTNsReworked() +
            ", numberOfCTNsSold=" + getNumberOfCTNsSold() +
            ", numberOfCTNsPacked=" + getNumberOfCTNsPacked() +
            ", numberOfCTNsInWarehouse=" + getNumberOfCTNsInWarehouse() +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
