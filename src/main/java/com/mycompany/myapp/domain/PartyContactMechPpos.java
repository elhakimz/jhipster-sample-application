package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A PartyContactMechPpos.
 */
@Entity
@Table(name = "party_contact_mech_ppos")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PartyContactMechPpos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "thru_date")
    private LocalDate thruDate;

    @ManyToOne
    @JsonIgnoreProperties(value = "partyContactMechPpos", allowSetters = true)
    private PartyContactMech partyContactMechanism;

    @ManyToOne
    @JsonIgnoreProperties(value = "partyContactMechPpos", allowSetters = true)
    private ContactMechPposType contactMechanismPurposeType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public PartyContactMechPpos fromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public PartyContactMechPpos thruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
        return this;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public PartyContactMech getPartyContactMechanism() {
        return partyContactMechanism;
    }

    public PartyContactMechPpos partyContactMechanism(PartyContactMech partyContactMech) {
        this.partyContactMechanism = partyContactMech;
        return this;
    }

    public void setPartyContactMechanism(PartyContactMech partyContactMech) {
        this.partyContactMechanism = partyContactMech;
    }

    public ContactMechPposType getContactMechanismPurposeType() {
        return contactMechanismPurposeType;
    }

    public PartyContactMechPpos contactMechanismPurposeType(ContactMechPposType contactMechPposType) {
        this.contactMechanismPurposeType = contactMechPposType;
        return this;
    }

    public void setContactMechanismPurposeType(ContactMechPposType contactMechPposType) {
        this.contactMechanismPurposeType = contactMechPposType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PartyContactMechPpos)) {
            return false;
        }
        return id != null && id.equals(((PartyContactMechPpos) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PartyContactMechPpos{" +
            "id=" + getId() +
            ", fromDate='" + getFromDate() + "'" +
            ", thruDate='" + getThruDate() + "'" +
            "}";
    }
}
