package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Agreement.
 */
@Entity
@Table(name = "agreement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Agreement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "agreement_no")
    private String agreementNo;

    @Column(name = "agreement_date")
    private LocalDate agreementDate;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnoreProperties(value = "agreements", allowSetters = true)
    private PartyRelationship partyRelationship;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgreementNo() {
        return agreementNo;
    }

    public Agreement agreementNo(String agreementNo) {
        this.agreementNo = agreementNo;
        return this;
    }

    public void setAgreementNo(String agreementNo) {
        this.agreementNo = agreementNo;
    }

    public LocalDate getAgreementDate() {
        return agreementDate;
    }

    public Agreement agreementDate(LocalDate agreementDate) {
        this.agreementDate = agreementDate;
        return this;
    }

    public void setAgreementDate(LocalDate agreementDate) {
        this.agreementDate = agreementDate;
    }

    public String getName() {
        return name;
    }

    public Agreement name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Agreement description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PartyRelationship getPartyRelationship() {
        return partyRelationship;
    }

    public Agreement partyRelationship(PartyRelationship partyRelationship) {
        this.partyRelationship = partyRelationship;
        return this;
    }

    public void setPartyRelationship(PartyRelationship partyRelationship) {
        this.partyRelationship = partyRelationship;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Agreement)) {
            return false;
        }
        return id != null && id.equals(((Agreement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Agreement{" +
            "id=" + getId() +
            ", agreementNo='" + getAgreementNo() + "'" +
            ", agreementDate='" + getAgreementDate() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
