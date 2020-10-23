package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.CasusRole;
import com.mycompany.myapp.repository.CasusRoleRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CasusRole}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CasusRoleResource {

    private final Logger log = LoggerFactory.getLogger(CasusRoleResource.class);

    private static final String ENTITY_NAME = "casusRole";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CasusRoleRepository casusRoleRepository;

    public CasusRoleResource(CasusRoleRepository casusRoleRepository) {
        this.casusRoleRepository = casusRoleRepository;
    }

    /**
     * {@code POST  /casus-roles} : Create a new casusRole.
     *
     * @param casusRole the casusRole to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new casusRole, or with status {@code 400 (Bad Request)} if the casusRole has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/casus-roles")
    public ResponseEntity<CasusRole> createCasusRole(@RequestBody CasusRole casusRole) throws URISyntaxException {
        log.debug("REST request to save CasusRole : {}", casusRole);
        if (casusRole.getId() != null) {
            throw new BadRequestAlertException("A new casusRole cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CasusRole result = casusRoleRepository.save(casusRole);
        return ResponseEntity.created(new URI("/api/casus-roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /casus-roles} : Updates an existing casusRole.
     *
     * @param casusRole the casusRole to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated casusRole,
     * or with status {@code 400 (Bad Request)} if the casusRole is not valid,
     * or with status {@code 500 (Internal Server Error)} if the casusRole couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/casus-roles")
    public ResponseEntity<CasusRole> updateCasusRole(@RequestBody CasusRole casusRole) throws URISyntaxException {
        log.debug("REST request to update CasusRole : {}", casusRole);
        if (casusRole.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CasusRole result = casusRoleRepository.save(casusRole);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, casusRole.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /casus-roles} : get all the casusRoles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of casusRoles in body.
     */
    @GetMapping("/casus-roles")
    public List<CasusRole> getAllCasusRoles() {
        log.debug("REST request to get all CasusRoles");
        return casusRoleRepository.findAll();
    }

    /**
     * {@code GET  /casus-roles/:id} : get the "id" casusRole.
     *
     * @param id the id of the casusRole to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the casusRole, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/casus-roles/{id}")
    public ResponseEntity<CasusRole> getCasusRole(@PathVariable Long id) {
        log.debug("REST request to get CasusRole : {}", id);
        Optional<CasusRole> casusRole = casusRoleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(casusRole);
    }

    /**
     * {@code DELETE  /casus-roles/:id} : delete the "id" casusRole.
     *
     * @param id the id of the casusRole to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/casus-roles/{id}")
    public ResponseEntity<Void> deleteCasusRole(@PathVariable Long id) {
        log.debug("REST request to delete CasusRole : {}", id);
        casusRoleRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
