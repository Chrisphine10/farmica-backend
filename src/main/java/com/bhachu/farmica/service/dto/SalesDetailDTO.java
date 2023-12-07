package com.bhachu.farmica.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.bhachu.farmica.domain.SalesDetail} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SalesDetailDTO implements Serializable {

    private Long id;

    private String uicode;

    @NotNull
    private LocalDate salesDate;

    @NotNull
    private Integer numberOfCTNs;

    private Integer receivedCTNs;

    private Integer startCTNNumber;

    private Integer endCTNNumber;

    private ZonedDateTime createdAt;

    private WarehouseDetailDTO warehouseDetail;

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

    public LocalDate getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(LocalDate salesDate) {
        this.salesDate = salesDate;
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

    public WarehouseDetailDTO getWarehouseDetail() {
        return warehouseDetail;
    }

    public void setWarehouseDetail(WarehouseDetailDTO warehouseDetail) {
        this.warehouseDetail = warehouseDetail;
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
        if (!(o instanceof SalesDetailDTO)) {
            return false;
        }

        SalesDetailDTO salesDetailDTO = (SalesDetailDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, salesDetailDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalesDetailDTO{" +
            "id=" + getId() +
            ", uicode='" + getUicode() + "'" +
            ", salesDate='" + getSalesDate() + "'" +
            ", numberOfCTNs=" + getNumberOfCTNs() +
            ", receivedCTNs=" + getReceivedCTNs() +
            ", startCTNNumber=" + getStartCTNNumber() +
            ", endCTNNumber=" + getEndCTNNumber() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", warehouseDetail=" + getWarehouseDetail() +
            ", lotDetail=" + getLotDetail() +
            ", style=" + getStyle() +
            ", user=" + getUser() +
            "}";
    }
}
