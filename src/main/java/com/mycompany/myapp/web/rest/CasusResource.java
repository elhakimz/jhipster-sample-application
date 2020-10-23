package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Casus;
import com.mycompany.myapp.repository.CasusRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Casus}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CasusResource {

    private final Logger log = LoggerFactory.getLogger(CasusResource.class);

    private static final String ENTITY_NAME = "casus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CasusRepository casusRepository;

    public CasusResource(CasusRepository casusRepository) {
        this.casusRepository = casusRepository;
    }

    /**
     * {@code POST  /casuses} : Create a new casus.
     *
     * @param casus the casus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new casus, or with status {@code 400 (Bad Request)} if the casus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/casuses")
    public ResponseEntity<Casus> createCasus(@RequestBody Casus casus) throws URISyntaxException {
        log.debug("REST request to save Casus : {}", casus);
        if (casus.getId() != null) {
            throw new BadRequestAlertException("A new casus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Casus result = casusRepository.save(casus);
        return ResponseEntity.created(new URI("/api/casuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /casuses} : Updates an existing casus.
     *
     * @param casus the casus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated casus,
     * or with status {@code 400 (Bad Request)} if the casus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the casus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/casuses")
    public ResponseEntity<Casus> updateCasus(@RequestBody Casus casus) throws URISyntaxException {
        log.debug("REST request to update Casus : {}", casus);
        if (casus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Casus result = casusRepository.save(casus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, casus.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /casuses} : get all the casuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of casuses in body.
     */
    @GetMapping("/casuses")
    public List<Casus> getAllCasuses() {
        log.debug("REST request to get all Casuses");
        return casusRepository.findAll();
    }

    /**
     * {@code GET  /casuses/:id} : get the "id" casus.
     *
     * @param id the id of the casus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the casus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/casuses/{id}")
    public ResponseEntity<Casus> getCasus(@PathVariable Long id) {
        log.debug("REST request to get Casus : {}", id);
        Optional<Casus> casus = casusRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(casus);
    }

    /**
     * {@code DELETE  /casuses/:id} : delete the "id" casus.
     *
     * @param id the id of the casus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/casuses/{id}")
    public ResponseEntity<Void> deleteCasus(@PathVariable Long id) {
        log.debug("REST request to delete Casus : {}", id);
        casusRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
