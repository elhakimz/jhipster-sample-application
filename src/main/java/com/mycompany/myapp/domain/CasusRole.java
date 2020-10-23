package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A CasusRole.
 */
@Entity
@Table(name = "casus_role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CasusRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = "casusRoles", allowSetters = true)
    private CasusRoleType roleType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CasusRoleType getRoleType() {
        return roleType;
    }

    public CasusRole roleType(CasusRoleType casusRoleType) {
        this.roleType = casusRoleType;
        return this;
    }

    public void setRoleType(CasusRoleType casusRoleType) {
        this.roleType = casusRoleType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CasusRole)) {
            return false;
        }
        return id != null && id.equals(((CasusRole) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CasusRole{" +
            "id=" + getId() +
            "}";
    }
}
