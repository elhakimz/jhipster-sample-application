package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A CommWorkEffort.
 */
@Entity
@Table(name = "comm_work_effort")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CommWorkEffort implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnoreProperties(value = "commWorkEfforts", allowSetters = true)
    private WorkEffort workEffort;

    @ManyToOne
    @JsonIgnoreProperties(value = "commWorkEfforts", allowSetters = true)
    private CommEvent communicationEvent;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public CommWorkEffort description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public WorkEffort getWorkEffort() {
        return workEffort;
    }

    public CommWorkEffort workEffort(WorkEffort workEffort) {
        this.workEffort = workEffort;
        return this;
    }

    public void setWorkEffort(WorkEffort workEffort) {
        this.workEffort = workEffort;
    }

    public CommEvent getCommunicationEvent() {
        return communicationEvent;
    }

    public CommWorkEffort communicationEvent(CommEvent commEvent) {
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
        if (!(o instanceof CommWorkEffort)) {
            return false;
        }
        return id != null && id.equals(((CommWorkEffort) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommWorkEffort{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
