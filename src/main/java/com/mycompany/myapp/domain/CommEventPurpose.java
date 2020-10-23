package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A CommEventPurpose.
 */
@Entity
@Table(name = "comm_event_purpose")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CommEventPurpose implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnoreProperties(value = "commEventPurposes", allowSetters = true)
    private CommEvent communicationEvent;

    @ManyToOne
    @JsonIgnoreProperties(value = "commEventPurposes", allowSetters = true)
    private CommEvtPposType purposeType;

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

    public CommEventPurpose description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CommEvent getCommunicationEvent() {
        return communicationEvent;
    }

    public CommEventPurpose communicationEvent(CommEvent commEvent) {
        this.communicationEvent = commEvent;
        return this;
    }

    public void setCommunicationEvent(CommEvent commEvent) {
        this.communicationEvent = commEvent;
    }

    public CommEvtPposType getPurposeType() {
        return purposeType;
    }

    public CommEventPurpose purposeType(CommEvtPposType commEvtPposType) {
        this.purposeType = commEvtPposType;
        return this;
    }

    public void setPurposeType(CommEvtPposType commEvtPposType) {
        this.purposeType = commEvtPposType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommEventPurpose)) {
            return false;
        }
        return id != null && id.equals(((CommEventPurpose) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommEventPurpose{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
