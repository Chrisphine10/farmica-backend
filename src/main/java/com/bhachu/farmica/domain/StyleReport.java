package com.bhachu.farmica.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A StyleReport.
 */
@Entity
@Table(name = "style_report")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StyleReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @Column(name = "total_style_in_warehouse")
    private Integer totalStyleInWarehouse;

    @Column(name = "total_style_in_sales")
    private Integer totalStyleInSales;

    @Column(name = "total_style_in_rework")
    private Integer totalStyleInRework;

    @Column(name = "total_style_in_packing")
    private Integer totalStyleInPacking;

    @Column(name = "total_style")
    private Integer totalStyle;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "user" }, allowSetters = true)
    private Style style;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public StyleReport id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public StyleReport createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getTotalStyleInWarehouse() {
        return this.totalStyleInWarehouse;
    }

    public StyleReport totalStyleInWarehouse(Integer totalStyleInWarehouse) {
        this.setTotalStyleInWarehouse(totalStyleInWarehouse);
        return this;
    }

    public void setTotalStyleInWarehouse(Integer totalStyleInWarehouse) {
        this.totalStyleInWarehouse = totalStyleInWarehouse;
    }

    public Integer getTotalStyleInSales() {
        return this.totalStyleInSales;
    }

    public StyleReport totalStyleInSales(Integer totalStyleInSales) {
        this.setTotalStyleInSales(totalStyleInSales);
        return this;
    }

    public void setTotalStyleInSales(Integer totalStyleInSales) {
        this.totalStyleInSales = totalStyleInSales;
    }

    public Integer getTotalStyleInRework() {
        return this.totalStyleInRework;
    }

    public StyleReport totalStyleInRework(Integer totalStyleInRework) {
        this.setTotalStyleInRework(totalStyleInRework);
        return this;
    }

    public void setTotalStyleInRework(Integer totalStyleInRework) {
        this.totalStyleInRework = totalStyleInRework;
    }

    public Integer getTotalStyleInPacking() {
        return this.totalStyleInPacking;
    }

    public StyleReport totalStyleInPacking(Integer totalStyleInPacking) {
        this.setTotalStyleInPacking(totalStyleInPacking);
        return this;
    }

    public void setTotalStyleInPacking(Integer totalStyleInPacking) {
        this.totalStyleInPacking = totalStyleInPacking;
    }

    public Integer getTotalStyle() {
        return this.totalStyle;
    }

    public StyleReport totalStyle(Integer totalStyle) {
        this.setTotalStyle(totalStyle);
        return this;
    }

    public void setTotalStyle(Integer totalStyle) {
        this.totalStyle = totalStyle;
    }

    public Style getStyle() {
        return this.style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public StyleReport style(Style style) {
        this.setStyle(style);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StyleReport)) {
            return false;
        }
        return getId() != null && getId().equals(((StyleReport) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StyleReport{" +
            "id=" + getId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", totalStyleInWarehouse=" + getTotalStyleInWarehouse() +
            ", totalStyleInSales=" + getTotalStyleInSales() +
            ", totalStyleInRework=" + getTotalStyleInRework() +
            ", totalStyleInPacking=" + getTotalStyleInPacking() +
            ", totalStyle=" + getTotalStyle() +
            "}";
    }
}
