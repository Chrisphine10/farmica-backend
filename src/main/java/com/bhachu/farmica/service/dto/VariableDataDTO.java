package com.bhachu.farmica.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bhachu.farmica.domain.VariableData} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VariableDataDTO implements Serializable {

    private Long id;

    private Integer accumulation;

    private String aiAccessCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAccumulation() {
        return accumulation;
    }

    public void setAccumulation(Integer accumulation) {
        this.accumulation = accumulation;
    }

    public String getAiAccessCode() {
        return aiAccessCode;
    }

    public void setAiAccessCode(String aiAccessCode) {
        this.aiAccessCode = aiAccessCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VariableDataDTO)) {
            return false;
        }

        VariableDataDTO variableDataDTO = (VariableDataDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, variableDataDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VariableDataDTO{" +
            "id=" + getId() +
            ", accumulation=" + getAccumulation() +
            ", aiAccessCode='" + getAiAccessCode() + "'" +
            "}";
    }
}
