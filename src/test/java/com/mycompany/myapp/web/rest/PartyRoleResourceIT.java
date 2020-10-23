package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.PartyRole;
import com.mycompany.myapp.repository.PartyRoleRepository;

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
 * Integration tests for the {@link PartyRoleResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PartyRoleResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private PartyRoleRepository partyRoleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartyRoleMockMvc;

    private PartyRole partyRole;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyRole createEntity(EntityManager em) {
        PartyRole partyRole = new PartyRole()
            .name(DEFAULT_NAME);
        return partyRole;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyRole createUpdatedEntity(EntityManager em) {
        PartyRole partyRole = new PartyRole()
            .name(UPDATED_NAME);
        return partyRole;
    }

    @BeforeEach
    public void initTest() {
        partyRole = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartyRole() throws Exception {
        int databaseSizeBeforeCreate = partyRoleRepository.findAll().size();
        // Create the PartyRole
        restPartyRoleMockMvc.perform(post("/api/party-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyRole)))
            .andExpect(status().isCreated());

        // Validate the PartyRole in the database
        List<PartyRole> partyRoleList = partyRoleRepository.findAll();
        assertThat(partyRoleList).hasSize(databaseSizeBeforeCreate + 1);
        PartyRole testPartyRole = partyRoleList.get(partyRoleList.size() - 1);
        assertThat(testPartyRole.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createPartyRoleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partyRoleRepository.findAll().size();

        // Create the PartyRole with an existing ID
        partyRole.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyRoleMockMvc.perform(post("/api/party-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyRole)))
            .andExpect(status().isBadRequest());

        // Validate the PartyRole in the database
        List<PartyRole> partyRoleList = partyRoleRepository.findAll();
        assertThat(partyRoleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = partyRoleRepository.findAll().size();
        // set the field null
        partyRole.setName(null);

        // Create the PartyRole, which fails.


        restPartyRoleMockMvc.perform(post("/api/party-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyRole)))
            .andExpect(status().isBadRequest());

        List<PartyRole> partyRoleList = partyRoleRepository.findAll();
        assertThat(partyRoleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPartyRoles() throws Exception {
        // Initialize the database
        partyRoleRepository.saveAndFlush(partyRole);

        // Get all the partyRoleList
        restPartyRoleMockMvc.perform(get("/api/party-roles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyRole.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getPartyRole() throws Exception {
        // Initialize the database
        partyRoleRepository.saveAndFlush(partyRole);

        // Get the partyRole
        restPartyRoleMockMvc.perform(get("/api/party-roles/{id}", partyRole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partyRole.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingPartyRole() throws Exception {
        // Get the partyRole
        restPartyRoleMockMvc.perform(get("/api/party-roles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartyRole() throws Exception {
        // Initialize the database
        partyRoleRepository.saveAndFlush(partyRole);

        int databaseSizeBeforeUpdate = partyRoleRepository.findAll().size();

        // Update the partyRole
        PartyRole updatedPartyRole = partyRoleRepository.findById(partyRole.getId()).get();
        // Disconnect from session so that the updates on updatedPartyRole are not directly saved in db
        em.detach(updatedPartyRole);
        updatedPartyRole
            .name(UPDATED_NAME);

        restPartyRoleMockMvc.perform(put("/api/party-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPartyRole)))
            .andExpect(status().isOk());

        // Validate the PartyRole in the database
        List<PartyRole> partyRoleList = partyRoleRepository.findAll();
        assertThat(partyRoleList).hasSize(databaseSizeBeforeUpdate);
        PartyRole testPartyRole = partyRoleList.get(partyRoleList.size() - 1);
        assertThat(testPartyRole.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPartyRole() throws Exception {
        int databaseSizeBeforeUpdate = partyRoleRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyRoleMockMvc.perform(put("/api/party-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyRole)))
            .andExpect(status().isBadRequest());

        // Validate the PartyRole in the database
        List<PartyRole> partyRoleList = partyRoleRepository.findAll();
        assertThat(partyRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePartyRole() throws Exception {
        // Initialize the database
        partyRoleRepository.saveAndFlush(partyRole);

        int databaseSizeBeforeDelete = partyRoleRepository.findAll().size();

        // Delete the partyRole
        restPartyRoleMockMvc.perform(delete("/api/party-roles/{id}", partyRole.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartyRole> partyRoleList = partyRoleRepository.findAll();
        assertThat(partyRoleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
