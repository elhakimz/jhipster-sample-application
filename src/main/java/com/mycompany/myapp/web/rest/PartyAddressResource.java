package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.PartyAddress;
import com.mycompany.myapp.repository.PartyAddressRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.PartyAddress}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PartyAddressResource {

    private final Logger log = LoggerFactory.getLogger(PartyAddressResource.class);

    private static final String ENTITY_NAME = "partyAddress";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartyAddressRepository partyAddressRepository;

    public PartyAddressResource(PartyAddressRepository partyAddressRepository) {
        this.partyAddressRepository = partyAddressRepository;
    }

    /**
     * {@code POST  /party-addresses} : Create a new partyAddress.
     *
     * @param partyAddress the partyAddress to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partyAddress, or with status {@code 400 (Bad Request)} if the partyAddress has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/party-addresses")
    public ResponseEntity<PartyAddress> createPartyAddress(@RequestBody PartyAddress partyAddress) throws URISyntaxException {
        log.debug("REST request to save PartyAddress : {}", partyAddress);
        if (partyAddress.getId() != null) {
            throw new BadRequestAlertException("A new partyAddress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartyAddress result = partyAddressRepository.save(partyAddress);
        return ResponseEntity.created(new URI("/api/party-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /party-addresses} : Updates an existing partyAddress.
     *
     * @param partyAddress the partyAddress to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyAddress,
     * or with status {@code 400 (Bad Request)} if the partyAddress is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partyAddress couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/party-addresses")
    public ResponseEntity<PartyAddress> updatePartyAddress(@RequestBody PartyAddress partyAddress) throws URISyntaxException {
        log.debug("REST request to update PartyAddress : {}", partyAddress);
        if (partyAddress.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PartyAddress result = partyAddressRepository.save(partyAddress);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partyAddress.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /party-addresses} : get all the partyAddresses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partyAddresses in body.
     */
    @GetMapping("/party-addresses")
    public List<PartyAddress> getAllPartyAddresses() {
        log.debug("REST request to get all PartyAddresses");
        return partyAddressRepository.findAll();
    }

    /**
     * {@code GET  /party-addresses/:id} : get the "id" partyAddress.
     *
     * @param id the id of the partyAddress to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partyAddress, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/party-addresses/{id}")
    public ResponseEntity<PartyAddress> getPartyAddress(@PathVariable Long id) {
        log.debug("REST request to get PartyAddress : {}", id);
        Optional<PartyAddress> partyAddress = partyAddressRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(partyAddress);
    }

    /**
     * {@code DELETE  /party-addresses/:id} : delete the "id" partyAddress.
     *
     * @param id the id of the partyAddress to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/party-addresses/{id}")
    public ResponseEntity<Void> deletePartyAddress(@PathVariable Long id) {
        log.debug("REST request to delete PartyAddress : {}", id);
        partyAddressRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
