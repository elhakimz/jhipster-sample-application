package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.CasusRoleType;
import com.mycompany.myapp.repository.CasusRoleTypeRepository;

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
 * Integration tests for the {@link CasusRoleTypeResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CasusRoleTypeResourceIT {

    @Autowired
    private CasusRoleTypeRepository casusRoleTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCasusRoleTypeMockMvc;

    private CasusRoleType casusRoleType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CasusRoleType createEntity(EntityManager em) {
        CasusRoleType casusRoleType = new CasusRoleType();
        return casusRoleType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CasusRoleType createUpdatedEntity(EntityManager em) {
        CasusRoleType casusRoleType = new CasusRoleType();
        return casusRoleType;
    }

    @BeforeEach
    public void initTest() {
        casusRoleType = createEntity(em);
    }

    @Test
    @Transactional
    public void createCasusRoleType() throws Exception {
        int databaseSizeBeforeCreate = casusRoleTypeRepository.findAll().size();
        // Create the CasusRoleType
        restCasusRoleTypeMockMvc.perform(post("/api/casus-role-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(casusRoleType)))
            .andExpect(status().isCreated());

        // Validate the CasusRoleType in the database
        List<CasusRoleType> casusRoleTypeList = casusRoleTypeRepository.findAll();
        assertThat(casusRoleTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CasusRoleType testCasusRoleType = casusRoleTypeList.get(casusRoleTypeList.size() - 1);
    }

    @Test
    @Transactional
    public void createCasusRoleTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = casusRoleTypeRepository.findAll().size();

        // Create the CasusRoleType with an existing ID
        casusRoleType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCasusRoleTypeMockMvc.perform(post("/api/casus-role-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(casusRoleType)))
            .andExpect(status().isBadRequest());

        // Validate the CasusRoleType in the database
        List<CasusRoleType> casusRoleTypeList = casusRoleTypeRepository.findAll();
        assertThat(casusRoleTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCasusRoleTypes() throws Exception {
        // Initialize the database
        casusRoleTypeRepository.saveAndFlush(casusRoleType);

        // Get all the casusRoleTypeList
        restCasusRoleTypeMockMvc.perform(get("/api/casus-role-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(casusRoleType.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getCasusRoleType() throws Exception {
        // Initialize the database
        casusRoleTypeRepository.saveAndFlush(casusRoleType);

        // Get the casusRoleType
        restCasusRoleTypeMockMvc.perform(get("/api/casus-role-types/{id}", casusRoleType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(casusRoleType.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingCasusRoleType() throws Exception {
        // Get the casusRoleType
        restCasusRoleTypeMockMvc.perform(get("/api/casus-role-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCasusRoleType() throws Exception {
        // Initialize the database
        casusRoleTypeRepository.saveAndFlush(casusRoleType);

        int databaseSizeBeforeUpdate = casusRoleTypeRepository.findAll().size();

        // Update the casusRoleType
        CasusRoleType updatedCasusRoleType = casusRoleTypeRepository.findById(casusRoleType.getId()).get();
        // Disconnect from session so that the updates on updatedCasusRoleType are not directly saved in db
        em.detach(updatedCasusRoleType);

        restCasusRoleTypeMockMvc.perform(put("/api/casus-role-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCasusRoleType)))
            .andExpect(status().isOk());

        // Validate the CasusRoleType in the database
        List<CasusRoleType> casusRoleTypeList = casusRoleTypeRepository.findAll();
        assertThat(casusRoleTypeList).hasSize(databaseSizeBeforeUpdate);
        CasusRoleType testCasusRoleType = casusRoleTypeList.get(casusRoleTypeList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingCasusRoleType() throws Exception {
        int databaseSizeBeforeUpdate = casusRoleTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCasusRoleTypeMockMvc.perform(put("/api/casus-role-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(casusRoleType)))
            .andExpect(status().isBadRequest());

        // Validate the CasusRoleType in the database
        List<CasusRoleType> casusRoleTypeList = casusRoleTypeRepository.findAll();
        assertThat(casusRoleTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCasusRoleType() throws Exception {
        // Initialize the database
        casusRoleTypeRepository.saveAndFlush(casusRoleType);

        int databaseSizeBeforeDelete = casusRoleTypeRepository.findAll().size();

        // Delete the casusRoleType
        restCasusRoleTypeMockMvc.perform(delete("/api/casus-role-types/{id}", casusRoleType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CasusRoleType> casusRoleTypeList = casusRoleTypeRepository.findAll();
        assertThat(casusRoleTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
