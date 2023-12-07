package com.bhachu.farmica.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.bhachu.farmica.domain.FarmicaReport} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FarmicaReportDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime createdAt;

    @NotNull
    private Integer totalItemsInWarehouse;

    @NotNull
    private Integer totalItemsInSales;

    @NotNull
    private Integer totalItemsInRework;

    @NotNull
    private Integer totalItemsInPacking;

    @NotNull
    private Integer totalItems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getTotalItemsInWarehouse() {
        return totalItemsInWarehouse;
    }

    public void setTotalItemsInWarehouse(Integer totalItemsInWarehouse) {
        this.totalItemsInWarehouse = totalItemsInWarehouse;
    }

    public Integer getTotalItemsInSales() {
        return totalItemsInSales;
    }

    public void setTotalItemsInSales(Integer totalItemsInSales) {
        this.totalItemsInSales = totalItemsInSales;
    }

    public Integer getTotalItemsInRework() {
        return totalItemsInRework;
    }

    public void setTotalItemsInRework(Integer totalItemsInRework) {
        this.totalItemsInRework = totalItemsInRework;
    }

    public Integer getTotalItemsInPacking() {
        return totalItemsInPacking;
    }

    public void setTotalItemsInPacking(Integer totalItemsInPacking) {
        this.totalItemsInPacking = totalItemsInPacking;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FarmicaReportDTO)) {
            return false;
        }

        FarmicaReportDTO farmicaReportDTO = (FarmicaReportDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, farmicaReportDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FarmicaReportDTO{" +
            "id=" + getId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", totalItemsInWarehouse=" + getTotalItemsInWarehouse() +
            ", totalItemsInSales=" + getTotalItemsInSales() +
            ", totalItemsInRework=" + getTotalItemsInRework() +
            ", totalItemsInPacking=" + getTotalItemsInPacking() +
            ", totalItems=" + getTotalItems() +
            "}";
    }
}
