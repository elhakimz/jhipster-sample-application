package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.PartyContactMechPpos;
import com.mycompany.myapp.repository.PartyContactMechPposRepository;

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
 * Integration tests for the {@link PartyContactMechPposResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PartyContactMechPposResourceIT {

    private static final LocalDate DEFAULT_FROM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_THRU_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_THRU_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PartyContactMechPposRepository partyContactMechPposRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartyContactMechPposMockMvc;

    private PartyContactMechPpos partyContactMechPpos;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyContactMechPpos createEntity(EntityManager em) {
        PartyContactMechPpos partyContactMechPpos = new PartyContactMechPpos()
            .fromDate(DEFAULT_FROM_DATE)
            .thruDate(DEFAULT_THRU_DATE);
        return partyContactMechPpos;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyContactMechPpos createUpdatedEntity(EntityManager em) {
        PartyContactMechPpos partyContactMechPpos = new PartyContactMechPpos()
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE);
        return partyContactMechPpos;
    }

    @BeforeEach
    public void initTest() {
        partyContactMechPpos = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartyContactMechPpos() throws Exception {
        int databaseSizeBeforeCreate = partyContactMechPposRepository.findAll().size();
        // Create the PartyContactMechPpos
        restPartyContactMechPposMockMvc.perform(post("/api/party-contact-mech-ppos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyContactMechPpos)))
            .andExpect(status().isCreated());

        // Validate the PartyContactMechPpos in the database
        List<PartyContactMechPpos> partyContactMechPposList = partyContactMechPposRepository.findAll();
        assertThat(partyContactMechPposList).hasSize(databaseSizeBeforeCreate + 1);
        PartyContactMechPpos testPartyContactMechPpos = partyContactMechPposList.get(partyContactMechPposList.size() - 1);
        assertThat(testPartyContactMechPpos.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testPartyContactMechPpos.getThruDate()).isEqualTo(DEFAULT_THRU_DATE);
    }

    @Test
    @Transactional
    public void createPartyContactMechPposWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partyContactMechPposRepository.findAll().size();

        // Create the PartyContactMechPpos with an existing ID
        partyContactMechPpos.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyContactMechPposMockMvc.perform(post("/api/party-contact-mech-ppos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyContactMechPpos)))
            .andExpect(status().isBadRequest());

        // Validate the PartyContactMechPpos in the database
        List<PartyContactMechPpos> partyContactMechPposList = partyContactMechPposRepository.findAll();
        assertThat(partyContactMechPposList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPartyContactMechPpos() throws Exception {
        // Initialize the database
        partyContactMechPposRepository.saveAndFlush(partyContactMechPpos);

        // Get all the partyContactMechPposList
        restPartyContactMechPposMockMvc.perform(get("/api/party-contact-mech-ppos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyContactMechPpos.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(DEFAULT_THRU_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getPartyContactMechPpos() throws Exception {
        // Initialize the database
        partyContactMechPposRepository.saveAndFlush(partyContactMechPpos);

        // Get the partyContactMechPpos
        restPartyContactMechPposMockMvc.perform(get("/api/party-contact-mech-ppos/{id}", partyContactMechPpos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partyContactMechPpos.getId().intValue()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.thruDate").value(DEFAULT_THRU_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPartyContactMechPpos() throws Exception {
        // Get the partyContactMechPpos
        restPartyContactMechPposMockMvc.perform(get("/api/party-contact-mech-ppos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartyContactMechPpos() throws Exception {
        // Initialize the database
        partyContactMechPposRepository.saveAndFlush(partyContactMechPpos);

        int databaseSizeBeforeUpdate = partyContactMechPposRepository.findAll().size();

        // Update the partyContactMechPpos
        PartyContactMechPpos updatedPartyContactMechPpos = partyContactMechPposRepository.findById(partyContactMechPpos.getId()).get();
        // Disconnect from session so that the updates on updatedPartyContactMechPpos are not directly saved in db
        em.detach(updatedPartyContactMechPpos);
        updatedPartyContactMechPpos
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE);

        restPartyContactMechPposMockMvc.perform(put("/api/party-contact-mech-ppos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPartyContactMechPpos)))
            .andExpect(status().isOk());

        // Validate the PartyContactMechPpos in the database
        List<PartyContactMechPpos> partyContactMechPposList = partyContactMechPposRepository.findAll();
        assertThat(partyContactMechPposList).hasSize(databaseSizeBeforeUpdate);
        PartyContactMechPpos testPartyContactMechPpos = partyContactMechPposList.get(partyContactMechPposList.size() - 1);
        assertThat(testPartyContactMechPpos.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testPartyContactMechPpos.getThruDate()).isEqualTo(UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPartyContactMechPpos() throws Exception {
        int databaseSizeBeforeUpdate = partyContactMechPposRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyContactMechPposMockMvc.perform(put("/api/party-contact-mech-ppos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyContactMechPpos)))
            .andExpect(status().isBadRequest());

        // Validate the PartyContactMechPpos in the database
        List<PartyContactMechPpos> partyContactMechPposList = partyContactMechPposRepository.findAll();
        assertThat(partyContactMechPposList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePartyContactMechPpos() throws Exception {
        // Initialize the database
        partyContactMechPposRepository.saveAndFlush(partyContactMechPpos);

        int databaseSizeBeforeDelete = partyContactMechPposRepository.findAll().size();

        // Delete the partyContactMechPpos
        restPartyContactMechPposMockMvc.perform(delete("/api/party-contact-mech-ppos/{id}", partyContactMechPpos.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartyContactMechPpos> partyContactMechPposList = partyContactMechPposRepository.findAll();
        assertThat(partyContactMechPposList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
