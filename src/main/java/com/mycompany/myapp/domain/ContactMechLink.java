package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ContactMechLink.
 */
@Entity
@Table(name = "contact_mech_link")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ContactMechLink implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = "contactMechLinks", allowSetters = true)
    private ContactMech toContactMechanism;

    @ManyToOne
    @JsonIgnoreProperties(value = "contactMechLinks", allowSetters = true)
    private ContactMech fromContactMechanism;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ContactMech getToContactMechanism() {
        return toContactMechanism;
    }

    public ContactMechLink toContactMechanism(ContactMech contactMech) {
        this.toContactMechanism = contactMech;
        return this;
    }

    public void setToContactMechanism(ContactMech contactMech) {
        this.toContactMechanism = contactMech;
    }

    public ContactMech getFromContactMechanism() {
        return fromContactMechanism;
    }

    public ContactMechLink fromContactMechanism(ContactMech contactMech) {
        this.fromContactMechanism = contactMech;
        return this;
    }

    public void setFromContactMechanism(ContactMech contactMech) {
        this.fromContactMechanism = contactMech;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContactMechLink)) {
            return false;
        }
        return id != null && id.equals(((ContactMechLink) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContactMechLink{" +
            "id=" + getId() +
            "}";
    }
}
