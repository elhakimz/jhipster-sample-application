package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.ContactMech;
import com.mycompany.myapp.repository.ContactMechRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ContactMechResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ContactMechResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_AREA_CODE = "AAAAAAAAAA";
    private static final String UPDATED_AREA_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_2 = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECTIONS = "AAAAAAAAAA";
    private static final String UPDATED_DIRECTIONS = "BBBBBBBBBB";

    private static final String DEFAULT_ELECTRONIC_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ELECTRONIC_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private ContactMechRepository contactMechRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContactMechMockMvc;

    private ContactMech contactMech;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactMech createEntity(EntityManager em) {
        ContactMech contactMech = new ContactMech()
            .code(DEFAULT_CODE)
            .areaCode(DEFAULT_AREA_CODE)
            .countryCode(DEFAULT_COUNTRY_CODE)
            .contactNumber(DEFAULT_CONTACT_NUMBER)
            .address1(DEFAULT_ADDRESS_1)
            .address2(DEFAULT_ADDRESS_2)
            .directions(DEFAULT_DIRECTIONS)
            .electronicAddress(DEFAULT_ELECTRONIC_ADDRESS);
        return contactMech;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactMech createUpdatedEntity(EntityManager em) {
        ContactMech contactMech = new ContactMech()
            .code(UPDATED_CODE)
            .areaCode(UPDATED_AREA_CODE)
            .countryCode(UPDATED_COUNTRY_CODE)
            .contactNumber(UPDATED_CONTACT_NUMBER)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .directions(UPDATED_DIRECTIONS)
            .electronicAddress(UPDATED_ELECTRONIC_ADDRESS);
        return contactMech;
    }

    @BeforeEach
    public void initTest() {
        contactMech = createEntity(em);
    }

    @Test
    @Transactional
    public void createContactMech() throws Exception {
        int databaseSizeBeforeCreate = contactMechRepository.findAll().size();
        // Create the ContactMech
        restContactMechMockMvc.perform(post("/api/contact-meches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactMech)))
            .andExpect(status().isCreated());

        // Validate the ContactMech in the database
        List<ContactMech> contactMechList = contactMechRepository.findAll();
        assertThat(contactMechList).hasSize(databaseSizeBeforeCreate + 1);
        ContactMech testContactMech = contactMechList.get(contactMechList.size() - 1);
        assertThat(testContactMech.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testContactMech.getAreaCode()).isEqualTo(DEFAULT_AREA_CODE);
        assertThat(testContactMech.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testContactMech.getContactNumber()).isEqualTo(DEFAULT_CONTACT_NUMBER);
        assertThat(testContactMech.getAddress1()).isEqualTo(DEFAULT_ADDRESS_1);
        assertThat(testContactMech.getAddress2()).isEqualTo(DEFAULT_ADDRESS_2);
        assertThat(testContactMech.getDirections()).isEqualTo(DEFAULT_DIRECTIONS);
        assertThat(testContactMech.getElectronicAddress()).isEqualTo(DEFAULT_ELECTRONIC_ADDRESS);
    }

    @Test
    @Transactional
    public void createContactMechWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contactMechRepository.findAll().size();

        // Create the ContactMech with an existing ID
        contactMech.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactMechMockMvc.perform(post("/api/contact-meches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactMech)))
            .andExpect(status().isBadRequest());

        // Validate the ContactMech in the database
        List<ContactMech> contactMechList = contactMechRepository.findAll();
        assertThat(contactMechList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllContactMeches() throws Exception {
        // Initialize the database
        contactMechRepository.saveAndFlush(contactMech);

        // Get all the contactMechList
        restContactMechMockMvc.perform(get("/api/contact-meches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactMech.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].areaCode").value(hasItem(DEFAULT_AREA_CODE)))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE)))
            .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER)))
            .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS_1)))
            .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS_2)))
            .andExpect(jsonPath("$.[*].directions").value(hasItem(DEFAULT_DIRECTIONS)))
            .andExpect(jsonPath("$.[*].electronicAddress").value(hasItem(DEFAULT_ELECTRONIC_ADDRESS)));
    }
    
    @Test
    @Transactional
    public void getContactMech() throws Exception {
        // Initialize the database
        contactMechRepository.saveAndFlush(contactMech);

        // Get the contactMech
        restContactMechMockMvc.perform(get("/api/contact-meches/{id}", contactMech.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contactMech.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.areaCode").value(DEFAULT_AREA_CODE))
            .andExpect(jsonPath("$.countryCode").value(DEFAULT_COUNTRY_CODE))
            .andExpect(jsonPath("$.contactNumber").value(DEFAULT_CONTACT_NUMBER))
            .andExpect(jsonPath("$.address1").value(DEFAULT_ADDRESS_1))
            .andExpect(jsonPath("$.address2").value(DEFAULT_ADDRESS_2))
            .andExpect(jsonPath("$.directions").value(DEFAULT_DIRECTIONS))
            .andExpect(jsonPath("$.electronicAddress").value(DEFAULT_ELECTRONIC_ADDRESS));
    }
    @Test
    @Transactional
    public void getNonExistingContactMech() throws Exception {
        // Get the contactMech
        restContactMechMockMvc.perform(get("/api/contact-meches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContactMech() throws Exception {
        // Initialize the database
        contactMechRepository.saveAndFlush(contactMech);

        int databaseSizeBeforeUpdate = contactMechRepository.findAll().size();

        // Update the contactMech
        ContactMech updatedContactMech = contactMechRepository.findById(contactMech.getId()).get();
        // Disconnect from session so that the updates on updatedContactMech are not directly saved in db
        em.detach(updatedContactMech);
        updatedContactMech
            .code(UPDATED_CODE)
            .areaCode(UPDATED_AREA_CODE)
            .countryCode(UPDATED_COUNTRY_CODE)
            .contactNumber(UPDATED_CONTACT_NUMBER)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .directions(UPDATED_DIRECTIONS)
            .electronicAddress(UPDATED_ELECTRONIC_ADDRESS);

        restContactMechMockMvc.perform(put("/api/contact-meches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedContactMech)))
            .andExpect(status().isOk());

        // Validate the ContactMech in the database
        List<ContactMech> contactMechList = contactMechRepository.findAll();
        assertThat(contactMechList).hasSize(databaseSizeBeforeUpdate);
        ContactMech testContactMech = contactMechList.get(contactMechList.size() - 1);
        assertThat(testContactMech.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testContactMech.getAreaCode()).isEqualTo(UPDATED_AREA_CODE);
        assertThat(testContactMech.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testContactMech.getContactNumber()).isEqualTo(UPDATED_CONTACT_NUMBER);
        assertThat(testContactMech.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testContactMech.getAddress2()).isEqualTo(UPDATED_ADDRESS_2);
        assertThat(testContactMech.getDirections()).isEqualTo(UPDATED_DIRECTIONS);
        assertThat(testContactMech.getElectronicAddress()).isEqualTo(UPDATED_ELECTRONIC_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingContactMech() throws Exception {
        int databaseSizeBeforeUpdate = contactMechRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactMechMockMvc.perform(put("/api/contact-meches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactMech)))
            .andExpect(status().isBadRequest());

        // Validate the ContactMech in the database
        List<ContactMech> contactMechList = contactMechRepository.findAll();
        assertThat(contactMechList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContactMech() throws Exception {
        // Initialize the database
        contactMechRepository.saveAndFlush(contactMech);

        int databaseSizeBeforeDelete = contactMechRepository.findAll().size();

        // Delete the contactMech
        restContactMechMockMvc.perform(delete("/api/contact-meches/{id}", contactMech.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContactMech> contactMechList = contactMechRepository.findAll();
        assertThat(contactMechList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
