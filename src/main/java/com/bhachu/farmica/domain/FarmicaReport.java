package com.bhachu.farmica.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A FarmicaReport.
 */
@Entity
@Table(name = "farmica_report")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FarmicaReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @NotNull
    @Column(name = "total_items_in_warehouse", nullable = false)
    private Integer totalItemsInWarehouse;

    @NotNull
    @Column(name = "total_items_in_sales", nullable = false)
    private Integer totalItemsInSales;

    @NotNull
    @Column(name = "total_items_in_rework", nullable = false)
    private Integer totalItemsInRework;

    @NotNull
    @Column(name = "total_items_in_packing", nullable = false)
    private Integer totalItemsInPacking;

    @NotNull
    @Column(name = "total_items", nullable = false)
    private Integer totalItems;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FarmicaReport id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public FarmicaReport createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getTotalItemsInWarehouse() {
        return this.totalItemsInWarehouse;
    }

    public FarmicaReport totalItemsInWarehouse(Integer totalItemsInWarehouse) {
        this.setTotalItemsInWarehouse(totalItemsInWarehouse);
        return this;
    }

    public void setTotalItemsInWarehouse(Integer totalItemsInWarehouse) {
        this.totalItemsInWarehouse = totalItemsInWarehouse;
    }

    public Integer getTotalItemsInSales() {
        return this.totalItemsInSales;
    }

    public FarmicaReport totalItemsInSales(Integer totalItemsInSales) {
        this.setTotalItemsInSales(totalItemsInSales);
        return this;
    }

    public void setTotalItemsInSales(Integer totalItemsInSales) {
        this.totalItemsInSales = totalItemsInSales;
    }

    public Integer getTotalItemsInRework() {
        return this.totalItemsInRework;
    }

    public FarmicaReport totalItemsInRework(Integer totalItemsInRework) {
        this.setTotalItemsInRework(totalItemsInRework);
        return this;
    }

    public void setTotalItemsInRework(Integer totalItemsInRework) {
        this.totalItemsInRework = totalItemsInRework;
    }

    public Integer getTotalItemsInPacking() {
        return this.totalItemsInPacking;
    }

    public FarmicaReport totalItemsInPacking(Integer totalItemsInPacking) {
        this.setTotalItemsInPacking(totalItemsInPacking);
        return this;
    }

    public void setTotalItemsInPacking(Integer totalItemsInPacking) {
        this.totalItemsInPacking = totalItemsInPacking;
    }

    public Integer getTotalItems() {
        return this.totalItems;
    }

    public FarmicaReport totalItems(Integer totalItems) {
        this.setTotalItems(totalItems);
        return this;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FarmicaReport)) {
            return false;
        }
        return getId() != null && getId().equals(((FarmicaReport) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FarmicaReport{" +
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
