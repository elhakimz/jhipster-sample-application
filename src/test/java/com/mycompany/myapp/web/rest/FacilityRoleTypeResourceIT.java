package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.FacilityRoleType;
import com.mycompany.myapp.repository.FacilityRoleTypeRepository;

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
 * Integration tests for the {@link FacilityRoleTypeResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FacilityRoleTypeResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private FacilityRoleTypeRepository facilityRoleTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFacilityRoleTypeMockMvc;

    private FacilityRoleType facilityRoleType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FacilityRoleType createEntity(EntityManager em) {
        FacilityRoleType facilityRoleType = new FacilityRoleType()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION);
        return facilityRoleType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FacilityRoleType createUpdatedEntity(EntityManager em) {
        FacilityRoleType facilityRoleType = new FacilityRoleType()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);
        return facilityRoleType;
    }

    @BeforeEach
    public void initTest() {
        facilityRoleType = createEntity(em);
    }

    @Test
    @Transactional
    public void createFacilityRoleType() throws Exception {
        int databaseSizeBeforeCreate = facilityRoleTypeRepository.findAll().size();
        // Create the FacilityRoleType
        restFacilityRoleTypeMockMvc.perform(post("/api/facility-role-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facilityRoleType)))
            .andExpect(status().isCreated());

        // Validate the FacilityRoleType in the database
        List<FacilityRoleType> facilityRoleTypeList = facilityRoleTypeRepository.findAll();
        assertThat(facilityRoleTypeList).hasSize(databaseSizeBeforeCreate + 1);
        FacilityRoleType testFacilityRoleType = facilityRoleTypeList.get(facilityRoleTypeList.size() - 1);
        assertThat(testFacilityRoleType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testFacilityRoleType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createFacilityRoleTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = facilityRoleTypeRepository.findAll().size();

        // Create the FacilityRoleType with an existing ID
        facilityRoleType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFacilityRoleTypeMockMvc.perform(post("/api/facility-role-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facilityRoleType)))
            .andExpect(status().isBadRequest());

        // Validate the FacilityRoleType in the database
        List<FacilityRoleType> facilityRoleTypeList = facilityRoleTypeRepository.findAll();
        assertThat(facilityRoleTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFacilityRoleTypes() throws Exception {
        // Initialize the database
        facilityRoleTypeRepository.saveAndFlush(facilityRoleType);

        // Get all the facilityRoleTypeList
        restFacilityRoleTypeMockMvc.perform(get("/api/facility-role-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facilityRoleType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getFacilityRoleType() throws Exception {
        // Initialize the database
        facilityRoleTypeRepository.saveAndFlush(facilityRoleType);

        // Get the facilityRoleType
        restFacilityRoleTypeMockMvc.perform(get("/api/facility-role-types/{id}", facilityRoleType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(facilityRoleType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingFacilityRoleType() throws Exception {
        // Get the facilityRoleType
        restFacilityRoleTypeMockMvc.perform(get("/api/facility-role-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFacilityRoleType() throws Exception {
        // Initialize the database
        facilityRoleTypeRepository.saveAndFlush(facilityRoleType);

        int databaseSizeBeforeUpdate = facilityRoleTypeRepository.findAll().size();

        // Update the facilityRoleType
        FacilityRoleType updatedFacilityRoleType = facilityRoleTypeRepository.findById(facilityRoleType.getId()).get();
        // Disconnect from session so that the updates on updatedFacilityRoleType are not directly saved in db
        em.detach(updatedFacilityRoleType);
        updatedFacilityRoleType
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);

        restFacilityRoleTypeMockMvc.perform(put("/api/facility-role-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFacilityRoleType)))
            .andExpect(status().isOk());

        // Validate the FacilityRoleType in the database
        List<FacilityRoleType> facilityRoleTypeList = facilityRoleTypeRepository.findAll();
        assertThat(facilityRoleTypeList).hasSize(databaseSizeBeforeUpdate);
        FacilityRoleType testFacilityRoleType = facilityRoleTypeList.get(facilityRoleTypeList.size() - 1);
        assertThat(testFacilityRoleType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFacilityRoleType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingFacilityRoleType() throws Exception {
        int databaseSizeBeforeUpdate = facilityRoleTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFacilityRoleTypeMockMvc.perform(put("/api/facility-role-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facilityRoleType)))
            .andExpect(status().isBadRequest());

        // Validate the FacilityRoleType in the database
        List<FacilityRoleType> facilityRoleTypeList = facilityRoleTypeRepository.findAll();
        assertThat(facilityRoleTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFacilityRoleType() throws Exception {
        // Initialize the database
        facilityRoleTypeRepository.saveAndFlush(facilityRoleType);

        int databaseSizeBeforeDelete = facilityRoleTypeRepository.findAll().size();

        // Delete the facilityRoleType
        restFacilityRoleTypeMockMvc.perform(delete("/api/facility-role-types/{id}", facilityRoleType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FacilityRoleType> facilityRoleTypeList = facilityRoleTypeRepository.findAll();
        assertThat(facilityRoleTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
