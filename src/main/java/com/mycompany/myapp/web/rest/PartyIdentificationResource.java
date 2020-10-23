package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.PartyIdentification;
import com.mycompany.myapp.repository.PartyIdentificationRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.PartyIdentification}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PartyIdentificationResource {

    private final Logger log = LoggerFactory.getLogger(PartyIdentificationResource.class);

    private static final String ENTITY_NAME = "partyIdentification";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartyIdentificationRepository partyIdentificationRepository;

    public PartyIdentificationResource(PartyIdentificationRepository partyIdentificationRepository) {
        this.partyIdentificationRepository = partyIdentificationRepository;
    }

    /**
     * {@code POST  /party-identifications} : Create a new partyIdentification.
     *
     * @param partyIdentification the partyIdentification to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partyIdentification, or with status {@code 400 (Bad Request)} if the partyIdentification has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/party-identifications")
    public ResponseEntity<PartyIdentification> createPartyIdentification(@Valid @RequestBody PartyIdentification partyIdentification) throws URISyntaxException {
        log.debug("REST request to save PartyIdentification : {}", partyIdentification);
        if (partyIdentification.getId() != null) {
            throw new BadRequestAlertException("A new partyIdentification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartyIdentification result = partyIdentificationRepository.save(partyIdentification);
        return ResponseEntity.created(new URI("/api/party-identifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /party-identifications} : Updates an existing partyIdentification.
     *
     * @param partyIdentification the partyIdentification to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyIdentification,
     * or with status {@code 400 (Bad Request)} if the partyIdentification is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partyIdentification couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/party-identifications")
    public ResponseEntity<PartyIdentification> updatePartyIdentification(@Valid @RequestBody PartyIdentification partyIdentification) throws URISyntaxException {
        log.debug("REST request to update PartyIdentification : {}", partyIdentification);
        if (partyIdentification.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PartyIdentification result = partyIdentificationRepository.save(partyIdentification);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partyIdentification.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /party-identifications} : get all the partyIdentifications.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partyIdentifications in body.
     */
    @GetMapping("/party-identifications")
    public List<PartyIdentification> getAllPartyIdentifications() {
        log.debug("REST request to get all PartyIdentifications");
        return partyIdentificationRepository.findAll();
    }

    /**
     * {@code GET  /party-identifications/:id} : get the "id" partyIdentification.
     *
     * @param id the id of the partyIdentification to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partyIdentification, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/party-identifications/{id}")
    public ResponseEntity<PartyIdentification> getPartyIdentification(@PathVariable Long id) {
        log.debug("REST request to get PartyIdentification : {}", id);
        Optional<PartyIdentification> partyIdentification = partyIdentificationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(partyIdentification);
    }

    /**
     * {@code DELETE  /party-identifications/:id} : delete the "id" partyIdentification.
     *
     * @param id the id of the partyIdentification to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/party-identifications/{id}")
    public ResponseEntity<Void> deletePartyIdentification(@PathVariable Long id) {
        log.debug("REST request to delete PartyIdentification : {}", id);
        partyIdentificationRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
