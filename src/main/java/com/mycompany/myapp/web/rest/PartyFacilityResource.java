package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.PartyFacility;
import com.mycompany.myapp.repository.PartyFacilityRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.PartyFacility}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PartyFacilityResource {

    private final Logger log = LoggerFactory.getLogger(PartyFacilityResource.class);

    private static final String ENTITY_NAME = "partyFacility";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartyFacilityRepository partyFacilityRepository;

    public PartyFacilityResource(PartyFacilityRepository partyFacilityRepository) {
        this.partyFacilityRepository = partyFacilityRepository;
    }

    /**
     * {@code POST  /party-facilities} : Create a new partyFacility.
     *
     * @param partyFacility the partyFacility to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partyFacility, or with status {@code 400 (Bad Request)} if the partyFacility has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/party-facilities")
    public ResponseEntity<PartyFacility> createPartyFacility(@RequestBody PartyFacility partyFacility) throws URISyntaxException {
        log.debug("REST request to save PartyFacility : {}", partyFacility);
        if (partyFacility.getId() != null) {
            throw new BadRequestAlertException("A new partyFacility cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartyFacility result = partyFacilityRepository.save(partyFacility);
        return ResponseEntity.created(new URI("/api/party-facilities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /party-facilities} : Updates an existing partyFacility.
     *
     * @param partyFacility the partyFacility to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyFacility,
     * or with status {@code 400 (Bad Request)} if the partyFacility is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partyFacility couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/party-facilities")
    public ResponseEntity<PartyFacility> updatePartyFacility(@RequestBody PartyFacility partyFacility) throws URISyntaxException {
        log.debug("REST request to update PartyFacility : {}", partyFacility);
        if (partyFacility.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PartyFacility result = partyFacilityRepository.save(partyFacility);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partyFacility.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /party-facilities} : get all the partyFacilities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partyFacilities in body.
     */
    @GetMapping("/party-facilities")
    public List<PartyFacility> getAllPartyFacilities() {
        log.debug("REST request to get all PartyFacilities");
        return partyFacilityRepository.findAll();
    }

    /**
     * {@code GET  /party-facilities/:id} : get the "id" partyFacility.
     *
     * @param id the id of the partyFacility to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partyFacility, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/party-facilities/{id}")
    public ResponseEntity<PartyFacility> getPartyFacility(@PathVariable Long id) {
        log.debug("REST request to get PartyFacility : {}", id);
        Optional<PartyFacility> partyFacility = partyFacilityRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(partyFacility);
    }

    /**
     * {@code DELETE  /party-facilities/:id} : delete the "id" partyFacility.
     *
     * @param id the id of the partyFacility to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/party-facilities/{id}")
    public ResponseEntity<Void> deletePartyFacility(@PathVariable Long id) {
        log.debug("REST request to delete PartyFacility : {}", id);
        partyFacilityRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
