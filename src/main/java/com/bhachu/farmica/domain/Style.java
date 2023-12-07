package com.bhachu.farmica.domain;

import com.bhachu.farmica.domain.enumeration.Grade;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Style.
 */
@Entity
@Table(name = "style")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Style implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "grade", nullable = false)
    private Grade grade;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @ManyToOne(optional = false)
    @NotNull
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Style id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Style name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Grade getGrade() {
        return this.grade;
    }

    public Style grade(Grade grade) {
        this.setGrade(grade);
        return this;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public String getCode() {
        return this.code;
    }

    public Style code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Style user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Style)) {
            return false;
        }
        return getId() != null && getId().equals(((Style) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Style{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", grade='" + getGrade() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
