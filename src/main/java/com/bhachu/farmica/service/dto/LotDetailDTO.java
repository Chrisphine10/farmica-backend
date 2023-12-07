package com.bhachu.farmica.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bhachu.farmica.domain.LotDetail} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LotDetailDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer lotNo;

    private BatchDetailDTO batchDetail;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLotNo() {
        return lotNo;
    }

    public void setLotNo(Integer lotNo) {
        this.lotNo = lotNo;
    }

    public BatchDetailDTO getBatchDetail() {
        return batchDetail;
    }

    public void setBatchDetail(BatchDetailDTO batchDetail) {
        this.batchDetail = batchDetail;
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
        if (!(o instanceof LotDetailDTO)) {
            return false;
        }

        LotDetailDTO lotDetailDTO = (LotDetailDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, lotDetailDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LotDetailDTO{" +
            "id=" + getId() +
            ", lotNo=" + getLotNo() +
            ", batchDetail=" + getBatchDetail() +
            ", user=" + getUser() +
            "}";
    }
}
