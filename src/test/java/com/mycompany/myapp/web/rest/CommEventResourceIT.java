package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.CommEvent;
import com.mycompany.myapp.repository.CommEventRepository;

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

/**
 * Integration tests for the {@link CommEventResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CommEventResourceIT {

    private static final String DEFAULT_EVENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_STARTED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_STARTED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_ENDED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ENDED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private CommEventRepository commEventRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommEventMockMvc;

    private CommEvent commEvent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommEvent createEntity(EntityManager em) {
        CommEvent commEvent = new CommEvent()
            .eventId(DEFAULT_EVENT_ID)
            .started(DEFAULT_STARTED)
            .ended(DEFAULT_ENDED)
            .note(DEFAULT_NOTE);
        return commEvent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommEvent createUpdatedEntity(EntityManager em) {
        CommEvent commEvent = new CommEvent()
            .eventId(UPDATED_EVENT_ID)
            .started(UPDATED_STARTED)
            .ended(UPDATED_ENDED)
            .note(UPDATED_NOTE);
        return commEvent;
    }

    @BeforeEach
    public void initTest() {
        commEvent = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommEvent() throws Exception {
        int databaseSizeBeforeCreate = commEventRepository.findAll().size();
        // Create the CommEvent
        restCommEventMockMvc.perform(post("/api/comm-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commEvent)))
            .andExpect(status().isCreated());

        // Validate the CommEvent in the database
        List<CommEvent> commEventList = commEventRepository.findAll();
        assertThat(commEventList).hasSize(databaseSizeBeforeCreate + 1);
        CommEvent testCommEvent = commEventList.get(commEventList.size() - 1);
        assertThat(testCommEvent.getEventId()).isEqualTo(DEFAULT_EVENT_ID);
        assertThat(testCommEvent.getStarted()).isEqualTo(DEFAULT_STARTED);
        assertThat(testCommEvent.getEnded()).isEqualTo(DEFAULT_ENDED);
        assertThat(testCommEvent.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createCommEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commEventRepository.findAll().size();

        // Create the CommEvent with an existing ID
        commEvent.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommEventMockMvc.perform(post("/api/comm-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commEvent)))
            .andExpect(status().isBadRequest());

        // Validate the CommEvent in the database
        List<CommEvent> commEventList = commEventRepository.findAll();
        assertThat(commEventList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCommEvents() throws Exception {
        // Initialize the database
        commEventRepository.saveAndFlush(commEvent);

        // Get all the commEventList
        restCommEventMockMvc.perform(get("/api/comm-events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].eventId").value(hasItem(DEFAULT_EVENT_ID)))
            .andExpect(jsonPath("$.[*].started").value(hasItem(sameInstant(DEFAULT_STARTED))))
            .andExpect(jsonPath("$.[*].ended").value(hasItem(sameInstant(DEFAULT_ENDED))))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));
    }
    
    @Test
    @Transactional
    public void getCommEvent() throws Exception {
        // Initialize the database
        commEventRepository.saveAndFlush(commEvent);

        // Get the commEvent
        restCommEventMockMvc.perform(get("/api/comm-events/{id}", commEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commEvent.getId().intValue()))
            .andExpect(jsonPath("$.eventId").value(DEFAULT_EVENT_ID))
            .andExpect(jsonPath("$.started").value(sameInstant(DEFAULT_STARTED)))
            .andExpect(jsonPath("$.ended").value(sameInstant(DEFAULT_ENDED)))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE));
    }
    @Test
    @Transactional
    public void getNonExistingCommEvent() throws Exception {
        // Get the commEvent
        restCommEventMockMvc.perform(get("/api/comm-events/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommEvent() throws Exception {
        // Initialize the database
        commEventRepository.saveAndFlush(commEvent);

        int databaseSizeBeforeUpdate = commEventRepository.findAll().size();

        // Update the commEvent
        CommEvent updatedCommEvent = commEventRepository.findById(commEvent.getId()).get();
        // Disconnect from session so that the updates on updatedCommEvent are not directly saved in db
        em.detach(updatedCommEvent);
        updatedCommEvent
            .eventId(UPDATED_EVENT_ID)
            .started(UPDATED_STARTED)
            .ended(UPDATED_ENDED)
            .note(UPDATED_NOTE);

        restCommEventMockMvc.perform(put("/api/comm-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommEvent)))
            .andExpect(status().isOk());

        // Validate the CommEvent in the database
        List<CommEvent> commEventList = commEventRepository.findAll();
        assertThat(commEventList).hasSize(databaseSizeBeforeUpdate);
        CommEvent testCommEvent = commEventList.get(commEventList.size() - 1);
        assertThat(testCommEvent.getEventId()).isEqualTo(UPDATED_EVENT_ID);
        assertThat(testCommEvent.getStarted()).isEqualTo(UPDATED_STARTED);
        assertThat(testCommEvent.getEnded()).isEqualTo(UPDATED_ENDED);
        assertThat(testCommEvent.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingCommEvent() throws Exception {
        int databaseSizeBeforeUpdate = commEventRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommEventMockMvc.perform(put("/api/comm-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commEvent)))
            .andExpect(status().isBadRequest());

        // Validate the CommEvent in the database
        List<CommEvent> commEventList = commEventRepository.findAll();
        assertThat(commEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommEvent() throws Exception {
        // Initialize the database
        commEventRepository.saveAndFlush(commEvent);

        int databaseSizeBeforeDelete = commEventRepository.findAll().size();

        // Delete the commEvent
        restCommEventMockMvc.perform(delete("/api/comm-events/{id}", commEvent.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CommEvent> commEventList = commEventRepository.findAll();
        assertThat(commEventList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
