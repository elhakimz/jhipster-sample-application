package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.WorkEffort;
import com.mycompany.myapp.repository.WorkEffortRepository;

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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.WorkEffortType;
/**
 * Integration tests for the {@link WorkEffortResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class WorkEffortResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_SCHEDULED_START = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SCHEDULED_START = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_SCHEDULED_COMPLETION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SCHEDULED_COMPLETION = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_TOTAL_MONEY_ALLOWED = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_MONEY_ALLOWED = new BigDecimal(2);

    private static final Double DEFAULT_TOTAL_HOURS_ALLOWED = 1D;
    private static final Double UPDATED_TOTAL_HOURS_ALLOWED = 2D;

    private static final Double DEFAULT_ESTIMATED_HOURS = 1D;
    private static final Double UPDATED_ESTIMATED_HOURS = 2D;

    private static final WorkEffortType DEFAULT_WORK_EFFORT_TYPE = WorkEffortType.PROGRAM;
    private static final WorkEffortType UPDATED_WORK_EFFORT_TYPE = WorkEffortType.PHASE;

    @Autowired
    private WorkEffortRepository workEffortRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkEffortMockMvc;

    private WorkEffort workEffort;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkEffort createEntity(EntityManager em) {
        WorkEffort workEffort = new WorkEffort()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .scheduledStart(DEFAULT_SCHEDULED_START)
            .scheduledCompletion(DEFAULT_SCHEDULED_COMPLETION)
            .totalMoneyAllowed(DEFAULT_TOTAL_MONEY_ALLOWED)
            .totalHoursAllowed(DEFAULT_TOTAL_HOURS_ALLOWED)
            .estimatedHours(DEFAULT_ESTIMATED_HOURS)
            .workEffortType(DEFAULT_WORK_EFFORT_TYPE);
        return workEffort;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkEffort createUpdatedEntity(EntityManager em) {
        WorkEffort workEffort = new WorkEffort()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .scheduledStart(UPDATED_SCHEDULED_START)
            .scheduledCompletion(UPDATED_SCHEDULED_COMPLETION)
            .totalMoneyAllowed(UPDATED_TOTAL_MONEY_ALLOWED)
            .totalHoursAllowed(UPDATED_TOTAL_HOURS_ALLOWED)
            .estimatedHours(UPDATED_ESTIMATED_HOURS)
            .workEffortType(UPDATED_WORK_EFFORT_TYPE);
        return workEffort;
    }

    @BeforeEach
    public void initTest() {
        workEffort = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkEffort() throws Exception {
        int databaseSizeBeforeCreate = workEffortRepository.findAll().size();
        // Create the WorkEffort
        restWorkEffortMockMvc.perform(post("/api/work-efforts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workEffort)))
            .andExpect(status().isCreated());

        // Validate the WorkEffort in the database
        List<WorkEffort> workEffortList = workEffortRepository.findAll();
        assertThat(workEffortList).hasSize(databaseSizeBeforeCreate + 1);
        WorkEffort testWorkEffort = workEffortList.get(workEffortList.size() - 1);
        assertThat(testWorkEffort.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testWorkEffort.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWorkEffort.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testWorkEffort.getScheduledStart()).isEqualTo(DEFAULT_SCHEDULED_START);
        assertThat(testWorkEffort.getScheduledCompletion()).isEqualTo(DEFAULT_SCHEDULED_COMPLETION);
        assertThat(testWorkEffort.getTotalMoneyAllowed()).isEqualTo(DEFAULT_TOTAL_MONEY_ALLOWED);
        assertThat(testWorkEffort.getTotalHoursAllowed()).isEqualTo(DEFAULT_TOTAL_HOURS_ALLOWED);
        assertThat(testWorkEffort.getEstimatedHours()).isEqualTo(DEFAULT_ESTIMATED_HOURS);
        assertThat(testWorkEffort.getWorkEffortType()).isEqualTo(DEFAULT_WORK_EFFORT_TYPE);
    }

    @Test
    @Transactional
    public void createWorkEffortWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workEffortRepository.findAll().size();

        // Create the WorkEffort with an existing ID
        workEffort.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkEffortMockMvc.perform(post("/api/work-efforts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workEffort)))
            .andExpect(status().isBadRequest());

        // Validate the WorkEffort in the database
        List<WorkEffort> workEffortList = workEffortRepository.findAll();
        assertThat(workEffortList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWorkEfforts() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList
        restWorkEffortMockMvc.perform(get("/api/work-efforts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workEffort.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].scheduledStart").value(hasItem(DEFAULT_SCHEDULED_START.toString())))
            .andExpect(jsonPath("$.[*].scheduledCompletion").value(hasItem(DEFAULT_SCHEDULED_COMPLETION.toString())))
            .andExpect(jsonPath("$.[*].totalMoneyAllowed").value(hasItem(DEFAULT_TOTAL_MONEY_ALLOWED.intValue())))
            .andExpect(jsonPath("$.[*].totalHoursAllowed").value(hasItem(DEFAULT_TOTAL_HOURS_ALLOWED.doubleValue())))
            .andExpect(jsonPath("$.[*].estimatedHours").value(hasItem(DEFAULT_ESTIMATED_HOURS.doubleValue())))
            .andExpect(jsonPath("$.[*].workEffortType").value(hasItem(DEFAULT_WORK_EFFORT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getWorkEffort() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get the workEffort
        restWorkEffortMockMvc.perform(get("/api/work-efforts/{id}", workEffort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workEffort.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.scheduledStart").value(DEFAULT_SCHEDULED_START.toString()))
            .andExpect(jsonPath("$.scheduledCompletion").value(DEFAULT_SCHEDULED_COMPLETION.toString()))
            .andExpect(jsonPath("$.totalMoneyAllowed").value(DEFAULT_TOTAL_MONEY_ALLOWED.intValue()))
            .andExpect(jsonPath("$.totalHoursAllowed").value(DEFAULT_TOTAL_HOURS_ALLOWED.doubleValue()))
            .andExpect(jsonPath("$.estimatedHours").value(DEFAULT_ESTIMATED_HOURS.doubleValue()))
            .andExpect(jsonPath("$.workEffortType").value(DEFAULT_WORK_EFFORT_TYPE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingWorkEffort() throws Exception {
        // Get the workEffort
        restWorkEffortMockMvc.perform(get("/api/work-efforts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkEffort() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        int databaseSizeBeforeUpdate = workEffortRepository.findAll().size();

        // Update the workEffort
        WorkEffort updatedWorkEffort = workEffortRepository.findById(workEffort.getId()).get();
        // Disconnect from session so that the updates on updatedWorkEffort are not directly saved in db
        em.detach(updatedWorkEffort);
        updatedWorkEffort
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .scheduledStart(UPDATED_SCHEDULED_START)
            .scheduledCompletion(UPDATED_SCHEDULED_COMPLETION)
            .totalMoneyAllowed(UPDATED_TOTAL_MONEY_ALLOWED)
            .totalHoursAllowed(UPDATED_TOTAL_HOURS_ALLOWED)
            .estimatedHours(UPDATED_ESTIMATED_HOURS)
            .workEffortType(UPDATED_WORK_EFFORT_TYPE);

        restWorkEffortMockMvc.perform(put("/api/work-efforts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedWorkEffort)))
            .andExpect(status().isOk());

        // Validate the WorkEffort in the database
        List<WorkEffort> workEffortList = workEffortRepository.findAll();
        assertThat(workEffortList).hasSize(databaseSizeBeforeUpdate);
        WorkEffort testWorkEffort = workEffortList.get(workEffortList.size() - 1);
        assertThat(testWorkEffort.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testWorkEffort.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWorkEffort.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testWorkEffort.getScheduledStart()).isEqualTo(UPDATED_SCHEDULED_START);
        assertThat(testWorkEffort.getScheduledCompletion()).isEqualTo(UPDATED_SCHEDULED_COMPLETION);
        assertThat(testWorkEffort.getTotalMoneyAllowed()).isEqualTo(UPDATED_TOTAL_MONEY_ALLOWED);
        assertThat(testWorkEffort.getTotalHoursAllowed()).isEqualTo(UPDATED_TOTAL_HOURS_ALLOWED);
        assertThat(testWorkEffort.getEstimatedHours()).isEqualTo(UPDATED_ESTIMATED_HOURS);
        assertThat(testWorkEffort.getWorkEffortType()).isEqualTo(UPDATED_WORK_EFFORT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkEffort() throws Exception {
        int databaseSizeBeforeUpdate = workEffortRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkEffortMockMvc.perform(put("/api/work-efforts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workEffort)))
            .andExpect(status().isBadRequest());

        // Validate the WorkEffort in the database
        List<WorkEffort> workEffortList = workEffortRepository.findAll();
        assertThat(workEffortList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWorkEffort() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        int databaseSizeBeforeDelete = workEffortRepository.findAll().size();

        // Delete the workEffort
        restWorkEffortMockMvc.perform(delete("/api/work-efforts/{id}", workEffort.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkEffort> workEffortList = workEffortRepository.findAll();
        assertThat(workEffortList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
