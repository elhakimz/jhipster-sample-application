package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.PartyContactMech;
import com.mycompany.myapp.repository.PartyContactMechRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.PartyContactMech}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PartyContactMechResource {

    private final Logger log = LoggerFactory.getLogger(PartyContactMechResource.class);

    private static final String ENTITY_NAME = "partyContactMech";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartyContactMechRepository partyContactMechRepository;

    public PartyContactMechResource(PartyContactMechRepository partyContactMechRepository) {
        this.partyContactMechRepository = partyContactMechRepository;
    }

    /**
     * {@code POST  /party-contact-meches} : Create a new partyContactMech.
     *
     * @param partyContactMech the partyContactMech to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partyContactMech, or with status {@code 400 (Bad Request)} if the partyContactMech has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/party-contact-meches")
    public ResponseEntity<PartyContactMech> createPartyContactMech(@RequestBody PartyContactMech partyContactMech) throws URISyntaxException {
        log.debug("REST request to save PartyContactMech : {}", partyContactMech);
        if (partyContactMech.getId() != null) {
            throw new BadRequestAlertException("A new partyContactMech cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartyContactMech result = partyContactMechRepository.save(partyContactMech);
        return ResponseEntity.created(new URI("/api/party-contact-meches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /party-contact-meches} : Updates an existing partyContactMech.
     *
     * @param partyContactMech the partyContactMech to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyContactMech,
     * or with status {@code 400 (Bad Request)} if the partyContactMech is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partyContactMech couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/party-contact-meches")
    public ResponseEntity<PartyContactMech> updatePartyContactMech(@RequestBody PartyContactMech partyContactMech) throws URISyntaxException {
        log.debug("REST request to update PartyContactMech : {}", partyContactMech);
        if (partyContactMech.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PartyContactMech result = partyContactMechRepository.save(partyContactMech);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partyContactMech.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /party-contact-meches} : get all the partyContactMeches.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partyContactMeches in body.
     */
    @GetMapping("/party-contact-meches")
    public List<PartyContactMech> getAllPartyContactMeches() {
        log.debug("REST request to get all PartyContactMeches");
        return partyContactMechRepository.findAll();
    }

    /**
     * {@code GET  /party-contact-meches/:id} : get the "id" partyContactMech.
     *
     * @param id the id of the partyContactMech to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partyContactMech, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/party-contact-meches/{id}")
    public ResponseEntity<PartyContactMech> getPartyContactMech(@PathVariable Long id) {
        log.debug("REST request to get PartyContactMech : {}", id);
        Optional<PartyContactMech> partyContactMech = partyContactMechRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(partyContactMech);
    }

    /**
     * {@code DELETE  /party-contact-meches/:id} : delete the "id" partyContactMech.
     *
     * @param id the id of the partyContactMech to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/party-contact-meches/{id}")
    public ResponseEntity<Void> deletePartyContactMech(@PathVariable Long id) {
        log.debug("REST request to delete PartyContactMech : {}", id);
        partyContactMechRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
