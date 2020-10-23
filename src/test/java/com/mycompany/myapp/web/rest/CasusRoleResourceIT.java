package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.CasusRole;
import com.mycompany.myapp.repository.CasusRoleRepository;

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
 * Integration tests for the {@link CasusRoleResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CasusRoleResourceIT {

    @Autowired
    private CasusRoleRepository casusRoleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCasusRoleMockMvc;

    private CasusRole casusRole;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CasusRole createEntity(EntityManager em) {
        CasusRole casusRole = new CasusRole();
        return casusRole;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CasusRole createUpdatedEntity(EntityManager em) {
        CasusRole casusRole = new CasusRole();
        return casusRole;
    }

    @BeforeEach
    public void initTest() {
        casusRole = createEntity(em);
    }

    @Test
    @Transactional
    public void createCasusRole() throws Exception {
        int databaseSizeBeforeCreate = casusRoleRepository.findAll().size();
        // Create the CasusRole
        restCasusRoleMockMvc.perform(post("/api/casus-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(casusRole)))
            .andExpect(status().isCreated());

        // Validate the CasusRole in the database
        List<CasusRole> casusRoleList = casusRoleRepository.findAll();
        assertThat(casusRoleList).hasSize(databaseSizeBeforeCreate + 1);
        CasusRole testCasusRole = casusRoleList.get(casusRoleList.size() - 1);
    }

    @Test
    @Transactional
    public void createCasusRoleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = casusRoleRepository.findAll().size();

        // Create the CasusRole with an existing ID
        casusRole.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCasusRoleMockMvc.perform(post("/api/casus-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(casusRole)))
            .andExpect(status().isBadRequest());

        // Validate the CasusRole in the database
        List<CasusRole> casusRoleList = casusRoleRepository.findAll();
        assertThat(casusRoleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCasusRoles() throws Exception {
        // Initialize the database
        casusRoleRepository.saveAndFlush(casusRole);

        // Get all the casusRoleList
        restCasusRoleMockMvc.perform(get("/api/casus-roles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(casusRole.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getCasusRole() throws Exception {
        // Initialize the database
        casusRoleRepository.saveAndFlush(casusRole);

        // Get the casusRole
        restCasusRoleMockMvc.perform(get("/api/casus-roles/{id}", casusRole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(casusRole.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingCasusRole() throws Exception {
        // Get the casusRole
        restCasusRoleMockMvc.perform(get("/api/casus-roles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCasusRole() throws Exception {
        // Initialize the database
        casusRoleRepository.saveAndFlush(casusRole);

        int databaseSizeBeforeUpdate = casusRoleRepository.findAll().size();

        // Update the casusRole
        CasusRole updatedCasusRole = casusRoleRepository.findById(casusRole.getId()).get();
        // Disconnect from session so that the updates on updatedCasusRole are not directly saved in db
        em.detach(updatedCasusRole);

        restCasusRoleMockMvc.perform(put("/api/casus-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCasusRole)))
            .andExpect(status().isOk());

        // Validate the CasusRole in the database
        List<CasusRole> casusRoleList = casusRoleRepository.findAll();
        assertThat(casusRoleList).hasSize(databaseSizeBeforeUpdate);
        CasusRole testCasusRole = casusRoleList.get(casusRoleList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingCasusRole() throws Exception {
        int databaseSizeBeforeUpdate = casusRoleRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCasusRoleMockMvc.perform(put("/api/casus-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(casusRole)))
            .andExpect(status().isBadRequest());

        // Validate the CasusRole in the database
        List<CasusRole> casusRoleList = casusRoleRepository.findAll();
        assertThat(casusRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCasusRole() throws Exception {
        // Initialize the database
        casusRoleRepository.saveAndFlush(casusRole);

        int databaseSizeBeforeDelete = casusRoleRepository.findAll().size();

        // Delete the casusRole
        restCasusRoleMockMvc.perform(delete("/api/casus-roles/{id}", casusRole.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CasusRole> casusRoleList = casusRoleRepository.findAll();
        assertThat(casusRoleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
