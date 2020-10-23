package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.ContactMechPposType;
import com.mycompany.myapp.repository.ContactMechPposTypeRepository;

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
 * Integration tests for the {@link ContactMechPposTypeResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ContactMechPposTypeResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ContactMechPposTypeRepository contactMechPposTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContactMechPposTypeMockMvc;

    private ContactMechPposType contactMechPposType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactMechPposType createEntity(EntityManager em) {
        ContactMechPposType contactMechPposType = new ContactMechPposType()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION);
        return contactMechPposType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactMechPposType createUpdatedEntity(EntityManager em) {
        ContactMechPposType contactMechPposType = new ContactMechPposType()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);
        return contactMechPposType;
    }

    @BeforeEach
    public void initTest() {
        contactMechPposType = createEntity(em);
    }

    @Test
    @Transactional
    public void createContactMechPposType() throws Exception {
        int databaseSizeBeforeCreate = contactMechPposTypeRepository.findAll().size();
        // Create the ContactMechPposType
        restContactMechPposTypeMockMvc.perform(post("/api/contact-mech-ppos-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactMechPposType)))
            .andExpect(status().isCreated());

        // Validate the ContactMechPposType in the database
        List<ContactMechPposType> contactMechPposTypeList = contactMechPposTypeRepository.findAll();
        assertThat(contactMechPposTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ContactMechPposType testContactMechPposType = contactMechPposTypeList.get(contactMechPposTypeList.size() - 1);
        assertThat(testContactMechPposType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testContactMechPposType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createContactMechPposTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contactMechPposTypeRepository.findAll().size();

        // Create the ContactMechPposType with an existing ID
        contactMechPposType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactMechPposTypeMockMvc.perform(post("/api/contact-mech-ppos-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactMechPposType)))
            .andExpect(status().isBadRequest());

        // Validate the ContactMechPposType in the database
        List<ContactMechPposType> contactMechPposTypeList = contactMechPposTypeRepository.findAll();
        assertThat(contactMechPposTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllContactMechPposTypes() throws Exception {
        // Initialize the database
        contactMechPposTypeRepository.saveAndFlush(contactMechPposType);

        // Get all the contactMechPposTypeList
        restContactMechPposTypeMockMvc.perform(get("/api/contact-mech-ppos-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactMechPposType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getContactMechPposType() throws Exception {
        // Initialize the database
        contactMechPposTypeRepository.saveAndFlush(contactMechPposType);

        // Get the contactMechPposType
        restContactMechPposTypeMockMvc.perform(get("/api/contact-mech-ppos-types/{id}", contactMechPposType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contactMechPposType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingContactMechPposType() throws Exception {
        // Get the contactMechPposType
        restContactMechPposTypeMockMvc.perform(get("/api/contact-mech-ppos-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContactMechPposType() throws Exception {
        // Initialize the database
        contactMechPposTypeRepository.saveAndFlush(contactMechPposType);

        int databaseSizeBeforeUpdate = contactMechPposTypeRepository.findAll().size();

        // Update the contactMechPposType
        ContactMechPposType updatedContactMechPposType = contactMechPposTypeRepository.findById(contactMechPposType.getId()).get();
        // Disconnect from session so that the updates on updatedContactMechPposType are not directly saved in db
        em.detach(updatedContactMechPposType);
        updatedContactMechPposType
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);

        restContactMechPposTypeMockMvc.perform(put("/api/contact-mech-ppos-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedContactMechPposType)))
            .andExpect(status().isOk());

        // Validate the ContactMechPposType in the database
        List<ContactMechPposType> contactMechPposTypeList = contactMechPposTypeRepository.findAll();
        assertThat(contactMechPposTypeList).hasSize(databaseSizeBeforeUpdate);
        ContactMechPposType testContactMechPposType = contactMechPposTypeList.get(contactMechPposTypeList.size() - 1);
        assertThat(testContactMechPposType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testContactMechPposType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingContactMechPposType() throws Exception {
        int databaseSizeBeforeUpdate = contactMechPposTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactMechPposTypeMockMvc.perform(put("/api/contact-mech-ppos-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactMechPposType)))
            .andExpect(status().isBadRequest());

        // Validate the ContactMechPposType in the database
        List<ContactMechPposType> contactMechPposTypeList = contactMechPposTypeRepository.findAll();
        assertThat(contactMechPposTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContactMechPposType() throws Exception {
        // Initialize the database
        contactMechPposTypeRepository.saveAndFlush(contactMechPposType);

        int databaseSizeBeforeDelete = contactMechPposTypeRepository.findAll().size();

        // Delete the contactMechPposType
        restContactMechPposTypeMockMvc.perform(delete("/api/contact-mech-ppos-types/{id}", contactMechPposType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContactMechPposType> contactMechPposTypeList = contactMechPposTypeRepository.findAll();
        assertThat(contactMechPposTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
