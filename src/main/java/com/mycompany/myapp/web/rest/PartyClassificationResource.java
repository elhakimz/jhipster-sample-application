package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.PartyClassification;
import com.mycompany.myapp.repository.PartyClassificationRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.PartyClassification}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PartyClassificationResource {

    private final Logger log = LoggerFactory.getLogger(PartyClassificationResource.class);

    private static final String ENTITY_NAME = "partyClassification";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartyClassificationRepository partyClassificationRepository;

    public PartyClassificationResource(PartyClassificationRepository partyClassificationRepository) {
        this.partyClassificationRepository = partyClassificationRepository;
    }

    /**
     * {@code POST  /party-classifications} : Create a new partyClassification.
     *
     * @param partyClassification the partyClassification to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partyClassification, or with status {@code 400 (Bad Request)} if the partyClassification has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/party-classifications")
    public ResponseEntity<PartyClassification> createPartyClassification(@RequestBody PartyClassification partyClassification) throws URISyntaxException {
        log.debug("REST request to save PartyClassification : {}", partyClassification);
        if (partyClassification.getId() != null) {
            throw new BadRequestAlertException("A new partyClassification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartyClassification result = partyClassificationRepository.save(partyClassification);
        return ResponseEntity.created(new URI("/api/party-classifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /party-classifications} : Updates an existing partyClassification.
     *
     * @param partyClassification the partyClassification to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyClassification,
     * or with status {@code 400 (Bad Request)} if the partyClassification is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partyClassification couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/party-classifications")
    public ResponseEntity<PartyClassification> updatePartyClassification(@RequestBody PartyClassification partyClassification) throws URISyntaxException {
        log.debug("REST request to update PartyClassification : {}", partyClassification);
        if (partyClassification.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PartyClassification result = partyClassificationRepository.save(partyClassification);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partyClassification.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /party-classifications} : get all the partyClassifications.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partyClassifications in body.
     */
    @GetMapping("/party-classifications")
    public List<PartyClassification> getAllPartyClassifications() {
        log.debug("REST request to get all PartyClassifications");
        return partyClassificationRepository.findAll();
    }

    /**
     * {@code GET  /party-classifications/:id} : get the "id" partyClassification.
     *
     * @param id the id of the partyClassification to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partyClassification, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/party-classifications/{id}")
    public ResponseEntity<PartyClassification> getPartyClassification(@PathVariable Long id) {
        log.debug("REST request to get PartyClassification : {}", id);
        Optional<PartyClassification> partyClassification = partyClassificationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(partyClassification);
    }

    /**
     * {@code DELETE  /party-classifications/:id} : delete the "id" partyClassification.
     *
     * @param id the id of the partyClassification to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/party-classifications/{id}")
    public ResponseEntity<Void> deletePartyClassification(@PathVariable Long id) {
        log.debug("REST request to delete PartyClassification : {}", id);
        partyClassificationRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
