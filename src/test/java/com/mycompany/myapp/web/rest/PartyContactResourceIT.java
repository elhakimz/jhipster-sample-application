package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.PartyContact;
import com.mycompany.myapp.repository.PartyContactRepository;

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

import com.mycompany.myapp.domain.enumeration.ContactType;
/**
 * Integration tests for the {@link PartyContactResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PartyContactResourceIT {

    private static final ContactType DEFAULT_CONTACT_TYPE = ContactType.PHONE;
    private static final ContactType UPDATED_CONTACT_TYPE = ContactType.EMAIL;

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PRIMARY = false;
    private static final Boolean UPDATED_PRIMARY = true;

    @Autowired
    private PartyContactRepository partyContactRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartyContactMockMvc;

    private PartyContact partyContact;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyContact createEntity(EntityManager em) {
        PartyContact partyContact = new PartyContact()
            .contactType(DEFAULT_CONTACT_TYPE)
            .number(DEFAULT_NUMBER)
            .primary(DEFAULT_PRIMARY);
        return partyContact;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyContact createUpdatedEntity(EntityManager em) {
        PartyContact partyContact = new PartyContact()
            .contactType(UPDATED_CONTACT_TYPE)
            .number(UPDATED_NUMBER)
            .primary(UPDATED_PRIMARY);
        return partyContact;
    }

    @BeforeEach
    public void initTest() {
        partyContact = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartyContact() throws Exception {
        int databaseSizeBeforeCreate = partyContactRepository.findAll().size();
        // Create the PartyContact
        restPartyContactMockMvc.perform(post("/api/party-contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyContact)))
            .andExpect(status().isCreated());

        // Validate the PartyContact in the database
        List<PartyContact> partyContactList = partyContactRepository.findAll();
        assertThat(partyContactList).hasSize(databaseSizeBeforeCreate + 1);
        PartyContact testPartyContact = partyContactList.get(partyContactList.size() - 1);
        assertThat(testPartyContact.getContactType()).isEqualTo(DEFAULT_CONTACT_TYPE);
        assertThat(testPartyContact.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testPartyContact.isPrimary()).isEqualTo(DEFAULT_PRIMARY);
    }

    @Test
    @Transactional
    public void createPartyContactWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partyContactRepository.findAll().size();

        // Create the PartyContact with an existing ID
        partyContact.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyContactMockMvc.perform(post("/api/party-contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyContact)))
            .andExpect(status().isBadRequest());

        // Validate the PartyContact in the database
        List<PartyContact> partyContactList = partyContactRepository.findAll();
        assertThat(partyContactList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkContactTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = partyContactRepository.findAll().size();
        // set the field null
        partyContact.setContactType(null);

        // Create the PartyContact, which fails.


        restPartyContactMockMvc.perform(post("/api/party-contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyContact)))
            .andExpect(status().isBadRequest());

        List<PartyContact> partyContactList = partyContactRepository.findAll();
        assertThat(partyContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPartyContacts() throws Exception {
        // Initialize the database
        partyContactRepository.saveAndFlush(partyContact);

        // Get all the partyContactList
        restPartyContactMockMvc.perform(get("/api/party-contacts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyContact.getId().intValue())))
            .andExpect(jsonPath("$.[*].contactType").value(hasItem(DEFAULT_CONTACT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].primary").value(hasItem(DEFAULT_PRIMARY.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPartyContact() throws Exception {
        // Initialize the database
        partyContactRepository.saveAndFlush(partyContact);

        // Get the partyContact
        restPartyContactMockMvc.perform(get("/api/party-contacts/{id}", partyContact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partyContact.getId().intValue()))
            .andExpect(jsonPath("$.contactType").value(DEFAULT_CONTACT_TYPE.toString()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.primary").value(DEFAULT_PRIMARY.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingPartyContact() throws Exception {
        // Get the partyContact
        restPartyContactMockMvc.perform(get("/api/party-contacts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartyContact() throws Exception {
        // Initialize the database
        partyContactRepository.saveAndFlush(partyContact);

        int databaseSizeBeforeUpdate = partyContactRepository.findAll().size();

        // Update the partyContact
        PartyContact updatedPartyContact = partyContactRepository.findById(partyContact.getId()).get();
        // Disconnect from session so that the updates on updatedPartyContact are not directly saved in db
        em.detach(updatedPartyContact);
        updatedPartyContact
            .contactType(UPDATED_CONTACT_TYPE)
            .number(UPDATED_NUMBER)
            .primary(UPDATED_PRIMARY);

        restPartyContactMockMvc.perform(put("/api/party-contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPartyContact)))
            .andExpect(status().isOk());

        // Validate the PartyContact in the database
        List<PartyContact> partyContactList = partyContactRepository.findAll();
        assertThat(partyContactList).hasSize(databaseSizeBeforeUpdate);
        PartyContact testPartyContact = partyContactList.get(partyContactList.size() - 1);
        assertThat(testPartyContact.getContactType()).isEqualTo(UPDATED_CONTACT_TYPE);
        assertThat(testPartyContact.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testPartyContact.isPrimary()).isEqualTo(UPDATED_PRIMARY);
    }

    @Test
    @Transactional
    public void updateNonExistingPartyContact() throws Exception {
        int databaseSizeBeforeUpdate = partyContactRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyContactMockMvc.perform(put("/api/party-contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyContact)))
            .andExpect(status().isBadRequest());

        // Validate the PartyContact in the database
        List<PartyContact> partyContactList = partyContactRepository.findAll();
        assertThat(partyContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePartyContact() throws Exception {
        // Initialize the database
        partyContactRepository.saveAndFlush(partyContact);

        int databaseSizeBeforeDelete = partyContactRepository.findAll().size();

        // Delete the partyContact
        restPartyContactMockMvc.perform(delete("/api/party-contacts/{id}", partyContact.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartyContact> partyContactList = partyContactRepository.findAll();
        assertThat(partyContactList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
