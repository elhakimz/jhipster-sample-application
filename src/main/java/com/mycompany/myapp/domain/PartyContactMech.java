package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A PartyContactMech.
 */
@Entity
@Table(name = "party_contact_mech")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PartyContactMech implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "thru_date")
    private LocalDate thruDate;

    @Column(name = "non_solicitation")
    private Boolean nonSolicitation;

    @ManyToOne
    @JsonIgnoreProperties(value = "partyContactMeches", allowSetters = true)
    private Party party;

    @ManyToOne
    @JsonIgnoreProperties(value = "partyContactMeches", allowSetters = true)
    private ContactMech contactMechanism;

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

    public PartyContactMech fromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public PartyContactMech thruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
        return this;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public Boolean isNonSolicitation() {
        return nonSolicitation;
    }

    public PartyContactMech nonSolicitation(Boolean nonSolicitation) {
        this.nonSolicitation = nonSolicitation;
        return this;
    }

    public void setNonSolicitation(Boolean nonSolicitation) {
        this.nonSolicitation = nonSolicitation;
    }

    public Party getParty() {
        return party;
    }

    public PartyContactMech party(Party party) {
        this.party = party;
        return this;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public ContactMech getContactMechanism() {
        return contactMechanism;
    }

    public PartyContactMech contactMechanism(ContactMech contactMech) {
        this.contactMechanism = contactMech;
        return this;
    }

    public void setContactMechanism(ContactMech contactMech) {
        this.contactMechanism = contactMech;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PartyContactMech)) {
            return false;
        }
        return id != null && id.equals(((PartyContactMech) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PartyContactMech{" +
            "id=" + getId() +
            ", fromDate='" + getFromDate() + "'" +
            ", thruDate='" + getThruDate() + "'" +
            ", nonSolicitation='" + isNonSolicitation() + "'" +
            "}";
    }
}
