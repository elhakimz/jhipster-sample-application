package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.PartyIdentification;
import com.mycompany.myapp.repository.PartyIdentificationRepository;

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

import com.mycompany.myapp.domain.enumeration.IdentificationType;
/**
 * Integration tests for the {@link PartyIdentificationResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PartyIdentificationResourceIT {

    private static final IdentificationType DEFAULT_IDENTIFICATION_TYPE = IdentificationType.KTP;
    private static final IdentificationType UPDATED_IDENTIFICATION_TYPE = IdentificationType.PASSPORT;

    private static final String DEFAULT_IDENT_NO = "AAAAAAAAAA";
    private static final String UPDATED_IDENT_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VALID_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PartyIdentificationRepository partyIdentificationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartyIdentificationMockMvc;

    private PartyIdentification partyIdentification;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyIdentification createEntity(EntityManager em) {
        PartyIdentification partyIdentification = new PartyIdentification()
            .identificationType(DEFAULT_IDENTIFICATION_TYPE)
            .identNo(DEFAULT_IDENT_NO)
            .validDate(DEFAULT_VALID_DATE);
        return partyIdentification;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyIdentification createUpdatedEntity(EntityManager em) {
        PartyIdentification partyIdentification = new PartyIdentification()
            .identificationType(UPDATED_IDENTIFICATION_TYPE)
            .identNo(UPDATED_IDENT_NO)
            .validDate(UPDATED_VALID_DATE);
        return partyIdentification;
    }

    @BeforeEach
    public void initTest() {
        partyIdentification = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartyIdentification() throws Exception {
        int databaseSizeBeforeCreate = partyIdentificationRepository.findAll().size();
        // Create the PartyIdentification
        restPartyIdentificationMockMvc.perform(post("/api/party-identifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyIdentification)))
            .andExpect(status().isCreated());

        // Validate the PartyIdentification in the database
        List<PartyIdentification> partyIdentificationList = partyIdentificationRepository.findAll();
        assertThat(partyIdentificationList).hasSize(databaseSizeBeforeCreate + 1);
        PartyIdentification testPartyIdentification = partyIdentificationList.get(partyIdentificationList.size() - 1);
        assertThat(testPartyIdentification.getIdentificationType()).isEqualTo(DEFAULT_IDENTIFICATION_TYPE);
        assertThat(testPartyIdentification.getIdentNo()).isEqualTo(DEFAULT_IDENT_NO);
        assertThat(testPartyIdentification.getValidDate()).isEqualTo(DEFAULT_VALID_DATE);
    }

    @Test
    @Transactional
    public void createPartyIdentificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partyIdentificationRepository.findAll().size();

        // Create the PartyIdentification with an existing ID
        partyIdentification.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyIdentificationMockMvc.perform(post("/api/party-identifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyIdentification)))
            .andExpect(status().isBadRequest());

        // Validate the PartyIdentification in the database
        List<PartyIdentification> partyIdentificationList = partyIdentificationRepository.findAll();
        assertThat(partyIdentificationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdentificationTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = partyIdentificationRepository.findAll().size();
        // set the field null
        partyIdentification.setIdentificationType(null);

        // Create the PartyIdentification, which fails.


        restPartyIdentificationMockMvc.perform(post("/api/party-identifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyIdentification)))
            .andExpect(status().isBadRequest());

        List<PartyIdentification> partyIdentificationList = partyIdentificationRepository.findAll();
        assertThat(partyIdentificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPartyIdentifications() throws Exception {
        // Initialize the database
        partyIdentificationRepository.saveAndFlush(partyIdentification);

        // Get all the partyIdentificationList
        restPartyIdentificationMockMvc.perform(get("/api/party-identifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyIdentification.getId().intValue())))
            .andExpect(jsonPath("$.[*].identificationType").value(hasItem(DEFAULT_IDENTIFICATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].identNo").value(hasItem(DEFAULT_IDENT_NO)))
            .andExpect(jsonPath("$.[*].validDate").value(hasItem(DEFAULT_VALID_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getPartyIdentification() throws Exception {
        // Initialize the database
        partyIdentificationRepository.saveAndFlush(partyIdentification);

        // Get the partyIdentification
        restPartyIdentificationMockMvc.perform(get("/api/party-identifications/{id}", partyIdentification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partyIdentification.getId().intValue()))
            .andExpect(jsonPath("$.identificationType").value(DEFAULT_IDENTIFICATION_TYPE.toString()))
            .andExpect(jsonPath("$.identNo").value(DEFAULT_IDENT_NO))
            .andExpect(jsonPath("$.validDate").value(DEFAULT_VALID_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPartyIdentification() throws Exception {
        // Get the partyIdentification
        restPartyIdentificationMockMvc.perform(get("/api/party-identifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartyIdentification() throws Exception {
        // Initialize the database
        partyIdentificationRepository.saveAndFlush(partyIdentification);

        int databaseSizeBeforeUpdate = partyIdentificationRepository.findAll().size();

        // Update the partyIdentification
        PartyIdentification updatedPartyIdentification = partyIdentificationRepository.findById(partyIdentification.getId()).get();
        // Disconnect from session so that the updates on updatedPartyIdentification are not directly saved in db
        em.detach(updatedPartyIdentification);
        updatedPartyIdentification
            .identificationType(UPDATED_IDENTIFICATION_TYPE)
            .identNo(UPDATED_IDENT_NO)
            .validDate(UPDATED_VALID_DATE);

        restPartyIdentificationMockMvc.perform(put("/api/party-identifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPartyIdentification)))
            .andExpect(status().isOk());

        // Validate the PartyIdentification in the database
        List<PartyIdentification> partyIdentificationList = partyIdentificationRepository.findAll();
        assertThat(partyIdentificationList).hasSize(databaseSizeBeforeUpdate);
        PartyIdentification testPartyIdentification = partyIdentificationList.get(partyIdentificationList.size() - 1);
        assertThat(testPartyIdentification.getIdentificationType()).isEqualTo(UPDATED_IDENTIFICATION_TYPE);
        assertThat(testPartyIdentification.getIdentNo()).isEqualTo(UPDATED_IDENT_NO);
        assertThat(testPartyIdentification.getValidDate()).isEqualTo(UPDATED_VALID_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPartyIdentification() throws Exception {
        int databaseSizeBeforeUpdate = partyIdentificationRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyIdentificationMockMvc.perform(put("/api/party-identifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyIdentification)))
            .andExpect(status().isBadRequest());

        // Validate the PartyIdentification in the database
        List<PartyIdentification> partyIdentificationList = partyIdentificationRepository.findAll();
        assertThat(partyIdentificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePartyIdentification() throws Exception {
        // Initialize the database
        partyIdentificationRepository.saveAndFlush(partyIdentification);

        int databaseSizeBeforeDelete = partyIdentificationRepository.findAll().size();

        // Delete the partyIdentification
        restPartyIdentificationMockMvc.perform(delete("/api/party-identifications/{id}", partyIdentification.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartyIdentification> partyIdentificationList = partyIdentificationRepository.findAll();
        assertThat(partyIdentificationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
