package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A CommEvent.
 */
@Entity
@Table(name = "comm_event")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CommEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_id")
    private String eventId;

    @Column(name = "started")
    private ZonedDateTime started;

    @Column(name = "ended")
    private ZonedDateTime ended;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JsonIgnoreProperties(value = "commEvents", allowSetters = true)
    private PartyRelationship contextOf;

    @ManyToOne
    @JsonIgnoreProperties(value = "commEvents", allowSetters = true)
    private ContactMechType occursVia;

    @ManyToOne
    @JsonIgnoreProperties(value = "commEvents", allowSetters = true)
    private Casus casus;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public CommEvent eventId(String eventId) {
        this.eventId = eventId;
        return this;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public ZonedDateTime getStarted() {
        return started;
    }

    public CommEvent started(ZonedDateTime started) {
        this.started = started;
        return this;
    }

    public void setStarted(ZonedDateTime started) {
        this.started = started;
    }

    public ZonedDateTime getEnded() {
        return ended;
    }

    public CommEvent ended(ZonedDateTime ended) {
        this.ended = ended;
        return this;
    }

    public void setEnded(ZonedDateTime ended) {
        this.ended = ended;
    }

    public String getNote() {
        return note;
    }

    public CommEvent note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public PartyRelationship getContextOf() {
        return contextOf;
    }

    public CommEvent contextOf(PartyRelationship partyRelationship) {
        this.contextOf = partyRelationship;
        return this;
    }

    public void setContextOf(PartyRelationship partyRelationship) {
        this.contextOf = partyRelationship;
    }

    public ContactMechType getOccursVia() {
        return occursVia;
    }

    public CommEvent occursVia(ContactMechType contactMechType) {
        this.occursVia = contactMechType;
        return this;
    }

    public void setOccursVia(ContactMechType contactMechType) {
        this.occursVia = contactMechType;
    }

    public Casus getCasus() {
        return casus;
    }

    public CommEvent casus(Casus casus) {
        this.casus = casus;
        return this;
    }

    public void setCasus(Casus casus) {
        this.casus = casus;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommEvent)) {
            return false;
        }
        return id != null && id.equals(((CommEvent) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommEvent{" +
            "id=" + getId() +
            ", eventId='" + getEventId() + "'" +
            ", started='" + getStarted() + "'" +
            ", ended='" + getEnded() + "'" +
            ", note='" + getNote() + "'" +
            "}";
    }
}
