package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.CommEventPurpose;
import com.mycompany.myapp.repository.CommEventPurposeRepository;

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
 * Integration tests for the {@link CommEventPurposeResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CommEventPurposeResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CommEventPurposeRepository commEventPurposeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommEventPurposeMockMvc;

    private CommEventPurpose commEventPurpose;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommEventPurpose createEntity(EntityManager em) {
        CommEventPurpose commEventPurpose = new CommEventPurpose()
            .description(DEFAULT_DESCRIPTION);
        return commEventPurpose;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommEventPurpose createUpdatedEntity(EntityManager em) {
        CommEventPurpose commEventPurpose = new CommEventPurpose()
            .description(UPDATED_DESCRIPTION);
        return commEventPurpose;
    }

    @BeforeEach
    public void initTest() {
        commEventPurpose = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommEventPurpose() throws Exception {
        int databaseSizeBeforeCreate = commEventPurposeRepository.findAll().size();
        // Create the CommEventPurpose
        restCommEventPurposeMockMvc.perform(post("/api/comm-event-purposes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commEventPurpose)))
            .andExpect(status().isCreated());

        // Validate the CommEventPurpose in the database
        List<CommEventPurpose> commEventPurposeList = commEventPurposeRepository.findAll();
        assertThat(commEventPurposeList).hasSize(databaseSizeBeforeCreate + 1);
        CommEventPurpose testCommEventPurpose = commEventPurposeList.get(commEventPurposeList.size() - 1);
        assertThat(testCommEventPurpose.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCommEventPurposeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commEventPurposeRepository.findAll().size();

        // Create the CommEventPurpose with an existing ID
        commEventPurpose.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommEventPurposeMockMvc.perform(post("/api/comm-event-purposes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commEventPurpose)))
            .andExpect(status().isBadRequest());

        // Validate the CommEventPurpose in the database
        List<CommEventPurpose> commEventPurposeList = commEventPurposeRepository.findAll();
        assertThat(commEventPurposeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCommEventPurposes() throws Exception {
        // Initialize the database
        commEventPurposeRepository.saveAndFlush(commEventPurpose);

        // Get all the commEventPurposeList
        restCommEventPurposeMockMvc.perform(get("/api/comm-event-purposes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commEventPurpose.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getCommEventPurpose() throws Exception {
        // Initialize the database
        commEventPurposeRepository.saveAndFlush(commEventPurpose);

        // Get the commEventPurpose
        restCommEventPurposeMockMvc.perform(get("/api/comm-event-purposes/{id}", commEventPurpose.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commEventPurpose.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingCommEventPurpose() throws Exception {
        // Get the commEventPurpose
        restCommEventPurposeMockMvc.perform(get("/api/comm-event-purposes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommEventPurpose() throws Exception {
        // Initialize the database
        commEventPurposeRepository.saveAndFlush(commEventPurpose);

        int databaseSizeBeforeUpdate = commEventPurposeRepository.findAll().size();

        // Update the commEventPurpose
        CommEventPurpose updatedCommEventPurpose = commEventPurposeRepository.findById(commEventPurpose.getId()).get();
        // Disconnect from session so that the updates on updatedCommEventPurpose are not directly saved in db
        em.detach(updatedCommEventPurpose);
        updatedCommEventPurpose
            .description(UPDATED_DESCRIPTION);

        restCommEventPurposeMockMvc.perform(put("/api/comm-event-purposes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommEventPurpose)))
            .andExpect(status().isOk());

        // Validate the CommEventPurpose in the database
        List<CommEventPurpose> commEventPurposeList = commEventPurposeRepository.findAll();
        assertThat(commEventPurposeList).hasSize(databaseSizeBeforeUpdate);
        CommEventPurpose testCommEventPurpose = commEventPurposeList.get(commEventPurposeList.size() - 1);
        assertThat(testCommEventPurpose.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCommEventPurpose() throws Exception {
        int databaseSizeBeforeUpdate = commEventPurposeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommEventPurposeMockMvc.perform(put("/api/comm-event-purposes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commEventPurpose)))
            .andExpect(status().isBadRequest());

        // Validate the CommEventPurpose in the database
        List<CommEventPurpose> commEventPurposeList = commEventPurposeRepository.findAll();
        assertThat(commEventPurposeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommEventPurpose() throws Exception {
        // Initialize the database
        commEventPurposeRepository.saveAndFlush(commEventPurpose);

        int databaseSizeBeforeDelete = commEventPurposeRepository.findAll().size();

        // Delete the commEventPurpose
        restCommEventPurposeMockMvc.perform(delete("/api/comm-event-purposes/{id}", commEventPurpose.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CommEventPurpose> commEventPurposeList = commEventPurposeRepository.findAll();
        assertThat(commEventPurposeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
