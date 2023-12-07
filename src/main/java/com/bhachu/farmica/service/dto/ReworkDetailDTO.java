package com.bhachu.farmica.service.dto;

import com.bhachu.farmica.domain.enumeration.ReworkStatus;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.bhachu.farmica.domain.ReworkDetail} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReworkDetailDTO implements Serializable {

    private Long id;

    private String uicode;

    @NotNull
    private LocalDate pdnDate;

    @NotNull
    private LocalDate reworkDate;

    @NotNull
    private Integer numberOfCTNs;

    private Integer startCTNNumber;

    private Integer endCTNNumber;

    @NotNull
    private ReworkStatus status;

    private ZonedDateTime createdAt;

    private WarehouseDetailDTO warehouseDetail;

    private LotDetailDTO lotDetail;

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

    public LocalDate getReworkDate() {
        return reworkDate;
    }

    public void setReworkDate(LocalDate reworkDate) {
        this.reworkDate = reworkDate;
    }

    public Integer getNumberOfCTNs() {
        return numberOfCTNs;
    }

    public void setNumberOfCTNs(Integer numberOfCTNs) {
        this.numberOfCTNs = numberOfCTNs;
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

    public ReworkStatus getStatus() {
        return status;
    }

    public void setStatus(ReworkStatus status) {
        this.status = status;
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
        if (!(o instanceof ReworkDetailDTO)) {
            return false;
        }

        ReworkDetailDTO reworkDetailDTO = (ReworkDetailDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, reworkDetailDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReworkDetailDTO{" +
            "id=" + getId() +
            ", uicode='" + getUicode() + "'" +
            ", pdnDate='" + getPdnDate() + "'" +
            ", reworkDate='" + getReworkDate() + "'" +
            ", numberOfCTNs=" + getNumberOfCTNs() +
            ", startCTNNumber=" + getStartCTNNumber() +
            ", endCTNNumber=" + getEndCTNNumber() +
            ", status='" + getStatus() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", warehouseDetail=" + getWarehouseDetail() +
            ", lotDetail=" + getLotDetail() +
            ", user=" + getUser() +
            "}";
    }
}
