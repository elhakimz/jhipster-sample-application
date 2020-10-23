package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.ContactMechType;
import com.mycompany.myapp.repository.ContactMechTypeRepository;

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
 * Integration tests for the {@link ContactMechTypeResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ContactMechTypeResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ContactMechTypeRepository contactMechTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContactMechTypeMockMvc;

    private ContactMechType contactMechType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactMechType createEntity(EntityManager em) {
        ContactMechType contactMechType = new ContactMechType()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME);
        return contactMechType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactMechType createUpdatedEntity(EntityManager em) {
        ContactMechType contactMechType = new ContactMechType()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME);
        return contactMechType;
    }

    @BeforeEach
    public void initTest() {
        contactMechType = createEntity(em);
    }

    @Test
    @Transactional
    public void createContactMechType() throws Exception {
        int databaseSizeBeforeCreate = contactMechTypeRepository.findAll().size();
        // Create the ContactMechType
        restContactMechTypeMockMvc.perform(post("/api/contact-mech-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactMechType)))
            .andExpect(status().isCreated());

        // Validate the ContactMechType in the database
        List<ContactMechType> contactMechTypeList = contactMechTypeRepository.findAll();
        assertThat(contactMechTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ContactMechType testContactMechType = contactMechTypeList.get(contactMechTypeList.size() - 1);
        assertThat(testContactMechType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testContactMechType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createContactMechTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contactMechTypeRepository.findAll().size();

        // Create the ContactMechType with an existing ID
        contactMechType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactMechTypeMockMvc.perform(post("/api/contact-mech-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactMechType)))
            .andExpect(status().isBadRequest());

        // Validate the ContactMechType in the database
        List<ContactMechType> contactMechTypeList = contactMechTypeRepository.findAll();
        assertThat(contactMechTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllContactMechTypes() throws Exception {
        // Initialize the database
        contactMechTypeRepository.saveAndFlush(contactMechType);

        // Get all the contactMechTypeList
        restContactMechTypeMockMvc.perform(get("/api/contact-mech-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactMechType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getContactMechType() throws Exception {
        // Initialize the database
        contactMechTypeRepository.saveAndFlush(contactMechType);

        // Get the contactMechType
        restContactMechTypeMockMvc.perform(get("/api/contact-mech-types/{id}", contactMechType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contactMechType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingContactMechType() throws Exception {
        // Get the contactMechType
        restContactMechTypeMockMvc.perform(get("/api/contact-mech-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContactMechType() throws Exception {
        // Initialize the database
        contactMechTypeRepository.saveAndFlush(contactMechType);

        int databaseSizeBeforeUpdate = contactMechTypeRepository.findAll().size();

        // Update the contactMechType
        ContactMechType updatedContactMechType = contactMechTypeRepository.findById(contactMechType.getId()).get();
        // Disconnect from session so that the updates on updatedContactMechType are not directly saved in db
        em.detach(updatedContactMechType);
        updatedContactMechType
            .code(UPDATED_CODE)
            .name(UPDATED_NAME);

        restContactMechTypeMockMvc.perform(put("/api/contact-mech-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedContactMechType)))
            .andExpect(status().isOk());

        // Validate the ContactMechType in the database
        List<ContactMechType> contactMechTypeList = contactMechTypeRepository.findAll();
        assertThat(contactMechTypeList).hasSize(databaseSizeBeforeUpdate);
        ContactMechType testContactMechType = contactMechTypeList.get(contactMechTypeList.size() - 1);
        assertThat(testContactMechType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testContactMechType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingContactMechType() throws Exception {
        int databaseSizeBeforeUpdate = contactMechTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactMechTypeMockMvc.perform(put("/api/contact-mech-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactMechType)))
            .andExpect(status().isBadRequest());

        // Validate the ContactMechType in the database
        List<ContactMechType> contactMechTypeList = contactMechTypeRepository.findAll();
        assertThat(contactMechTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContactMechType() throws Exception {
        // Initialize the database
        contactMechTypeRepository.saveAndFlush(contactMechType);

        int databaseSizeBeforeDelete = contactMechTypeRepository.findAll().size();

        // Delete the contactMechType
        restContactMechTypeMockMvc.perform(delete("/api/contact-mech-types/{id}", contactMechType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContactMechType> contactMechTypeList = contactMechTypeRepository.findAll();
        assertThat(contactMechTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
