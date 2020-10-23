package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.CommWorkEffort;
import com.mycompany.myapp.repository.CommWorkEffortRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CommWorkEffort}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CommWorkEffortResource {

    private final Logger log = LoggerFactory.getLogger(CommWorkEffortResource.class);

    private static final String ENTITY_NAME = "commWorkEffort";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommWorkEffortRepository commWorkEffortRepository;

    public CommWorkEffortResource(CommWorkEffortRepository commWorkEffortRepository) {
        this.commWorkEffortRepository = commWorkEffortRepository;
    }

    /**
     * {@code POST  /comm-work-efforts} : Create a new commWorkEffort.
     *
     * @param commWorkEffort the commWorkEffort to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commWorkEffort, or with status {@code 400 (Bad Request)} if the commWorkEffort has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/comm-work-efforts")
    public ResponseEntity<CommWorkEffort> createCommWorkEffort(@RequestBody CommWorkEffort commWorkEffort) throws URISyntaxException {
        log.debug("REST request to save CommWorkEffort : {}", commWorkEffort);
        if (commWorkEffort.getId() != null) {
            throw new BadRequestAlertException("A new commWorkEffort cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommWorkEffort result = commWorkEffortRepository.save(commWorkEffort);
        return ResponseEntity.created(new URI("/api/comm-work-efforts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /comm-work-efforts} : Updates an existing commWorkEffort.
     *
     * @param commWorkEffort the commWorkEffort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commWorkEffort,
     * or with status {@code 400 (Bad Request)} if the commWorkEffort is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commWorkEffort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/comm-work-efforts")
    public ResponseEntity<CommWorkEffort> updateCommWorkEffort(@RequestBody CommWorkEffort commWorkEffort) throws URISyntaxException {
        log.debug("REST request to update CommWorkEffort : {}", commWorkEffort);
        if (commWorkEffort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommWorkEffort result = commWorkEffortRepository.save(commWorkEffort);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commWorkEffort.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /comm-work-efforts} : get all the commWorkEfforts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commWorkEfforts in body.
     */
    @GetMapping("/comm-work-efforts")
    public List<CommWorkEffort> getAllCommWorkEfforts() {
        log.debug("REST request to get all CommWorkEfforts");
        return commWorkEffortRepository.findAll();
    }

    /**
     * {@code GET  /comm-work-efforts/:id} : get the "id" commWorkEffort.
     *
     * @param id the id of the commWorkEffort to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commWorkEffort, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/comm-work-efforts/{id}")
    public ResponseEntity<CommWorkEffort> getCommWorkEffort(@PathVariable Long id) {
        log.debug("REST request to get CommWorkEffort : {}", id);
        Optional<CommWorkEffort> commWorkEffort = commWorkEffortRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(commWorkEffort);
    }

    /**
     * {@code DELETE  /comm-work-efforts/:id} : delete the "id" commWorkEffort.
     *
     * @param id the id of the commWorkEffort to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/comm-work-efforts/{id}")
    public ResponseEntity<Void> deleteCommWorkEffort(@PathVariable Long id) {
        log.debug("REST request to delete CommWorkEffort : {}", id);
        commWorkEffortRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
