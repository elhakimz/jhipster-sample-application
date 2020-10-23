package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

import com.mycompany.myapp.domain.enumeration.IdentificationType;

/**
 * A PartyIdentification.
 */
@Entity
@Table(name = "party_identification")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PartyIdentification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "identification_type", nullable = false)
    private IdentificationType identificationType;

    @Column(name = "ident_no")
    private String identNo;

    @Column(name = "valid_date")
    private LocalDate validDate;

    @ManyToOne
    @JsonIgnoreProperties(value = "partyIdentifications", allowSetters = true)
    private Party party;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IdentificationType getIdentificationType() {
        return identificationType;
    }

    public PartyIdentification identificationType(IdentificationType identificationType) {
        this.identificationType = identificationType;
        return this;
    }

    public void setIdentificationType(IdentificationType identificationType) {
        this.identificationType = identificationType;
    }

    public String getIdentNo() {
        return identNo;
    }

    public PartyIdentification identNo(String identNo) {
        this.identNo = identNo;
        return this;
    }

    public void setIdentNo(String identNo) {
        this.identNo = identNo;
    }

    public LocalDate getValidDate() {
        return validDate;
    }

    public PartyIdentification validDate(LocalDate validDate) {
        this.validDate = validDate;
        return this;
    }

    public void setValidDate(LocalDate validDate) {
        this.validDate = validDate;
    }

    public Party getParty() {
        return party;
    }

    public PartyIdentification party(Party party) {
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
        if (!(o instanceof PartyIdentification)) {
            return false;
        }
        return id != null && id.equals(((PartyIdentification) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PartyIdentification{" +
            "id=" + getId() +
            ", identificationType='" + getIdentificationType() + "'" +
            ", identNo='" + getIdentNo() + "'" +
            ", validDate='" + getValidDate() + "'" +
            "}";
    }
}
