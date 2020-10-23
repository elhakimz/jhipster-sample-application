package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.PartyRelationship;
import com.mycompany.myapp.repository.PartyRelationshipRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.PartyRelType;
import com.mycompany.myapp.domain.enumeration.PriorityType;
/**
 * Integration tests for the {@link PartyRelationshipResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PartyRelationshipResourceIT {

    private static final PartyRelType DEFAULT_PARTY_RELATIONSHIP_TYPE = PartyRelType.EMPLOYMENT;
    private static final PartyRelType UPDATED_PARTY_RELATIONSHIP_TYPE = PartyRelType.PURCHASE;

    private static final LocalDate DEFAULT_FROM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_THRU_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_THRU_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final PriorityType DEFAULT_PRIORITY_TYPE = PriorityType.HIGH;
    private static final PriorityType UPDATED_PRIORITY_TYPE = PriorityType.MEDIUM;

    @Autowired
    private PartyRelationshipRepository partyRelationshipRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartyRelationshipMockMvc;

    private PartyRelationship partyRelationship;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyRelationship createEntity(EntityManager em) {
        PartyRelationship partyRelationship = new PartyRelationship()
            .partyRelationshipType(DEFAULT_PARTY_RELATIONSHIP_TYPE)
            .fromDate(DEFAULT_FROM_DATE)
            .thruDate(DEFAULT_THRU_DATE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .priorityType(DEFAULT_PRIORITY_TYPE);
        return partyRelationship;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyRelationship createUpdatedEntity(EntityManager em) {
        PartyRelationship partyRelationship = new PartyRelationship()
            .partyRelationshipType(UPDATED_PARTY_RELATIONSHIP_TYPE)
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .priorityType(UPDATED_PRIORITY_TYPE);
        return partyRelationship;
    }

    @BeforeEach
    public void initTest() {
        partyRelationship = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartyRelationship() throws Exception {
        int databaseSizeBeforeCreate = partyRelationshipRepository.findAll().size();
        // Create the PartyRelationship
        restPartyRelationshipMockMvc.perform(post("/api/party-relationships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyRelationship)))
            .andExpect(status().isCreated());

        // Validate the PartyRelationship in the database
        List<PartyRelationship> partyRelationshipList = partyRelationshipRepository.findAll();
        assertThat(partyRelationshipList).hasSize(databaseSizeBeforeCreate + 1);
        PartyRelationship testPartyRelationship = partyRelationshipList.get(partyRelationshipList.size() - 1);
        assertThat(testPartyRelationship.getPartyRelationshipType()).isEqualTo(DEFAULT_PARTY_RELATIONSHIP_TYPE);
        assertThat(testPartyRelationship.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testPartyRelationship.getThruDate()).isEqualTo(DEFAULT_THRU_DATE);
        assertThat(testPartyRelationship.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPartyRelationship.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPartyRelationship.getPriorityType()).isEqualTo(DEFAULT_PRIORITY_TYPE);
    }

    @Test
    @Transactional
    public void createPartyRelationshipWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partyRelationshipRepository.findAll().size();

        // Create the PartyRelationship with an existing ID
        partyRelationship.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyRelationshipMockMvc.perform(post("/api/party-relationships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyRelationship)))
            .andExpect(status().isBadRequest());

        // Validate the PartyRelationship in the database
        List<PartyRelationship> partyRelationshipList = partyRelationshipRepository.findAll();
        assertThat(partyRelationshipList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPartyRelationshipTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = partyRelationshipRepository.findAll().size();
        // set the field null
        partyRelationship.setPartyRelationshipType(null);

        // Create the PartyRelationship, which fails.


        restPartyRelationshipMockMvc.perform(post("/api/party-relationships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyRelationship)))
            .andExpect(status().isBadRequest());

        List<PartyRelationship> partyRelationshipList = partyRelationshipRepository.findAll();
        assertThat(partyRelationshipList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPartyRelationships() throws Exception {
        // Initialize the database
        partyRelationshipRepository.saveAndFlush(partyRelationship);

        // Get all the partyRelationshipList
        restPartyRelationshipMockMvc.perform(get("/api/party-relationships?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyRelationship.getId().intValue())))
            .andExpect(jsonPath("$.[*].partyRelationshipType").value(hasItem(DEFAULT_PARTY_RELATIONSHIP_TYPE.toString())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(DEFAULT_THRU_DATE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].priorityType").value(hasItem(DEFAULT_PRIORITY_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getPartyRelationship() throws Exception {
        // Initialize the database
        partyRelationshipRepository.saveAndFlush(partyRelationship);

        // Get the partyRelationship
        restPartyRelationshipMockMvc.perform(get("/api/party-relationships/{id}", partyRelationship.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partyRelationship.getId().intValue()))
            .andExpect(jsonPath("$.partyRelationshipType").value(DEFAULT_PARTY_RELATIONSHIP_TYPE.toString()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.thruDate").value(DEFAULT_THRU_DATE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.priorityType").value(DEFAULT_PRIORITY_TYPE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPartyRelationship() throws Exception {
        // Get the partyRelationship
        restPartyRelationshipMockMvc.perform(get("/api/party-relationships/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartyRelationship() throws Exception {
        // Initialize the database
        partyRelationshipRepository.saveAndFlush(partyRelationship);

        int databaseSizeBeforeUpdate = partyRelationshipRepository.findAll().size();

        // Update the partyRelationship
        PartyRelationship updatedPartyRelationship = partyRelationshipRepository.findById(partyRelationship.getId()).get();
        // Disconnect from session so that the updates on updatedPartyRelationship are not directly saved in db
        em.detach(updatedPartyRelationship);
        updatedPartyRelationship
            .partyRelationshipType(UPDATED_PARTY_RELATIONSHIP_TYPE)
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .priorityType(UPDATED_PRIORITY_TYPE);

        restPartyRelationshipMockMvc.perform(put("/api/party-relationships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPartyRelationship)))
            .andExpect(status().isOk());

        // Validate the PartyRelationship in the database
        List<PartyRelationship> partyRelationshipList = partyRelationshipRepository.findAll();
        assertThat(partyRelationshipList).hasSize(databaseSizeBeforeUpdate);
        PartyRelationship testPartyRelationship = partyRelationshipList.get(partyRelationshipList.size() - 1);
        assertThat(testPartyRelationship.getPartyRelationshipType()).isEqualTo(UPDATED_PARTY_RELATIONSHIP_TYPE);
        assertThat(testPartyRelationship.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testPartyRelationship.getThruDate()).isEqualTo(UPDATED_THRU_DATE);
        assertThat(testPartyRelationship.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPartyRelationship.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPartyRelationship.getPriorityType()).isEqualTo(UPDATED_PRIORITY_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPartyRelationship() throws Exception {
        int databaseSizeBeforeUpdate = partyRelationshipRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyRelationshipMockMvc.perform(put("/api/party-relationships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyRelationship)))
            .andExpect(status().isBadRequest());

        // Validate the PartyRelationship in the database
        List<PartyRelationship> partyRelationshipList = partyRelationshipRepository.findAll();
        assertThat(partyRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePartyRelationship() throws Exception {
        // Initialize the database
        partyRelationshipRepository.saveAndFlush(partyRelationship);

        int databaseSizeBeforeDelete = partyRelationshipRepository.findAll().size();

        // Delete the partyRelationship
        restPartyRelationshipMockMvc.perform(delete("/api/party-relationships/{id}", partyRelationship.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartyRelationship> partyRelationshipList = partyRelationshipRepository.findAll();
        assertThat(partyRelationshipList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
