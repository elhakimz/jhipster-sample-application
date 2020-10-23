package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.StatusType;
import com.mycompany.myapp.repository.StatusTypeRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.StatusType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class StatusTypeResource {

    private final Logger log = LoggerFactory.getLogger(StatusTypeResource.class);

    private static final String ENTITY_NAME = "statusType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StatusTypeRepository statusTypeRepository;

    public StatusTypeResource(StatusTypeRepository statusTypeRepository) {
        this.statusTypeRepository = statusTypeRepository;
    }

    /**
     * {@code POST  /status-types} : Create a new statusType.
     *
     * @param statusType the statusType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new statusType, or with status {@code 400 (Bad Request)} if the statusType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/status-types")
    public ResponseEntity<StatusType> createStatusType(@RequestBody StatusType statusType) throws URISyntaxException {
        log.debug("REST request to save StatusType : {}", statusType);
        if (statusType.getId() != null) {
            throw new BadRequestAlertException("A new statusType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StatusType result = statusTypeRepository.save(statusType);
        return ResponseEntity.created(new URI("/api/status-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /status-types} : Updates an existing statusType.
     *
     * @param statusType the statusType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statusType,
     * or with status {@code 400 (Bad Request)} if the statusType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the statusType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/status-types")
    public ResponseEntity<StatusType> updateStatusType(@RequestBody StatusType statusType) throws URISyntaxException {
        log.debug("REST request to update StatusType : {}", statusType);
        if (statusType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StatusType result = statusTypeRepository.save(statusType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, statusType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /status-types} : get all the statusTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of statusTypes in body.
     */
    @GetMapping("/status-types")
    public List<StatusType> getAllStatusTypes() {
        log.debug("REST request to get all StatusTypes");
        return statusTypeRepository.findAll();
    }

    /**
     * {@code GET  /status-types/:id} : get the "id" statusType.
     *
     * @param id the id of the statusType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the statusType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/status-types/{id}")
    public ResponseEntity<StatusType> getStatusType(@PathVariable Long id) {
        log.debug("REST request to get StatusType : {}", id);
        Optional<StatusType> statusType = statusTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(statusType);
    }

    /**
     * {@code DELETE  /status-types/:id} : delete the "id" statusType.
     *
     * @param id the id of the statusType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/status-types/{id}")
    public ResponseEntity<Void> deleteStatusType(@PathVariable Long id) {
        log.debug("REST request to delete StatusType : {}", id);
        statusTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
