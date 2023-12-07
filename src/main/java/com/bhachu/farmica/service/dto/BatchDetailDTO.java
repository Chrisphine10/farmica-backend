package com.bhachu.farmica.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.bhachu.farmica.domain.BatchDetail} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BatchDetailDTO implements Serializable {

    private Long id;

    @NotNull
    private String batchNo;

    @NotNull
    private ZonedDateTime createdAt;

    private Integer drier;

    private RegionDTO region;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getDrier() {
        return drier;
    }

    public void setDrier(Integer drier) {
        this.drier = drier;
    }

    public RegionDTO getRegion() {
        return region;
    }

    public void setRegion(RegionDTO region) {
        this.region = region;
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
        if (!(o instanceof BatchDetailDTO)) {
            return false;
        }

        BatchDetailDTO batchDetailDTO = (BatchDetailDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, batchDetailDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BatchDetailDTO{" +
            "id=" + getId() +
            ", batchNo='" + getBatchNo() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", drier=" + getDrier() +
            ", region=" + getRegion() +
            ", user=" + getUser() +
            "}";
    }
}
