package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.PartyClassification;
import com.mycompany.myapp.repository.PartyClassificationRepository;

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

import com.mycompany.myapp.domain.enumeration.PartyClassType;
/**
 * Integration tests for the {@link PartyClassificationResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PartyClassificationResourceIT {

    private static final LocalDate DEFAULT_FROM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_THRU_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_THRU_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final PartyClassType DEFAULT_PARTY_CLASS_TYPE = PartyClassType.ORG_MINORITY;
    private static final PartyClassType UPDATED_PARTY_CLASS_TYPE = PartyClassType.ORG_INDUSTRY;

    @Autowired
    private PartyClassificationRepository partyClassificationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartyClassificationMockMvc;

    private PartyClassification partyClassification;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyClassification createEntity(EntityManager em) {
        PartyClassification partyClassification = new PartyClassification()
            .fromDate(DEFAULT_FROM_DATE)
            .thruDate(DEFAULT_THRU_DATE)
            .partyClassType(DEFAULT_PARTY_CLASS_TYPE);
        return partyClassification;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyClassification createUpdatedEntity(EntityManager em) {
        PartyClassification partyClassification = new PartyClassification()
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .partyClassType(UPDATED_PARTY_CLASS_TYPE);
        return partyClassification;
    }

    @BeforeEach
    public void initTest() {
        partyClassification = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartyClassification() throws Exception {
        int databaseSizeBeforeCreate = partyClassificationRepository.findAll().size();
        // Create the PartyClassification
        restPartyClassificationMockMvc.perform(post("/api/party-classifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyClassification)))
            .andExpect(status().isCreated());

        // Validate the PartyClassification in the database
        List<PartyClassification> partyClassificationList = partyClassificationRepository.findAll();
        assertThat(partyClassificationList).hasSize(databaseSizeBeforeCreate + 1);
        PartyClassification testPartyClassification = partyClassificationList.get(partyClassificationList.size() - 1);
        assertThat(testPartyClassification.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testPartyClassification.getThruDate()).isEqualTo(DEFAULT_THRU_DATE);
        assertThat(testPartyClassification.getPartyClassType()).isEqualTo(DEFAULT_PARTY_CLASS_TYPE);
    }

    @Test
    @Transactional
    public void createPartyClassificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partyClassificationRepository.findAll().size();

        // Create the PartyClassification with an existing ID
        partyClassification.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyClassificationMockMvc.perform(post("/api/party-classifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyClassification)))
            .andExpect(status().isBadRequest());

        // Validate the PartyClassification in the database
        List<PartyClassification> partyClassificationList = partyClassificationRepository.findAll();
        assertThat(partyClassificationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPartyClassifications() throws Exception {
        // Initialize the database
        partyClassificationRepository.saveAndFlush(partyClassification);

        // Get all the partyClassificationList
        restPartyClassificationMockMvc.perform(get("/api/party-classifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyClassification.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(DEFAULT_THRU_DATE.toString())))
            .andExpect(jsonPath("$.[*].partyClassType").value(hasItem(DEFAULT_PARTY_CLASS_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getPartyClassification() throws Exception {
        // Initialize the database
        partyClassificationRepository.saveAndFlush(partyClassification);

        // Get the partyClassification
        restPartyClassificationMockMvc.perform(get("/api/party-classifications/{id}", partyClassification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partyClassification.getId().intValue()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.thruDate").value(DEFAULT_THRU_DATE.toString()))
            .andExpect(jsonPath("$.partyClassType").value(DEFAULT_PARTY_CLASS_TYPE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPartyClassification() throws Exception {
        // Get the partyClassification
        restPartyClassificationMockMvc.perform(get("/api/party-classifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartyClassification() throws Exception {
        // Initialize the database
        partyClassificationRepository.saveAndFlush(partyClassification);

        int databaseSizeBeforeUpdate = partyClassificationRepository.findAll().size();

        // Update the partyClassification
        PartyClassification updatedPartyClassification = partyClassificationRepository.findById(partyClassification.getId()).get();
        // Disconnect from session so that the updates on updatedPartyClassification are not directly saved in db
        em.detach(updatedPartyClassification);
        updatedPartyClassification
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .partyClassType(UPDATED_PARTY_CLASS_TYPE);

        restPartyClassificationMockMvc.perform(put("/api/party-classifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPartyClassification)))
            .andExpect(status().isOk());

        // Validate the PartyClassification in the database
        List<PartyClassification> partyClassificationList = partyClassificationRepository.findAll();
        assertThat(partyClassificationList).hasSize(databaseSizeBeforeUpdate);
        PartyClassification testPartyClassification = partyClassificationList.get(partyClassificationList.size() - 1);
        assertThat(testPartyClassification.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testPartyClassification.getThruDate()).isEqualTo(UPDATED_THRU_DATE);
        assertThat(testPartyClassification.getPartyClassType()).isEqualTo(UPDATED_PARTY_CLASS_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPartyClassification() throws Exception {
        int databaseSizeBeforeUpdate = partyClassificationRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyClassificationMockMvc.perform(put("/api/party-classifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyClassification)))
            .andExpect(status().isBadRequest());

        // Validate the PartyClassification in the database
        List<PartyClassification> partyClassificationList = partyClassificationRepository.findAll();
        assertThat(partyClassificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePartyClassification() throws Exception {
        // Initialize the database
        partyClassificationRepository.saveAndFlush(partyClassification);

        int databaseSizeBeforeDelete = partyClassificationRepository.findAll().size();

        // Delete the partyClassification
        restPartyClassificationMockMvc.perform(delete("/api/party-classifications/{id}", partyClassification.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartyClassification> partyClassificationList = partyClassificationRepository.findAll();
        assertThat(partyClassificationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
