package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.ContactMech;
import com.mycompany.myapp.repository.ContactMechRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ContactMech}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ContactMechResource {

    private final Logger log = LoggerFactory.getLogger(ContactMechResource.class);

    private static final String ENTITY_NAME = "contactMech";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContactMechRepository contactMechRepository;

    public ContactMechResource(ContactMechRepository contactMechRepository) {
        this.contactMechRepository = contactMechRepository;
    }

    /**
     * {@code POST  /contact-meches} : Create a new contactMech.
     *
     * @param contactMech the contactMech to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contactMech, or with status {@code 400 (Bad Request)} if the contactMech has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contact-meches")
    public ResponseEntity<ContactMech> createContactMech(@RequestBody ContactMech contactMech) throws URISyntaxException {
        log.debug("REST request to save ContactMech : {}", contactMech);
        if (contactMech.getId() != null) {
            throw new BadRequestAlertException("A new contactMech cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContactMech result = contactMechRepository.save(contactMech);
        return ResponseEntity.created(new URI("/api/contact-meches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contact-meches} : Updates an existing contactMech.
     *
     * @param contactMech the contactMech to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactMech,
     * or with status {@code 400 (Bad Request)} if the contactMech is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contactMech couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contact-meches")
    public ResponseEntity<ContactMech> updateContactMech(@RequestBody ContactMech contactMech) throws URISyntaxException {
        log.debug("REST request to update ContactMech : {}", contactMech);
        if (contactMech.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContactMech result = contactMechRepository.save(contactMech);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contactMech.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /contact-meches} : get all the contactMeches.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contactMeches in body.
     */
    @GetMapping("/contact-meches")
    public List<ContactMech> getAllContactMeches() {
        log.debug("REST request to get all ContactMeches");
        return contactMechRepository.findAll();
    }

    /**
     * {@code GET  /contact-meches/:id} : get the "id" contactMech.
     *
     * @param id the id of the contactMech to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contactMech, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contact-meches/{id}")
    public ResponseEntity<ContactMech> getContactMech(@PathVariable Long id) {
        log.debug("REST request to get ContactMech : {}", id);
        Optional<ContactMech> contactMech = contactMechRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(contactMech);
    }

    /**
     * {@code DELETE  /contact-meches/:id} : delete the "id" contactMech.
     *
     * @param id the id of the contactMech to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contact-meches/{id}")
    public ResponseEntity<Void> deleteContactMech(@PathVariable Long id) {
        log.debug("REST request to delete ContactMech : {}", id);
        contactMechRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
