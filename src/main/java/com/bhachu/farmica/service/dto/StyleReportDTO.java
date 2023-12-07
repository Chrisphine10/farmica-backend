package com.bhachu.farmica.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.bhachu.farmica.domain.StyleReport} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StyleReportDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime createdAt;

    private Integer totalStyleInWarehouse;

    private Integer totalStyleInSales;

    private Integer totalStyleInRework;

    private Integer totalStyleInPacking;

    private Integer totalStyle;

    private StyleDTO style;

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

    public Integer getTotalStyleInWarehouse() {
        return totalStyleInWarehouse;
    }

    public void setTotalStyleInWarehouse(Integer totalStyleInWarehouse) {
        this.totalStyleInWarehouse = totalStyleInWarehouse;
    }

    public Integer getTotalStyleInSales() {
        return totalStyleInSales;
    }

    public void setTotalStyleInSales(Integer totalStyleInSales) {
        this.totalStyleInSales = totalStyleInSales;
    }

    public Integer getTotalStyleInRework() {
        return totalStyleInRework;
    }

    public void setTotalStyleInRework(Integer totalStyleInRework) {
        this.totalStyleInRework = totalStyleInRework;
    }

    public Integer getTotalStyleInPacking() {
        return totalStyleInPacking;
    }

    public void setTotalStyleInPacking(Integer totalStyleInPacking) {
        this.totalStyleInPacking = totalStyleInPacking;
    }

    public Integer getTotalStyle() {
        return totalStyle;
    }

    public void setTotalStyle(Integer totalStyle) {
        this.totalStyle = totalStyle;
    }

    public StyleDTO getStyle() {
        return style;
    }

    public void setStyle(StyleDTO style) {
        this.style = style;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StyleReportDTO)) {
            return false;
        }

        StyleReportDTO styleReportDTO = (StyleReportDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, styleReportDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StyleReportDTO{" +
            "id=" + getId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", totalStyleInWarehouse=" + getTotalStyleInWarehouse() +
            ", totalStyleInSales=" + getTotalStyleInSales() +
            ", totalStyleInRework=" + getTotalStyleInRework() +
            ", totalStyleInPacking=" + getTotalStyleInPacking() +
            ", totalStyle=" + getTotalStyle() +
            ", style=" + getStyle() +
            "}";
    }
}
