package com.bhachu.farmica.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.bhachu.farmica.domain.PackingZoneDetail} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PackingZoneDetailDTO implements Serializable {

    private Long id;

    private String uicode;

    @NotNull
    private LocalDate pdnDate;

    @NotNull
    private LocalDate packageDate;

    @NotNull
    private Double weightReceived;

    @NotNull
    private Double weightBalance;

    @NotNull
    private Integer numberOfCTNs;

    private Integer receivedCTNs;

    private Integer startCTNNumber;

    private Integer endCTNNumber;

    private Integer numberOfCTNsReworked;

    private Integer numberOfCTNsSold;

    private Integer numberOfCTNsPacked;

    private Integer numberOfCTNsInWarehouse;

    private ZonedDateTime createdAt;

    private LotDetailDTO lotDetail;

    private StyleDTO style;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUicode() {
        return uicode;
    }

    public void setUicode(String uicode) {
        this.uicode = uicode;
    }

    public LocalDate getPdnDate() {
        return pdnDate;
    }

    public void setPdnDate(LocalDate pdnDate) {
        this.pdnDate = pdnDate;
    }

    public LocalDate getPackageDate() {
        return packageDate;
    }

    public void setPackageDate(LocalDate packageDate) {
        this.packageDate = packageDate;
    }

    public Double getWeightReceived() {
        return weightReceived;
    }

    public void setWeightReceived(Double weightReceived) {
        this.weightReceived = weightReceived;
    }

    public Double getWeightBalance() {
        return weightBalance;
    }

    public void setWeightBalance(Double weightBalance) {
        this.weightBalance = weightBalance;
    }

    public Integer getNumberOfCTNs() {
        return numberOfCTNs;
    }

    public void setNumberOfCTNs(Integer numberOfCTNs) {
        this.numberOfCTNs = numberOfCTNs;
    }

    public Integer getReceivedCTNs() {
        return receivedCTNs;
    }

    public void setReceivedCTNs(Integer receivedCTNs) {
        this.receivedCTNs = receivedCTNs;
    }

    public Integer getStartCTNNumber() {
        return startCTNNumber;
    }

    public void setStartCTNNumber(Integer startCTNNumber) {
        this.startCTNNumber = startCTNNumber;
    }

    public Integer getEndCTNNumber() {
        return endCTNNumber;
    }

    public void setEndCTNNumber(Integer endCTNNumber) {
        this.endCTNNumber = endCTNNumber;
    }

    public Integer getNumberOfCTNsReworked() {
        return numberOfCTNsReworked;
    }

    public void setNumberOfCTNsReworked(Integer numberOfCTNsReworked) {
        this.numberOfCTNsReworked = numberOfCTNsReworked;
    }

    public Integer getNumberOfCTNsSold() {
        return numberOfCTNsSold;
    }

    public void setNumberOfCTNsSold(Integer numberOfCTNsSold) {
        this.numberOfCTNsSold = numberOfCTNsSold;
    }

    public Integer getNumberOfCTNsPacked() {
        return numberOfCTNsPacked;
    }

    public void setNumberOfCTNsPacked(Integer numberOfCTNsPacked) {
        this.numberOfCTNsPacked = numberOfCTNsPacked;
    }

    public Integer getNumberOfCTNsInWarehouse() {
        return numberOfCTNsInWarehouse;
    }

    public void setNumberOfCTNsInWarehouse(Integer numberOfCTNsInWarehouse) {
        this.numberOfCTNsInWarehouse = numberOfCTNsInWarehouse;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LotDetailDTO getLotDetail() {
        return lotDetail;
    }

    public void setLotDetail(LotDetailDTO lotDetail) {
        this.lotDetail = lotDetail;
    }

    public StyleDTO getStyle() {
        return style;
    }

    public void setStyle(StyleDTO style) {
        this.style = style;
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
        if (!(o instanceof PackingZoneDetailDTO)) {
            return false;
        }

        PackingZoneDetailDTO packingZoneDetailDTO = (PackingZoneDetailDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, packingZoneDetailDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PackingZoneDetailDTO{" +
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
            ", lotDetail=" + getLotDetail() +
            ", style=" + getStyle() +
            ", user=" + getUser() +
            "}";
    }
}
