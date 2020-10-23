package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.ContactMechLink;
import com.mycompany.myapp.repository.ContactMechLinkRepository;

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
 * Integration tests for the {@link ContactMechLinkResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ContactMechLinkResourceIT {

    @Autowired
    private ContactMechLinkRepository contactMechLinkRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContactMechLinkMockMvc;

    private ContactMechLink contactMechLink;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactMechLink createEntity(EntityManager em) {
        ContactMechLink contactMechLink = new ContactMechLink();
        return contactMechLink;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactMechLink createUpdatedEntity(EntityManager em) {
        ContactMechLink contactMechLink = new ContactMechLink();
        return contactMechLink;
    }

    @BeforeEach
    public void initTest() {
        contactMechLink = createEntity(em);
    }

    @Test
    @Transactional
    public void createContactMechLink() throws Exception {
        int databaseSizeBeforeCreate = contactMechLinkRepository.findAll().size();
        // Create the ContactMechLink
        restContactMechLinkMockMvc.perform(post("/api/contact-mech-links")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactMechLink)))
            .andExpect(status().isCreated());

        // Validate the ContactMechLink in the database
        List<ContactMechLink> contactMechLinkList = contactMechLinkRepository.findAll();
        assertThat(contactMechLinkList).hasSize(databaseSizeBeforeCreate + 1);
        ContactMechLink testContactMechLink = contactMechLinkList.get(contactMechLinkList.size() - 1);
    }

    @Test
    @Transactional
    public void createContactMechLinkWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contactMechLinkRepository.findAll().size();

        // Create the ContactMechLink with an existing ID
        contactMechLink.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactMechLinkMockMvc.perform(post("/api/contact-mech-links")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactMechLink)))
            .andExpect(status().isBadRequest());

        // Validate the ContactMechLink in the database
        List<ContactMechLink> contactMechLinkList = contactMechLinkRepository.findAll();
        assertThat(contactMechLinkList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllContactMechLinks() throws Exception {
        // Initialize the database
        contactMechLinkRepository.saveAndFlush(contactMechLink);

        // Get all the contactMechLinkList
        restContactMechLinkMockMvc.perform(get("/api/contact-mech-links?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactMechLink.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getContactMechLink() throws Exception {
        // Initialize the database
        contactMechLinkRepository.saveAndFlush(contactMechLink);

        // Get the contactMechLink
        restContactMechLinkMockMvc.perform(get("/api/contact-mech-links/{id}", contactMechLink.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contactMechLink.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingContactMechLink() throws Exception {
        // Get the contactMechLink
        restContactMechLinkMockMvc.perform(get("/api/contact-mech-links/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContactMechLink() throws Exception {
        // Initialize the database
        contactMechLinkRepository.saveAndFlush(contactMechLink);

        int databaseSizeBeforeUpdate = contactMechLinkRepository.findAll().size();

        // Update the contactMechLink
        ContactMechLink updatedContactMechLink = contactMechLinkRepository.findById(contactMechLink.getId()).get();
        // Disconnect from session so that the updates on updatedContactMechLink are not directly saved in db
        em.detach(updatedContactMechLink);

        restContactMechLinkMockMvc.perform(put("/api/contact-mech-links")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedContactMechLink)))
            .andExpect(status().isOk());

        // Validate the ContactMechLink in the database
        List<ContactMechLink> contactMechLinkList = contactMechLinkRepository.findAll();
        assertThat(contactMechLinkList).hasSize(databaseSizeBeforeUpdate);
        ContactMechLink testContactMechLink = contactMechLinkList.get(contactMechLinkList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingContactMechLink() throws Exception {
        int databaseSizeBeforeUpdate = contactMechLinkRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactMechLinkMockMvc.perform(put("/api/contact-mech-links")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactMechLink)))
            .andExpect(status().isBadRequest());

        // Validate the ContactMechLink in the database
        List<ContactMechLink> contactMechLinkList = contactMechLinkRepository.findAll();
        assertThat(contactMechLinkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContactMechLink() throws Exception {
        // Initialize the database
        contactMechLinkRepository.saveAndFlush(contactMechLink);

        int databaseSizeBeforeDelete = contactMechLinkRepository.findAll().size();

        // Delete the contactMechLink
        restContactMechLinkMockMvc.perform(delete("/api/contact-mech-links/{id}", contactMechLink.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContactMechLink> contactMechLinkList = contactMechLinkRepository.findAll();
        assertThat(contactMechLinkList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
