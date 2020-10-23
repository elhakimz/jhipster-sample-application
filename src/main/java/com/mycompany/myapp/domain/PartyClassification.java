package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

import com.mycompany.myapp.domain.enumeration.PartyClassType;

/**
 * A PartyClassification.
 */
@Entity
@Table(name = "party_classification")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PartyClassification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "thru_date")
    private LocalDate thruDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "party_class_type")
    private PartyClassType partyClassType;

    @ManyToOne
    @JsonIgnoreProperties(value = "partyClassifications", allowSetters = true)
    private Party party;

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

    public PartyClassification fromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public PartyClassification thruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
        return this;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public PartyClassType getPartyClassType() {
        return partyClassType;
    }

    public PartyClassification partyClassType(PartyClassType partyClassType) {
        this.partyClassType = partyClassType;
        return this;
    }

    public void setPartyClassType(PartyClassType partyClassType) {
        this.partyClassType = partyClassType;
    }

    public Party getParty() {
        return party;
    }

    public PartyClassification party(Party party) {
        this.party = party;
        return this;
    }

    public void setParty(Party party) {
        this.party = party;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PartyClassification)) {
            return false;
        }
        return id != null && id.equals(((PartyClassification) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PartyClassification{" +
            "id=" + getId() +
            ", fromDate='" + getFromDate() + "'" +
            ", thruDate='" + getThruDate() + "'" +
            ", partyClassType='" + getPartyClassType() + "'" +
            "}";
    }
}
