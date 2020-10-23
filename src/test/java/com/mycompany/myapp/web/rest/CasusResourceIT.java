package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.Casus;
import com.mycompany.myapp.repository.CasusRepository;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.CasusStatusType;
/**
 * Integration tests for the {@link CasusResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CasusResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_START = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final CasusStatusType DEFAULT_STATUS_TYPE = CasusStatusType.ACTIVE;
    private static final CasusStatusType UPDATED_STATUS_TYPE = CasusStatusType.NONACTIVE;

    @Autowired
    private CasusRepository casusRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCasusMockMvc;

    private Casus casus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Casus createEntity(EntityManager em) {
        Casus casus = new Casus()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION)
            .start(DEFAULT_START)
            .statusType(DEFAULT_STATUS_TYPE);
        return casus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Casus createUpdatedEntity(EntityManager em) {
        Casus casus = new Casus()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .start(UPDATED_START)
            .statusType(UPDATED_STATUS_TYPE);
        return casus;
    }

    @BeforeEach
    public void initTest() {
        casus = createEntity(em);
    }

    @Test
    @Transactional
    public void createCasus() throws Exception {
        int databaseSizeBeforeCreate = casusRepository.findAll().size();
        // Create the Casus
        restCasusMockMvc.perform(post("/api/casuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(casus)))
            .andExpect(status().isCreated());

        // Validate the Casus in the database
        List<Casus> casusList = casusRepository.findAll();
        assertThat(casusList).hasSize(databaseSizeBeforeCreate + 1);
        Casus testCasus = casusList.get(casusList.size() - 1);
        assertThat(testCasus.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCasus.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCasus.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testCasus.getStatusType()).isEqualTo(DEFAULT_STATUS_TYPE);
    }

    @Test
    @Transactional
    public void createCasusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = casusRepository.findAll().size();

        // Create the Casus with an existing ID
        casus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCasusMockMvc.perform(post("/api/casuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(casus)))
            .andExpect(status().isBadRequest());

        // Validate the Casus in the database
        List<Casus> casusList = casusRepository.findAll();
        assertThat(casusList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCasuses() throws Exception {
        // Initialize the database
        casusRepository.saveAndFlush(casus);

        // Get all the casusList
        restCasusMockMvc.perform(get("/api/casuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(casus.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].start").value(hasItem(sameInstant(DEFAULT_START))))
            .andExpect(jsonPath("$.[*].statusType").value(hasItem(DEFAULT_STATUS_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getCasus() throws Exception {
        // Initialize the database
        casusRepository.saveAndFlush(casus);

        // Get the casus
        restCasusMockMvc.perform(get("/api/casuses/{id}", casus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(casus.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.start").value(sameInstant(DEFAULT_START)))
            .andExpect(jsonPath("$.statusType").value(DEFAULT_STATUS_TYPE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingCasus() throws Exception {
        // Get the casus
        restCasusMockMvc.perform(get("/api/casuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCasus() throws Exception {
        // Initialize the database
        casusRepository.saveAndFlush(casus);

        int databaseSizeBeforeUpdate = casusRepository.findAll().size();

        // Update the casus
        Casus updatedCasus = casusRepository.findById(casus.getId()).get();
        // Disconnect from session so that the updates on updatedCasus are not directly saved in db
        em.detach(updatedCasus);
        updatedCasus
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .start(UPDATED_START)
            .statusType(UPDATED_STATUS_TYPE);

        restCasusMockMvc.perform(put("/api/casuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCasus)))
            .andExpect(status().isOk());

        // Validate the Casus in the database
        List<Casus> casusList = casusRepository.findAll();
        assertThat(casusList).hasSize(databaseSizeBeforeUpdate);
        Casus testCasus = casusList.get(casusList.size() - 1);
        assertThat(testCasus.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCasus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCasus.getStart()).isEqualTo(UPDATED_START);
        assertThat(testCasus.getStatusType()).isEqualTo(UPDATED_STATUS_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingCasus() throws Exception {
        int databaseSizeBeforeUpdate = casusRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCasusMockMvc.perform(put("/api/casuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(casus)))
            .andExpect(status().isBadRequest());

        // Validate the Casus in the database
        List<Casus> casusList = casusRepository.findAll();
        assertThat(casusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCasus() throws Exception {
        // Initialize the database
        casusRepository.saveAndFlush(casus);

        int databaseSizeBeforeDelete = casusRepository.findAll().size();

        // Delete the casus
        restCasusMockMvc.perform(delete("/api/casuses/{id}", casus.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Casus> casusList = casusRepository.findAll();
        assertThat(casusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
