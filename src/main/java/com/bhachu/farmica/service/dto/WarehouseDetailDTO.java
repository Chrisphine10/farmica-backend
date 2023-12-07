package com.bhachu.farmica.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.bhachu.farmica.domain.WarehouseDetail} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WarehouseDetailDTO implements Serializable {

    private Long id;

    private String uicode;

    @NotNull
    private LocalDate warehouseDate;

    private Integer numberOfCTNs;

    private Integer receivedCTNs;

    private Integer startCTNNumber;

    private Integer endCTNNumber;

    private ZonedDateTime createdAt;

    private PackingZoneDetailDTO packingZoneDetail;

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

    public LocalDate getWarehouseDate() {
        return warehouseDate;
    }

    public void setWarehouseDate(LocalDate warehouseDate) {
        this.warehouseDate = warehouseDate;
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

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public PackingZoneDetailDTO getPackingZoneDetail() {
        return packingZoneDetail;
    }

    public void setPackingZoneDetail(PackingZoneDetailDTO packingZoneDetail) {
        this.packingZoneDetail = packingZoneDetail;
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
        if (!(o instanceof WarehouseDetailDTO)) {
            return false;
        }

        WarehouseDetailDTO warehouseDetailDTO = (WarehouseDetailDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, warehouseDetailDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WarehouseDetailDTO{" +
            "id=" + getId() +
            ", uicode='" + getUicode() + "'" +
            ", warehouseDate='" + getWarehouseDate() + "'" +
            ", numberOfCTNs=" + getNumberOfCTNs() +
            ", receivedCTNs=" + getReceivedCTNs() +
            ", startCTNNumber=" + getStartCTNNumber() +
            ", endCTNNumber=" + getEndCTNNumber() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", packingZoneDetail=" + getPackingZoneDetail() +
            ", lotDetail=" + getLotDetail() +
            ", style=" + getStyle() +
            ", user=" + getUser() +
            "}";
    }
}
