package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.FacilityContactMech;
import com.mycompany.myapp.repository.FacilityContactMechRepository;

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
 * Integration tests for the {@link FacilityContactMechResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FacilityContactMechResourceIT {

    @Autowired
    private FacilityContactMechRepository facilityContactMechRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFacilityContactMechMockMvc;

    private FacilityContactMech facilityContactMech;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FacilityContactMech createEntity(EntityManager em) {
        FacilityContactMech facilityContactMech = new FacilityContactMech();
        return facilityContactMech;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FacilityContactMech createUpdatedEntity(EntityManager em) {
        FacilityContactMech facilityContactMech = new FacilityContactMech();
        return facilityContactMech;
    }

    @BeforeEach
    public void initTest() {
        facilityContactMech = createEntity(em);
    }

    @Test
    @Transactional
    public void createFacilityContactMech() throws Exception {
        int databaseSizeBeforeCreate = facilityContactMechRepository.findAll().size();
        // Create the FacilityContactMech
        restFacilityContactMechMockMvc.perform(post("/api/facility-contact-meches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facilityContactMech)))
            .andExpect(status().isCreated());

        // Validate the FacilityContactMech in the database
        List<FacilityContactMech> facilityContactMechList = facilityContactMechRepository.findAll();
        assertThat(facilityContactMechList).hasSize(databaseSizeBeforeCreate + 1);
        FacilityContactMech testFacilityContactMech = facilityContactMechList.get(facilityContactMechList.size() - 1);
    }

    @Test
    @Transactional
    public void createFacilityContactMechWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = facilityContactMechRepository.findAll().size();

        // Create the FacilityContactMech with an existing ID
        facilityContactMech.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFacilityContactMechMockMvc.perform(post("/api/facility-contact-meches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facilityContactMech)))
            .andExpect(status().isBadRequest());

        // Validate the FacilityContactMech in the database
        List<FacilityContactMech> facilityContactMechList = facilityContactMechRepository.findAll();
        assertThat(facilityContactMechList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFacilityContactMeches() throws Exception {
        // Initialize the database
        facilityContactMechRepository.saveAndFlush(facilityContactMech);

        // Get all the facilityContactMechList
        restFacilityContactMechMockMvc.perform(get("/api/facility-contact-meches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facilityContactMech.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getFacilityContactMech() throws Exception {
        // Initialize the database
        facilityContactMechRepository.saveAndFlush(facilityContactMech);

        // Get the facilityContactMech
        restFacilityContactMechMockMvc.perform(get("/api/facility-contact-meches/{id}", facilityContactMech.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(facilityContactMech.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingFacilityContactMech() throws Exception {
        // Get the facilityContactMech
        restFacilityContactMechMockMvc.perform(get("/api/facility-contact-meches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFacilityContactMech() throws Exception {
        // Initialize the database
        facilityContactMechRepository.saveAndFlush(facilityContactMech);

        int databaseSizeBeforeUpdate = facilityContactMechRepository.findAll().size();

        // Update the facilityContactMech
        FacilityContactMech updatedFacilityContactMech = facilityContactMechRepository.findById(facilityContactMech.getId()).get();
        // Disconnect from session so that the updates on updatedFacilityContactMech are not directly saved in db
        em.detach(updatedFacilityContactMech);

        restFacilityContactMechMockMvc.perform(put("/api/facility-contact-meches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFacilityContactMech)))
            .andExpect(status().isOk());

        // Validate the FacilityContactMech in the database
        List<FacilityContactMech> facilityContactMechList = facilityContactMechRepository.findAll();
        assertThat(facilityContactMechList).hasSize(databaseSizeBeforeUpdate);
        FacilityContactMech testFacilityContactMech = facilityContactMechList.get(facilityContactMechList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingFacilityContactMech() throws Exception {
        int databaseSizeBeforeUpdate = facilityContactMechRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFacilityContactMechMockMvc.perform(put("/api/facility-contact-meches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facilityContactMech)))
            .andExpect(status().isBadRequest());

        // Validate the FacilityContactMech in the database
        List<FacilityContactMech> facilityContactMechList = facilityContactMechRepository.findAll();
        assertThat(facilityContactMechList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFacilityContactMech() throws Exception {
        // Initialize the database
        facilityContactMechRepository.saveAndFlush(facilityContactMech);

        int databaseSizeBeforeDelete = facilityContactMechRepository.findAll().size();

        // Delete the facilityContactMech
        restFacilityContactMechMockMvc.perform(delete("/api/facility-contact-meches/{id}", facilityContactMech.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FacilityContactMech> facilityContactMechList = facilityContactMechRepository.findAll();
        assertThat(facilityContactMechList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
