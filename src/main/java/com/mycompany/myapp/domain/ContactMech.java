package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ContactMech.
 */
@Entity
@Table(name = "contact_mech")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ContactMech implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "area_code")
    private String areaCode;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "address_1")
    private String address1;

    @Column(name = "address_2")
    private String address2;

    @Column(name = "directions")
    private String directions;

    @Column(name = "electronic_address")
    private String electronicAddress;

    @ManyToOne
    @JsonIgnoreProperties(value = "contactMeches", allowSetters = true)
    private ContactMechType contactMechanismType;

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

    public ContactMech code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public ContactMech areaCode(String areaCode) {
        this.areaCode = areaCode;
        return this;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public ContactMech countryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public ContactMech contactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
        return this;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress1() {
        return address1;
    }

    public ContactMech address1(String address1) {
        this.address1 = address1;
        return this;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public ContactMech address2(String address2) {
        this.address2 = address2;
        return this;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getDirections() {
        return directions;
    }

    public ContactMech directions(String directions) {
        this.directions = directions;
        return this;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public String getElectronicAddress() {
        return electronicAddress;
    }

    public ContactMech electronicAddress(String electronicAddress) {
        this.electronicAddress = electronicAddress;
        return this;
    }

    public void setElectronicAddress(String electronicAddress) {
        this.electronicAddress = electronicAddress;
    }

    public ContactMechType getContactMechanismType() {
        return contactMechanismType;
    }

    public ContactMech contactMechanismType(ContactMechType contactMechType) {
        this.contactMechanismType = contactMechType;
        return this;
    }

    public void setContactMechanismType(ContactMechType contactMechType) {
        this.contactMechanismType = contactMechType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContactMech)) {
            return false;
        }
        return id != null && id.equals(((ContactMech) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContactMech{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", areaCode='" + getAreaCode() + "'" +
            ", countryCode='" + getCountryCode() + "'" +
            ", contactNumber='" + getContactNumber() + "'" +
            ", address1='" + getAddress1() + "'" +
            ", address2='" + getAddress2() + "'" +
            ", directions='" + getDirections() + "'" +
            ", electronicAddress='" + getElectronicAddress() + "'" +
            "}";
    }
}
