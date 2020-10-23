package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.mycompany.myapp.domain.enumeration.ContactType;

/**
 * A PartyContact.
 */
@Entity
@Table(name = "party_contact")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PartyContact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "contact_type", nullable = false)
    private ContactType contactType;

    @Column(name = "number")
    private String number;

    @Column(name = "jhi_primary")
    private Boolean primary;

    @ManyToOne
    @JsonIgnoreProperties(value = "partyContacts", allowSetters = true)
    private Party party;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ContactType getContactType() {
        return contactType;
    }

    public PartyContact contactType(ContactType contactType) {
        this.contactType = contactType;
        return this;
    }

    public void setContactType(ContactType contactType) {
        this.contactType = contactType;
    }

    public String getNumber() {
        return number;
    }

    public PartyContact number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Boolean isPrimary() {
        return primary;
    }

    public PartyContact primary(Boolean primary) {
        this.primary = primary;
        return this;
    }

    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }

    public Party getParty() {
        return party;
    }

    public PartyContact party(Party party) {
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
        if (!(o instanceof PartyContact)) {
            return false;
        }
        return id != null && id.equals(((PartyContact) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PartyContact{" +
            "id=" + getId() +
            ", contactType='" + getContactType() + "'" +
            ", number='" + getNumber() + "'" +
            ", primary='" + isPrimary() + "'" +
            "}";
    }
}
