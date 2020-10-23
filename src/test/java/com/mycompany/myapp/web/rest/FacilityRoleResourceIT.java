package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.FacilityRole;
import com.mycompany.myapp.repository.FacilityRoleRepository;

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
 * Integration tests for the {@link FacilityRoleResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FacilityRoleResourceIT {

    @Autowired
    private FacilityRoleRepository facilityRoleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFacilityRoleMockMvc;

    private FacilityRole facilityRole;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FacilityRole createEntity(EntityManager em) {
        FacilityRole facilityRole = new FacilityRole();
        return facilityRole;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FacilityRole createUpdatedEntity(EntityManager em) {
        FacilityRole facilityRole = new FacilityRole();
        return facilityRole;
    }

    @BeforeEach
    public void initTest() {
        facilityRole = createEntity(em);
    }

    @Test
    @Transactional
    public void createFacilityRole() throws Exception {
        int databaseSizeBeforeCreate = facilityRoleRepository.findAll().size();
        // Create the FacilityRole
        restFacilityRoleMockMvc.perform(post("/api/facility-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facilityRole)))
            .andExpect(status().isCreated());

        // Validate the FacilityRole in the database
        List<FacilityRole> facilityRoleList = facilityRoleRepository.findAll();
        assertThat(facilityRoleList).hasSize(databaseSizeBeforeCreate + 1);
        FacilityRole testFacilityRole = facilityRoleList.get(facilityRoleList.size() - 1);
    }

    @Test
    @Transactional
    public void createFacilityRoleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = facilityRoleRepository.findAll().size();

        // Create the FacilityRole with an existing ID
        facilityRole.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFacilityRoleMockMvc.perform(post("/api/facility-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facilityRole)))
            .andExpect(status().isBadRequest());

        // Validate the FacilityRole in the database
        List<FacilityRole> facilityRoleList = facilityRoleRepository.findAll();
        assertThat(facilityRoleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFacilityRoles() throws Exception {
        // Initialize the database
        facilityRoleRepository.saveAndFlush(facilityRole);

        // Get all the facilityRoleList
        restFacilityRoleMockMvc.perform(get("/api/facility-roles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facilityRole.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getFacilityRole() throws Exception {
        // Initialize the database
        facilityRoleRepository.saveAndFlush(facilityRole);

        // Get the facilityRole
        restFacilityRoleMockMvc.perform(get("/api/facility-roles/{id}", facilityRole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(facilityRole.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingFacilityRole() throws Exception {
        // Get the facilityRole
        restFacilityRoleMockMvc.perform(get("/api/facility-roles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFacilityRole() throws Exception {
        // Initialize the database
        facilityRoleRepository.saveAndFlush(facilityRole);

        int databaseSizeBeforeUpdate = facilityRoleRepository.findAll().size();

        // Update the facilityRole
        FacilityRole updatedFacilityRole = facilityRoleRepository.findById(facilityRole.getId()).get();
        // Disconnect from session so that the updates on updatedFacilityRole are not directly saved in db
        em.detach(updatedFacilityRole);

        restFacilityRoleMockMvc.perform(put("/api/facility-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFacilityRole)))
            .andExpect(status().isOk());

        // Validate the FacilityRole in the database
        List<FacilityRole> facilityRoleList = facilityRoleRepository.findAll();
        assertThat(facilityRoleList).hasSize(databaseSizeBeforeUpdate);
        FacilityRole testFacilityRole = facilityRoleList.get(facilityRoleList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingFacilityRole() throws Exception {
        int databaseSizeBeforeUpdate = facilityRoleRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFacilityRoleMockMvc.perform(put("/api/facility-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facilityRole)))
            .andExpect(status().isBadRequest());

        // Validate the FacilityRole in the database
        List<FacilityRole> facilityRoleList = facilityRoleRepository.findAll();
        assertThat(facilityRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFacilityRole() throws Exception {
        // Initialize the database
        facilityRoleRepository.saveAndFlush(facilityRole);

        int databaseSizeBeforeDelete = facilityRoleRepository.findAll().size();

        // Delete the facilityRole
        restFacilityRoleMockMvc.perform(delete("/api/facility-roles/{id}", facilityRole.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FacilityRole> facilityRoleList = facilityRoleRepository.findAll();
        assertThat(facilityRoleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
