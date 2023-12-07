package com.bhachu.farmica.service.dto;

import com.bhachu.farmica.domain.enumeration.Grade;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bhachu.farmica.domain.Style} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StyleDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Grade grade;

    @NotNull
    private String code;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
        if (!(o instanceof StyleDTO)) {
            return false;
        }

        StyleDTO styleDTO = (StyleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, styleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StyleDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", grade='" + getGrade() + "'" +
            ", code='" + getCode() + "'" +
            ", user=" + getUser() +
            "}";
    }
}
