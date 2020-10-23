package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.CommWorkEffort;
import com.mycompany.myapp.repository.CommWorkEffortRepository;

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
 * Integration tests for the {@link CommWorkEffortResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CommWorkEffortResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CommWorkEffortRepository commWorkEffortRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommWorkEffortMockMvc;

    private CommWorkEffort commWorkEffort;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommWorkEffort createEntity(EntityManager em) {
        CommWorkEffort commWorkEffort = new CommWorkEffort()
            .description(DEFAULT_DESCRIPTION);
        return commWorkEffort;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommWorkEffort createUpdatedEntity(EntityManager em) {
        CommWorkEffort commWorkEffort = new CommWorkEffort()
            .description(UPDATED_DESCRIPTION);
        return commWorkEffort;
    }

    @BeforeEach
    public void initTest() {
        commWorkEffort = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommWorkEffort() throws Exception {
        int databaseSizeBeforeCreate = commWorkEffortRepository.findAll().size();
        // Create the CommWorkEffort
        restCommWorkEffortMockMvc.perform(post("/api/comm-work-efforts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commWorkEffort)))
            .andExpect(status().isCreated());

        // Validate the CommWorkEffort in the database
        List<CommWorkEffort> commWorkEffortList = commWorkEffortRepository.findAll();
        assertThat(commWorkEffortList).hasSize(databaseSizeBeforeCreate + 1);
        CommWorkEffort testCommWorkEffort = commWorkEffortList.get(commWorkEffortList.size() - 1);
        assertThat(testCommWorkEffort.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCommWorkEffortWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commWorkEffortRepository.findAll().size();

        // Create the CommWorkEffort with an existing ID
        commWorkEffort.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommWorkEffortMockMvc.perform(post("/api/comm-work-efforts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commWorkEffort)))
            .andExpect(status().isBadRequest());

        // Validate the CommWorkEffort in the database
        List<CommWorkEffort> commWorkEffortList = commWorkEffortRepository.findAll();
        assertThat(commWorkEffortList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCommWorkEfforts() throws Exception {
        // Initialize the database
        commWorkEffortRepository.saveAndFlush(commWorkEffort);

        // Get all the commWorkEffortList
        restCommWorkEffortMockMvc.perform(get("/api/comm-work-efforts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commWorkEffort.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getCommWorkEffort() throws Exception {
        // Initialize the database
        commWorkEffortRepository.saveAndFlush(commWorkEffort);

        // Get the commWorkEffort
        restCommWorkEffortMockMvc.perform(get("/api/comm-work-efforts/{id}", commWorkEffort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commWorkEffort.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingCommWorkEffort() throws Exception {
        // Get the commWorkEffort
        restCommWorkEffortMockMvc.perform(get("/api/comm-work-efforts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommWorkEffort() throws Exception {
        // Initialize the database
        commWorkEffortRepository.saveAndFlush(commWorkEffort);

        int databaseSizeBeforeUpdate = commWorkEffortRepository.findAll().size();

        // Update the commWorkEffort
        CommWorkEffort updatedCommWorkEffort = commWorkEffortRepository.findById(commWorkEffort.getId()).get();
        // Disconnect from session so that the updates on updatedCommWorkEffort are not directly saved in db
        em.detach(updatedCommWorkEffort);
        updatedCommWorkEffort
            .description(UPDATED_DESCRIPTION);

        restCommWorkEffortMockMvc.perform(put("/api/comm-work-efforts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommWorkEffort)))
            .andExpect(status().isOk());

        // Validate the CommWorkEffort in the database
        List<CommWorkEffort> commWorkEffortList = commWorkEffortRepository.findAll();
        assertThat(commWorkEffortList).hasSize(databaseSizeBeforeUpdate);
        CommWorkEffort testCommWorkEffort = commWorkEffortList.get(commWorkEffortList.size() - 1);
        assertThat(testCommWorkEffort.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCommWorkEffort() throws Exception {
        int databaseSizeBeforeUpdate = commWorkEffortRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommWorkEffortMockMvc.perform(put("/api/comm-work-efforts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commWorkEffort)))
            .andExpect(status().isBadRequest());

        // Validate the CommWorkEffort in the database
        List<CommWorkEffort> commWorkEffortList = commWorkEffortRepository.findAll();
        assertThat(commWorkEffortList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommWorkEffort() throws Exception {
        // Initialize the database
        commWorkEffortRepository.saveAndFlush(commWorkEffort);

        int databaseSizeBeforeDelete = commWorkEffortRepository.findAll().size();

        // Delete the commWorkEffort
        restCommWorkEffortMockMvc.perform(delete("/api/comm-work-efforts/{id}", commWorkEffort.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CommWorkEffort> commWorkEffortList = commWorkEffortRepository.findAll();
        assertThat(commWorkEffortList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
