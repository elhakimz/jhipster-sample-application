package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.PartyFacility;
import com.mycompany.myapp.repository.PartyFacilityRepository;

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
 * Integration tests for the {@link PartyFacilityResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PartyFacilityResourceIT {

    @Autowired
    private PartyFacilityRepository partyFacilityRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartyFacilityMockMvc;

    private PartyFacility partyFacility;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyFacility createEntity(EntityManager em) {
        PartyFacility partyFacility = new PartyFacility();
        return partyFacility;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyFacility createUpdatedEntity(EntityManager em) {
        PartyFacility partyFacility = new PartyFacility();
        return partyFacility;
    }

    @BeforeEach
    public void initTest() {
        partyFacility = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartyFacility() throws Exception {
        int databaseSizeBeforeCreate = partyFacilityRepository.findAll().size();
        // Create the PartyFacility
        restPartyFacilityMockMvc.perform(post("/api/party-facilities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyFacility)))
            .andExpect(status().isCreated());

        // Validate the PartyFacility in the database
        List<PartyFacility> partyFacilityList = partyFacilityRepository.findAll();
        assertThat(partyFacilityList).hasSize(databaseSizeBeforeCreate + 1);
        PartyFacility testPartyFacility = partyFacilityList.get(partyFacilityList.size() - 1);
    }

    @Test
    @Transactional
    public void createPartyFacilityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partyFacilityRepository.findAll().size();

        // Create the PartyFacility with an existing ID
        partyFacility.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyFacilityMockMvc.perform(post("/api/party-facilities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyFacility)))
            .andExpect(status().isBadRequest());

        // Validate the PartyFacility in the database
        List<PartyFacility> partyFacilityList = partyFacilityRepository.findAll();
        assertThat(partyFacilityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPartyFacilities() throws Exception {
        // Initialize the database
        partyFacilityRepository.saveAndFlush(partyFacility);

        // Get all the partyFacilityList
        restPartyFacilityMockMvc.perform(get("/api/party-facilities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyFacility.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getPartyFacility() throws Exception {
        // Initialize the database
        partyFacilityRepository.saveAndFlush(partyFacility);

        // Get the partyFacility
        restPartyFacilityMockMvc.perform(get("/api/party-facilities/{id}", partyFacility.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partyFacility.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingPartyFacility() throws Exception {
        // Get the partyFacility
        restPartyFacilityMockMvc.perform(get("/api/party-facilities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartyFacility() throws Exception {
        // Initialize the database
        partyFacilityRepository.saveAndFlush(partyFacility);

        int databaseSizeBeforeUpdate = partyFacilityRepository.findAll().size();

        // Update the partyFacility
        PartyFacility updatedPartyFacility = partyFacilityRepository.findById(partyFacility.getId()).get();
        // Disconnect from session so that the updates on updatedPartyFacility are not directly saved in db
        em.detach(updatedPartyFacility);

        restPartyFacilityMockMvc.perform(put("/api/party-facilities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPartyFacility)))
            .andExpect(status().isOk());

        // Validate the PartyFacility in the database
        List<PartyFacility> partyFacilityList = partyFacilityRepository.findAll();
        assertThat(partyFacilityList).hasSize(databaseSizeBeforeUpdate);
        PartyFacility testPartyFacility = partyFacilityList.get(partyFacilityList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingPartyFacility() throws Exception {
        int databaseSizeBeforeUpdate = partyFacilityRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyFacilityMockMvc.perform(put("/api/party-facilities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyFacility)))
            .andExpect(status().isBadRequest());

        // Validate the PartyFacility in the database
        List<PartyFacility> partyFacilityList = partyFacilityRepository.findAll();
        assertThat(partyFacilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePartyFacility() throws Exception {
        // Initialize the database
        partyFacilityRepository.saveAndFlush(partyFacility);

        int databaseSizeBeforeDelete = partyFacilityRepository.findAll().size();

        // Delete the partyFacility
        restPartyFacilityMockMvc.perform(delete("/api/party-facilities/{id}", partyFacility.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartyFacility> partyFacilityList = partyFacilityRepository.findAll();
        assertThat(partyFacilityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
