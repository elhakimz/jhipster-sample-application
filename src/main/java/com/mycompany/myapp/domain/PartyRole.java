package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A PartyRole.
 */
@Entity
@Table(name = "party_role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PartyRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JsonIgnoreProperties(value = "partyRoles", allowSetters = true)
    private Party party;

    @ManyToOne
    @JsonIgnoreProperties(value = "partyRoles", allowSetters = true)
    private RoleType roleType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public PartyRole name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Party getParty() {
        return party;
    }

    public PartyRole party(Party party) {
        this.party = party;
        return this;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public PartyRole roleType(RoleType roleType) {
        this.roleType = roleType;
        return this;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PartyRole)) {
            return false;
        }
        return id != null && id.equals(((PartyRole) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PartyRole{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
