package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.ContactMechLink;
import com.mycompany.myapp.repository.ContactMechLinkRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ContactMechLink}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ContactMechLinkResource {

    private final Logger log = LoggerFactory.getLogger(ContactMechLinkResource.class);

    private static final String ENTITY_NAME = "contactMechLink";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContactMechLinkRepository contactMechLinkRepository;

    public ContactMechLinkResource(ContactMechLinkRepository contactMechLinkRepository) {
        this.contactMechLinkRepository = contactMechLinkRepository;
    }

    /**
     * {@code POST  /contact-mech-links} : Create a new contactMechLink.
     *
     * @param contactMechLink the contactMechLink to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contactMechLink, or with status {@code 400 (Bad Request)} if the contactMechLink has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contact-mech-links")
    public ResponseEntity<ContactMechLink> createContactMechLink(@RequestBody ContactMechLink contactMechLink) throws URISyntaxException {
        log.debug("REST request to save ContactMechLink : {}", contactMechLink);
        if (contactMechLink.getId() != null) {
            throw new BadRequestAlertException("A new contactMechLink cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContactMechLink result = contactMechLinkRepository.save(contactMechLink);
        return ResponseEntity.created(new URI("/api/contact-mech-links/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contact-mech-links} : Updates an existing contactMechLink.
     *
     * @param contactMechLink the contactMechLink to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactMechLink,
     * or with status {@code 400 (Bad Request)} if the contactMechLink is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contactMechLink couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contact-mech-links")
    public ResponseEntity<ContactMechLink> updateContactMechLink(@RequestBody ContactMechLink contactMechLink) throws URISyntaxException {
        log.debug("REST request to update ContactMechLink : {}", contactMechLink);
        if (contactMechLink.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContactMechLink result = contactMechLinkRepository.save(contactMechLink);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contactMechLink.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /contact-mech-links} : get all the contactMechLinks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contactMechLinks in body.
     */
    @GetMapping("/contact-mech-links")
    public List<ContactMechLink> getAllContactMechLinks() {
        log.debug("REST request to get all ContactMechLinks");
        return contactMechLinkRepository.findAll();
    }

    /**
     * {@code GET  /contact-mech-links/:id} : get the "id" contactMechLink.
     *
     * @param id the id of the contactMechLink to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contactMechLink, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contact-mech-links/{id}")
    public ResponseEntity<ContactMechLink> getContactMechLink(@PathVariable Long id) {
        log.debug("REST request to get ContactMechLink : {}", id);
        Optional<ContactMechLink> contactMechLink = contactMechLinkRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(contactMechLink);
    }

    /**
     * {@code DELETE  /contact-mech-links/:id} : delete the "id" contactMechLink.
     *
     * @param id the id of the contactMechLink to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contact-mech-links/{id}")
    public ResponseEntity<Void> deleteContactMechLink(@PathVariable Long id) {
        log.debug("REST request to delete ContactMechLink : {}", id);
        contactMechLinkRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
