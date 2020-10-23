package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import com.mycompany.myapp.domain.enumeration.FacilityType;

/**
 * A Facility.
 */
@Entity
@Table(name = "facility")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Facility implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @Column(name = "area")
    private Double area;

    @Enumerated(EnumType.STRING)
    @Column(name = "facility_type")
    private FacilityType facilityType;

    @ManyToOne
    @JsonIgnoreProperties(value = "facilities", allowSetters = true)
    private Facility parent;

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

    public Facility code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public Facility description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getArea() {
        return area;
    }

    public Facility area(Double area) {
        this.area = area;
        return this;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public FacilityType getFacilityType() {
        return facilityType;
    }

    public Facility facilityType(FacilityType facilityType) {
        this.facilityType = facilityType;
        return this;
    }

    public void setFacilityType(FacilityType facilityType) {
        this.facilityType = facilityType;
    }

    public Facility getParent() {
        return parent;
    }

    public Facility parent(Facility facility) {
        this.parent = facility;
        return this;
    }

    public void setParent(Facility facility) {
        this.parent = facility;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Facility)) {
            return false;
        }
        return id != null && id.equals(((Facility) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Facility{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", area=" + getArea() +
            ", facilityType='" + getFacilityType() + "'" +
            "}";
    }
}
