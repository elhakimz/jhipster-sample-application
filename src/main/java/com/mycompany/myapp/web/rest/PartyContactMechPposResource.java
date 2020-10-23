package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.PartyContactMechPpos;
import com.mycompany.myapp.repository.PartyContactMechPposRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.PartyContactMechPpos}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PartyContactMechPposResource {

    private final Logger log = LoggerFactory.getLogger(PartyContactMechPposResource.class);

    private static final String ENTITY_NAME = "partyContactMechPpos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartyContactMechPposRepository partyContactMechPposRepository;

    public PartyContactMechPposResource(PartyContactMechPposRepository partyContactMechPposRepository) {
        this.partyContactMechPposRepository = partyContactMechPposRepository;
    }

    /**
     * {@code POST  /party-contact-mech-ppos} : Create a new partyContactMechPpos.
     *
     * @param partyContactMechPpos the partyContactMechPpos to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partyContactMechPpos, or with status {@code 400 (Bad Request)} if the partyContactMechPpos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/party-contact-mech-ppos")
    public ResponseEntity<PartyContactMechPpos> createPartyContactMechPpos(@RequestBody PartyContactMechPpos partyContactMechPpos) throws URISyntaxException {
        log.debug("REST request to save PartyContactMechPpos : {}", partyContactMechPpos);
        if (partyContactMechPpos.getId() != null) {
            throw new BadRequestAlertException("A new partyContactMechPpos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartyContactMechPpos result = partyContactMechPposRepository.save(partyContactMechPpos);
        return ResponseEntity.created(new URI("/api/party-contact-mech-ppos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /party-contact-mech-ppos} : Updates an existing partyContactMechPpos.
     *
     * @param partyContactMechPpos the partyContactMechPpos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyContactMechPpos,
     * or with status {@code 400 (Bad Request)} if the partyContactMechPpos is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partyContactMechPpos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/party-contact-mech-ppos")
    public ResponseEntity<PartyContactMechPpos> updatePartyContactMechPpos(@RequestBody PartyContactMechPpos partyContactMechPpos) throws URISyntaxException {
        log.debug("REST request to update PartyContactMechPpos : {}", partyContactMechPpos);
        if (partyContactMechPpos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PartyContactMechPpos result = partyContactMechPposRepository.save(partyContactMechPpos);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partyContactMechPpos.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /party-contact-mech-ppos} : get all the partyContactMechPpos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partyContactMechPpos in body.
     */
    @GetMapping("/party-contact-mech-ppos")
    public List<PartyContactMechPpos> getAllPartyContactMechPpos() {
        log.debug("REST request to get all PartyContactMechPpos");
        return partyContactMechPposRepository.findAll();
    }

    /**
     * {@code GET  /party-contact-mech-ppos/:id} : get the "id" partyContactMechPpos.
     *
     * @param id the id of the partyContactMechPpos to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partyContactMechPpos, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/party-contact-mech-ppos/{id}")
    public ResponseEntity<PartyContactMechPpos> getPartyContactMechPpos(@PathVariable Long id) {
        log.debug("REST request to get PartyContactMechPpos : {}", id);
        Optional<PartyContactMechPpos> partyContactMechPpos = partyContactMechPposRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(partyContactMechPpos);
    }

    /**
     * {@code DELETE  /party-contact-mech-ppos/:id} : delete the "id" partyContactMechPpos.
     *
     * @param id the id of the partyContactMechPpos to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/party-contact-mech-ppos/{id}")
    public ResponseEntity<Void> deletePartyContactMechPpos(@PathVariable Long id) {
        log.debug("REST request to delete PartyContactMechPpos : {}", id);
        partyContactMechPposRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
