package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A FacilityRole.
 */
@Entity
@Table(name = "facility_role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FacilityRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = "facilityRoles", allowSetters = true)
    private Party party;

    @ManyToOne
    @JsonIgnoreProperties(value = "facilityRoles", allowSetters = true)
    private FacilityRoleType facilityRoleType;

    @ManyToOne
    @JsonIgnoreProperties(value = "facilityRoles", allowSetters = true)
    private Facility facility;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Party getParty() {
        return party;
    }

    public FacilityRole party(Party party) {
        this.party = party;
        return this;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public FacilityRoleType getFacilityRoleType() {
        return facilityRoleType;
    }

    public FacilityRole facilityRoleType(FacilityRoleType facilityRoleType) {
        this.facilityRoleType = facilityRoleType;
        return this;
    }

    public void setFacilityRoleType(FacilityRoleType facilityRoleType) {
        this.facilityRoleType = facilityRoleType;
    }

    public Facility getFacility() {
        return facility;
    }

    public FacilityRole facility(Facility facility) {
        this.facility = facility;
        return this;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FacilityRole)) {
            return false;
        }
        return id != null && id.equals(((FacilityRole) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FacilityRole{" +
            "id=" + getId() +
            "}";
    }
}
