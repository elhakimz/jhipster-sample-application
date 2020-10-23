package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.CommEvtPposType;
import com.mycompany.myapp.repository.CommEvtPposTypeRepository;

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
 * Integration tests for the {@link CommEvtPposTypeResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CommEvtPposTypeResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CommEvtPposTypeRepository commEvtPposTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommEvtPposTypeMockMvc;

    private CommEvtPposType commEvtPposType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommEvtPposType createEntity(EntityManager em) {
        CommEvtPposType commEvtPposType = new CommEvtPposType()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION);
        return commEvtPposType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommEvtPposType createUpdatedEntity(EntityManager em) {
        CommEvtPposType commEvtPposType = new CommEvtPposType()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);
        return commEvtPposType;
    }

    @BeforeEach
    public void initTest() {
        commEvtPposType = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommEvtPposType() throws Exception {
        int databaseSizeBeforeCreate = commEvtPposTypeRepository.findAll().size();
        // Create the CommEvtPposType
        restCommEvtPposTypeMockMvc.perform(post("/api/comm-evt-ppos-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commEvtPposType)))
            .andExpect(status().isCreated());

        // Validate the CommEvtPposType in the database
        List<CommEvtPposType> commEvtPposTypeList = commEvtPposTypeRepository.findAll();
        assertThat(commEvtPposTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CommEvtPposType testCommEvtPposType = commEvtPposTypeList.get(commEvtPposTypeList.size() - 1);
        assertThat(testCommEvtPposType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCommEvtPposType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCommEvtPposTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commEvtPposTypeRepository.findAll().size();

        // Create the CommEvtPposType with an existing ID
        commEvtPposType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommEvtPposTypeMockMvc.perform(post("/api/comm-evt-ppos-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commEvtPposType)))
            .andExpect(status().isBadRequest());

        // Validate the CommEvtPposType in the database
        List<CommEvtPposType> commEvtPposTypeList = commEvtPposTypeRepository.findAll();
        assertThat(commEvtPposTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCommEvtPposTypes() throws Exception {
        // Initialize the database
        commEvtPposTypeRepository.saveAndFlush(commEvtPposType);

        // Get all the commEvtPposTypeList
        restCommEvtPposTypeMockMvc.perform(get("/api/comm-evt-ppos-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commEvtPposType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getCommEvtPposType() throws Exception {
        // Initialize the database
        commEvtPposTypeRepository.saveAndFlush(commEvtPposType);

        // Get the commEvtPposType
        restCommEvtPposTypeMockMvc.perform(get("/api/comm-evt-ppos-types/{id}", commEvtPposType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commEvtPposType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingCommEvtPposType() throws Exception {
        // Get the commEvtPposType
        restCommEvtPposTypeMockMvc.perform(get("/api/comm-evt-ppos-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommEvtPposType() throws Exception {
        // Initialize the database
        commEvtPposTypeRepository.saveAndFlush(commEvtPposType);

        int databaseSizeBeforeUpdate = commEvtPposTypeRepository.findAll().size();

        // Update the commEvtPposType
        CommEvtPposType updatedCommEvtPposType = commEvtPposTypeRepository.findById(commEvtPposType.getId()).get();
        // Disconnect from session so that the updates on updatedCommEvtPposType are not directly saved in db
        em.detach(updatedCommEvtPposType);
        updatedCommEvtPposType
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);

        restCommEvtPposTypeMockMvc.perform(put("/api/comm-evt-ppos-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommEvtPposType)))
            .andExpect(status().isOk());

        // Validate the CommEvtPposType in the database
        List<CommEvtPposType> commEvtPposTypeList = commEvtPposTypeRepository.findAll();
        assertThat(commEvtPposTypeList).hasSize(databaseSizeBeforeUpdate);
        CommEvtPposType testCommEvtPposType = commEvtPposTypeList.get(commEvtPposTypeList.size() - 1);
        assertThat(testCommEvtPposType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCommEvtPposType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCommEvtPposType() throws Exception {
        int databaseSizeBeforeUpdate = commEvtPposTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommEvtPposTypeMockMvc.perform(put("/api/comm-evt-ppos-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commEvtPposType)))
            .andExpect(status().isBadRequest());

        // Validate the CommEvtPposType in the database
        List<CommEvtPposType> commEvtPposTypeList = commEvtPposTypeRepository.findAll();
        assertThat(commEvtPposTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommEvtPposType() throws Exception {
        // Initialize the database
        commEvtPposTypeRepository.saveAndFlush(commEvtPposType);

        int databaseSizeBeforeDelete = commEvtPposTypeRepository.findAll().size();

        // Delete the commEvtPposType
        restCommEvtPposTypeMockMvc.perform(delete("/api/comm-evt-ppos-types/{id}", commEvtPposType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CommEvtPposType> commEvtPposTypeList = commEvtPposTypeRepository.findAll();
        assertThat(commEvtPposTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
