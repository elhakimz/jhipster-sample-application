package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.WorkEffort;
import com.mycompany.myapp.repository.WorkEffortRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.WorkEffort}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class WorkEffortResource {

    private final Logger log = LoggerFactory.getLogger(WorkEffortResource.class);

    private static final String ENTITY_NAME = "workEffort";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkEffortRepository workEffortRepository;

    public WorkEffortResource(WorkEffortRepository workEffortRepository) {
        this.workEffortRepository = workEffortRepository;
    }

    /**
     * {@code POST  /work-efforts} : Create a new workEffort.
     *
     * @param workEffort the workEffort to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workEffort, or with status {@code 400 (Bad Request)} if the workEffort has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/work-efforts")
    public ResponseEntity<WorkEffort> createWorkEffort(@RequestBody WorkEffort workEffort) throws URISyntaxException {
        log.debug("REST request to save WorkEffort : {}", workEffort);
        if (workEffort.getId() != null) {
            throw new BadRequestAlertException("A new workEffort cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkEffort result = workEffortRepository.save(workEffort);
        return ResponseEntity.created(new URI("/api/work-efforts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /work-efforts} : Updates an existing workEffort.
     *
     * @param workEffort the workEffort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workEffort,
     * or with status {@code 400 (Bad Request)} if the workEffort is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workEffort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/work-efforts")
    public ResponseEntity<WorkEffort> updateWorkEffort(@RequestBody WorkEffort workEffort) throws URISyntaxException {
        log.debug("REST request to update WorkEffort : {}", workEffort);
        if (workEffort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WorkEffort result = workEffortRepository.save(workEffort);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workEffort.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /work-efforts} : get all the workEfforts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workEfforts in body.
     */
    @GetMapping("/work-efforts")
    public List<WorkEffort> getAllWorkEfforts() {
        log.debug("REST request to get all WorkEfforts");
        return workEffortRepository.findAll();
    }

    /**
     * {@code GET  /work-efforts/:id} : get the "id" workEffort.
     *
     * @param id the id of the workEffort to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workEffort, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/work-efforts/{id}")
    public ResponseEntity<WorkEffort> getWorkEffort(@PathVariable Long id) {
        log.debug("REST request to get WorkEffort : {}", id);
        Optional<WorkEffort> workEffort = workEffortRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(workEffort);
    }

    /**
     * {@code DELETE  /work-efforts/:id} : delete the "id" workEffort.
     *
     * @param id the id of the workEffort to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/work-efforts/{id}")
    public ResponseEntity<Void> deleteWorkEffort(@PathVariable Long id) {
        log.debug("REST request to delete WorkEffort : {}", id);
        workEffortRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
