package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.mycompany.myapp.domain.enumeration.WorkEffortType;

/**
 * A WorkEffort.
 */
@Entity
@Table(name = "work_effort")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WorkEffort implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "scheduled_start")
    private LocalDate scheduledStart;

    @Column(name = "scheduled_completion")
    private LocalDate scheduledCompletion;

    @Column(name = "total_money_allowed", precision = 21, scale = 2)
    private BigDecimal totalMoneyAllowed;

    @Column(name = "total_hours_allowed")
    private Double totalHoursAllowed;

    @Column(name = "estimated_hours")
    private Double estimatedHours;

    @Enumerated(EnumType.STRING)
    @Column(name = "work_effort_type")
    private WorkEffortType workEffortType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public WorkEffort code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public WorkEffort name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public WorkEffort description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getScheduledStart() {
        return scheduledStart;
    }

    public WorkEffort scheduledStart(LocalDate scheduledStart) {
        this.scheduledStart = scheduledStart;
        return this;
    }

    public void setScheduledStart(LocalDate scheduledStart) {
        this.scheduledStart = scheduledStart;
    }

    public LocalDate getScheduledCompletion() {
        return scheduledCompletion;
    }

    public WorkEffort scheduledCompletion(LocalDate scheduledCompletion) {
        this.scheduledCompletion = scheduledCompletion;
        return this;
    }

    public void setScheduledCompletion(LocalDate scheduledCompletion) {
        this.scheduledCompletion = scheduledCompletion;
    }

    public BigDecimal getTotalMoneyAllowed() {
        return totalMoneyAllowed;
    }

    public WorkEffort totalMoneyAllowed(BigDecimal totalMoneyAllowed) {
        this.totalMoneyAllowed = totalMoneyAllowed;
        return this;
    }

    public void setTotalMoneyAllowed(BigDecimal totalMoneyAllowed) {
        this.totalMoneyAllowed = totalMoneyAllowed;
    }

    public Double getTotalHoursAllowed() {
        return totalHoursAllowed;
    }

    public WorkEffort totalHoursAllowed(Double totalHoursAllowed) {
        this.totalHoursAllowed = totalHoursAllowed;
        return this;
    }

    public void setTotalHoursAllowed(Double totalHoursAllowed) {
        this.totalHoursAllowed = totalHoursAllowed;
    }

    public Double getEstimatedHours() {
        return estimatedHours;
    }

    public WorkEffort estimatedHours(Double estimatedHours) {
        this.estimatedHours = estimatedHours;
        return this;
    }

    public void setEstimatedHours(Double estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public WorkEffortType getWorkEffortType() {
        return workEffortType;
    }

    public WorkEffort workEffortType(WorkEffortType workEffortType) {
        this.workEffortType = workEffortType;
        return this;
    }

    public void setWorkEffortType(WorkEffortType workEffortType) {
        this.workEffortType = workEffortType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkEffort)) {
            return false;
        }
        return id != null && id.equals(((WorkEffort) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkEffort{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", scheduledStart='" + getScheduledStart() + "'" +
            ", scheduledCompletion='" + getScheduledCompletion() + "'" +
            ", totalMoneyAllowed=" + getTotalMoneyAllowed() +
            ", totalHoursAllowed=" + getTotalHoursAllowed() +
            ", estimatedHours=" + getEstimatedHours() +
            ", workEffortType='" + getWorkEffortType() + "'" +
            "}";
    }
}
