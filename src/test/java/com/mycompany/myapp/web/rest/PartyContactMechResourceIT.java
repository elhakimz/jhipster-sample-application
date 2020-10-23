package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.PartyContactMech;
import com.mycompany.myapp.repository.PartyContactMechRepository;

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

/**
 * Integration tests for the {@link PartyContactMechResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PartyContactMechResourceIT {

    private static final LocalDate DEFAULT_FROM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_THRU_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_THRU_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_NON_SOLICITATION = false;
    private static final Boolean UPDATED_NON_SOLICITATION = true;

    @Autowired
    private PartyContactMechRepository partyContactMechRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartyContactMechMockMvc;

    private PartyContactMech partyContactMech;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyContactMech createEntity(EntityManager em) {
        PartyContactMech partyContactMech = new PartyContactMech()
            .fromDate(DEFAULT_FROM_DATE)
            .thruDate(DEFAULT_THRU_DATE)
            .nonSolicitation(DEFAULT_NON_SOLICITATION);
        return partyContactMech;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyContactMech createUpdatedEntity(EntityManager em) {
        PartyContactMech partyContactMech = new PartyContactMech()
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .nonSolicitation(UPDATED_NON_SOLICITATION);
        return partyContactMech;
    }

    @BeforeEach
    public void initTest() {
        partyContactMech = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartyContactMech() throws Exception {
        int databaseSizeBeforeCreate = partyContactMechRepository.findAll().size();
        // Create the PartyContactMech
        restPartyContactMechMockMvc.perform(post("/api/party-contact-meches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyContactMech)))
            .andExpect(status().isCreated());

        // Validate the PartyContactMech in the database
        List<PartyContactMech> partyContactMechList = partyContactMechRepository.findAll();
        assertThat(partyContactMechList).hasSize(databaseSizeBeforeCreate + 1);
        PartyContactMech testPartyContactMech = partyContactMechList.get(partyContactMechList.size() - 1);
        assertThat(testPartyContactMech.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testPartyContactMech.getThruDate()).isEqualTo(DEFAULT_THRU_DATE);
        assertThat(testPartyContactMech.isNonSolicitation()).isEqualTo(DEFAULT_NON_SOLICITATION);
    }

    @Test
    @Transactional
    public void createPartyContactMechWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partyContactMechRepository.findAll().size();

        // Create the PartyContactMech with an existing ID
        partyContactMech.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyContactMechMockMvc.perform(post("/api/party-contact-meches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyContactMech)))
            .andExpect(status().isBadRequest());

        // Validate the PartyContactMech in the database
        List<PartyContactMech> partyContactMechList = partyContactMechRepository.findAll();
        assertThat(partyContactMechList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPartyContactMeches() throws Exception {
        // Initialize the database
        partyContactMechRepository.saveAndFlush(partyContactMech);

        // Get all the partyContactMechList
        restPartyContactMechMockMvc.perform(get("/api/party-contact-meches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyContactMech.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(DEFAULT_THRU_DATE.toString())))
            .andExpect(jsonPath("$.[*].nonSolicitation").value(hasItem(DEFAULT_NON_SOLICITATION.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPartyContactMech() throws Exception {
        // Initialize the database
        partyContactMechRepository.saveAndFlush(partyContactMech);

        // Get the partyContactMech
        restPartyContactMechMockMvc.perform(get("/api/party-contact-meches/{id}", partyContactMech.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partyContactMech.getId().intValue()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.thruDate").value(DEFAULT_THRU_DATE.toString()))
            .andExpect(jsonPath("$.nonSolicitation").value(DEFAULT_NON_SOLICITATION.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingPartyContactMech() throws Exception {
        // Get the partyContactMech
        restPartyContactMechMockMvc.perform(get("/api/party-contact-meches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartyContactMech() throws Exception {
        // Initialize the database
        partyContactMechRepository.saveAndFlush(partyContactMech);

        int databaseSizeBeforeUpdate = partyContactMechRepository.findAll().size();

        // Update the partyContactMech
        PartyContactMech updatedPartyContactMech = partyContactMechRepository.findById(partyContactMech.getId()).get();
        // Disconnect from session so that the updates on updatedPartyContactMech are not directly saved in db
        em.detach(updatedPartyContactMech);
        updatedPartyContactMech
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .nonSolicitation(UPDATED_NON_SOLICITATION);

        restPartyContactMechMockMvc.perform(put("/api/party-contact-meches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPartyContactMech)))
            .andExpect(status().isOk());

        // Validate the PartyContactMech in the database
        List<PartyContactMech> partyContactMechList = partyContactMechRepository.findAll();
        assertThat(partyContactMechList).hasSize(databaseSizeBeforeUpdate);
        PartyContactMech testPartyContactMech = partyContactMechList.get(partyContactMechList.size() - 1);
        assertThat(testPartyContactMech.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testPartyContactMech.getThruDate()).isEqualTo(UPDATED_THRU_DATE);
        assertThat(testPartyContactMech.isNonSolicitation()).isEqualTo(UPDATED_NON_SOLICITATION);
    }

    @Test
    @Transactional
    public void updateNonExistingPartyContactMech() throws Exception {
        int databaseSizeBeforeUpdate = partyContactMechRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyContactMechMockMvc.perform(put("/api/party-contact-meches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyContactMech)))
            .andExpect(status().isBadRequest());

        // Validate the PartyContactMech in the database
        List<PartyContactMech> partyContactMechList = partyContactMechRepository.findAll();
        assertThat(partyContactMechList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePartyContactMech() throws Exception {
        // Initialize the database
        partyContactMechRepository.saveAndFlush(partyContactMech);

        int databaseSizeBeforeDelete = partyContactMechRepository.findAll().size();

        // Delete the partyContactMech
        restPartyContactMechMockMvc.perform(delete("/api/party-contact-meches/{id}", partyContactMech.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartyContactMech> partyContactMechList = partyContactMechRepository.findAll();
        assertThat(partyContactMechList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
