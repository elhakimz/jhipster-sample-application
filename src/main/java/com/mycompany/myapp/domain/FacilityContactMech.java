package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A FacilityContactMech.
 */
@Entity
@Table(name = "facility_contact_mech")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FacilityContactMech implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = "facilityContactMeches", allowSetters = true)
    private ContactMech contactMechanism;

    @ManyToOne
    @JsonIgnoreProperties(value = "facilityContactMeches", allowSetters = true)
    private Facility facility;

    @ManyToOne
    @JsonIgnoreProperties(value = "facilityContactMeches", allowSetters = true)
    private CommEvent communicationEvent;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ContactMech getContactMechanism() {
        return contactMechanism;
    }

    public FacilityContactMech contactMechanism(ContactMech contactMech) {
        this.contactMechanism = contactMech;
        return this;
    }

    public void setContactMechanism(ContactMech contactMech) {
        this.contactMechanism = contactMech;
    }

    public Facility getFacility() {
        return facility;
    }

    public FacilityContactMech facility(Facility facility) {
        this.facility = facility;
        return this;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public CommEvent getCommunicationEvent() {
        return communicationEvent;
    }

    public FacilityContactMech communicationEvent(CommEvent commEvent) {
        this.communicationEvent = commEvent;
        return this;
    }

    public void setCommunicationEvent(CommEvent commEvent) {
        this.communicationEvent = commEvent;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FacilityContactMech)) {
            return false;
        }
        return id != null && id.equals(((FacilityContactMech) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FacilityContactMech{" +
            "id=" + getId() +
            "}";
    }
}
