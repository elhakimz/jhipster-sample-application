package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.PartyContact;
import com.mycompany.myapp.repository.PartyContactRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.PartyContact}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PartyContactResource {

    private final Logger log = LoggerFactory.getLogger(PartyContactResource.class);

    private static final String ENTITY_NAME = "partyContact";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartyContactRepository partyContactRepository;

    public PartyContactResource(PartyContactRepository partyContactRepository) {
        this.partyContactRepository = partyContactRepository;
    }

    /**
     * {@code POST  /party-contacts} : Create a new partyContact.
     *
     * @param partyContact the partyContact to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partyContact, or with status {@code 400 (Bad Request)} if the partyContact has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/party-contacts")
    public ResponseEntity<PartyContact> createPartyContact(@Valid @RequestBody PartyContact partyContact) throws URISyntaxException {
        log.debug("REST request to save PartyContact : {}", partyContact);
        if (partyContact.getId() != null) {
            throw new BadRequestAlertException("A new partyContact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartyContact result = partyContactRepository.save(partyContact);
        return ResponseEntity.created(new URI("/api/party-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /party-contacts} : Updates an existing partyContact.
     *
     * @param partyContact the partyContact to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyContact,
     * or with status {@code 400 (Bad Request)} if the partyContact is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partyContact couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/party-contacts")
    public ResponseEntity<PartyContact> updatePartyContact(@Valid @RequestBody PartyContact partyContact) throws URISyntaxException {
        log.debug("REST request to update PartyContact : {}", partyContact);
        if (partyContact.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PartyContact result = partyContactRepository.save(partyContact);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partyContact.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /party-contacts} : get all the partyContacts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partyContacts in body.
     */
    @GetMapping("/party-contacts")
    public List<PartyContact> getAllPartyContacts() {
        log.debug("REST request to get all PartyContacts");
        return partyContactRepository.findAll();
    }

    /**
     * {@code GET  /party-contacts/:id} : get the "id" partyContact.
     *
     * @param id the id of the partyContact to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partyContact, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/party-contacts/{id}")
    public ResponseEntity<PartyContact> getPartyContact(@PathVariable Long id) {
        log.debug("REST request to get PartyContact : {}", id);
        Optional<PartyContact> partyContact = partyContactRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(partyContact);
    }

    /**
     * {@code DELETE  /party-contacts/:id} : delete the "id" partyContact.
     *
     * @param id the id of the partyContact to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/party-contacts/{id}")
    public ResponseEntity<Void> deletePartyContact(@PathVariable Long id) {
        log.debug("REST request to delete PartyContact : {}", id);
        partyContactRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
