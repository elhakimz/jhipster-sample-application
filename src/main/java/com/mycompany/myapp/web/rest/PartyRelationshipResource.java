package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.PartyRelationship;
import com.mycompany.myapp.repository.PartyRelationshipRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.PartyRelationship}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PartyRelationshipResource {

    private final Logger log = LoggerFactory.getLogger(PartyRelationshipResource.class);

    private static final String ENTITY_NAME = "partyRelationship";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartyRelationshipRepository partyRelationshipRepository;

    public PartyRelationshipResource(PartyRelationshipRepository partyRelationshipRepository) {
        this.partyRelationshipRepository = partyRelationshipRepository;
    }

    /**
     * {@code POST  /party-relationships} : Create a new partyRelationship.
     *
     * @param partyRelationship the partyRelationship to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partyRelationship, or with status {@code 400 (Bad Request)} if the partyRelationship has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/party-relationships")
    public ResponseEntity<PartyRelationship> createPartyRelationship(@Valid @RequestBody PartyRelationship partyRelationship) throws URISyntaxException {
        log.debug("REST request to save PartyRelationship : {}", partyRelationship);
        if (partyRelationship.getId() != null) {
            throw new BadRequestAlertException("A new partyRelationship cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartyRelationship result = partyRelationshipRepository.save(partyRelationship);
        return ResponseEntity.created(new URI("/api/party-relationships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /party-relationships} : Updates an existing partyRelationship.
     *
     * @param partyRelationship the partyRelationship to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyRelationship,
     * or with status {@code 400 (Bad Request)} if the partyRelationship is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partyRelationship couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/party-relationships")
    public ResponseEntity<PartyRelationship> updatePartyRelationship(@Valid @RequestBody PartyRelationship partyRelationship) throws URISyntaxException {
        log.debug("REST request to update PartyRelationship : {}", partyRelationship);
        if (partyRelationship.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PartyRelationship result = partyRelationshipRepository.save(partyRelationship);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partyRelationship.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /party-relationships} : get all the partyRelationships.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partyRelationships in body.
     */
    @GetMapping("/party-relationships")
    public List<PartyRelationship> getAllPartyRelationships() {
        log.debug("REST request to get all PartyRelationships");
        return partyRelationshipRepository.findAll();
    }

    /**
     * {@code GET  /party-relationships/:id} : get the "id" partyRelationship.
     *
     * @param id the id of the partyRelationship to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partyRelationship, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/party-relationships/{id}")
    public ResponseEntity<PartyRelationship> getPartyRelationship(@PathVariable Long id) {
        log.debug("REST request to get PartyRelationship : {}", id);
        Optional<PartyRelationship> partyRelationship = partyRelationshipRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(partyRelationship);
    }

    /**
     * {@code DELETE  /party-relationships/:id} : delete the "id" partyRelationship.
     *
     * @param id the id of the partyRelationship to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/party-relationships/{id}")
    public ResponseEntity<Void> deletePartyRelationship(@PathVariable Long id) {
        log.debug("REST request to delete PartyRelationship : {}", id);
        partyRelationshipRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
