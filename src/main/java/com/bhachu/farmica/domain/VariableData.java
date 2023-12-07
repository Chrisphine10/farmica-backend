package com.bhachu.farmica.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A VariableData.
 */
@Entity
@Table(name = "variable_data")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VariableData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "accumulation")
    private Integer accumulation;

    @Column(name = "ai_access_code")
    private String aiAccessCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public VariableData id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAccumulation() {
        return this.accumulation;
    }

    public VariableData accumulation(Integer accumulation) {
        this.setAccumulation(accumulation);
        return this;
    }

    public void setAccumulation(Integer accumulation) {
        this.accumulation = accumulation;
    }

    public String getAiAccessCode() {
        return this.aiAccessCode;
    }

    public VariableData aiAccessCode(String aiAccessCode) {
        this.setAiAccessCode(aiAccessCode);
        return this;
    }

    public void setAiAccessCode(String aiAccessCode) {
        this.aiAccessCode = aiAccessCode;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VariableData)) {
            return false;
        }
        return getId() != null && getId().equals(((VariableData) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VariableData{" +
            "id=" + getId() +
            ", accumulation=" + getAccumulation() +
            ", aiAccessCode='" + getAiAccessCode() + "'" +
            "}";
    }
}
