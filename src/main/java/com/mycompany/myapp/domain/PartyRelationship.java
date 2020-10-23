package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

import com.mycompany.myapp.domain.enumeration.PartyRelType;

import com.mycompany.myapp.domain.enumeration.PriorityType;

/**
 * A PartyRelationship.
 */
@Entity
@Table(name = "party_relationship")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PartyRelationship implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "party_relationship_type", nullable = false)
    private PartyRelType partyRelationshipType;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "thru_date")
    private LocalDate thruDate;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority_type")
    private PriorityType priorityType;

    @ManyToOne
    @JsonIgnoreProperties(value = "partyRelationships", allowSetters = true)
    private PartyRole fromPartyRole;

    @ManyToOne
    @JsonIgnoreProperties(value = "partyRelationships", allowSetters = true)
    private PartyRole toPartyRole;

    @ManyToOne
    @JsonIgnoreProperties(value = "partyRelationships", allowSetters = true)
    private StatusType statusType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PartyRelType getPartyRelationshipType() {
        return partyRelationshipType;
    }

    public PartyRelationship partyRelationshipType(PartyRelType partyRelationshipType) {
        this.partyRelationshipType = partyRelationshipType;
        return this;
    }

    public void setPartyRelationshipType(PartyRelType partyRelationshipType) {
        this.partyRelationshipType = partyRelationshipType;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public PartyRelationship fromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public PartyRelationship thruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
        return this;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public String getName() {
        return name;
    }

    public PartyRelationship name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public PartyRelationship description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PriorityType getPriorityType() {
        return priorityType;
    }

    public PartyRelationship priorityType(PriorityType priorityType) {
        this.priorityType = priorityType;
        return this;
    }

    public void setPriorityType(PriorityType priorityType) {
        this.priorityType = priorityType;
    }

    public PartyRole getFromPartyRole() {
        return fromPartyRole;
    }

    public PartyRelationship fromPartyRole(PartyRole partyRole) {
        this.fromPartyRole = partyRole;
        return this;
    }

    public void setFromPartyRole(PartyRole partyRole) {
        this.fromPartyRole = partyRole;
    }

    public PartyRole getToPartyRole() {
        return toPartyRole;
    }

    public PartyRelationship toPartyRole(PartyRole partyRole) {
        this.toPartyRole = partyRole;
        return this;
    }

    public void setToPartyRole(PartyRole partyRole) {
        this.toPartyRole = partyRole;
    }

    public StatusType getStatusType() {
        return statusType;
    }

    public PartyRelationship statusType(StatusType statusType) {
        this.statusType = statusType;
        return this;
    }

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PartyRelationship)) {
            return false;
        }
        return id != null && id.equals(((PartyRelationship) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PartyRelationship{" +
            "id=" + getId() +
            ", partyRelationshipType='" + getPartyRelationshipType() + "'" +
            ", fromDate='" + getFromDate() + "'" +
            ", thruDate='" + getThruDate() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", priorityType='" + getPriorityType() + "'" +
            "}";
    }
}
