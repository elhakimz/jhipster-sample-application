package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.CasusRoleType;
import com.mycompany.myapp.repository.CasusRoleTypeRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CasusRoleType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CasusRoleTypeResource {

    private final Logger log = LoggerFactory.getLogger(CasusRoleTypeResource.class);

    private static final String ENTITY_NAME = "casusRoleType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CasusRoleTypeRepository casusRoleTypeRepository;

    public CasusRoleTypeResource(CasusRoleTypeRepository casusRoleTypeRepository) {
        this.casusRoleTypeRepository = casusRoleTypeRepository;
    }

    /**
     * {@code POST  /casus-role-types} : Create a new casusRoleType.
     *
     * @param casusRoleType the casusRoleType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new casusRoleType, or with status {@code 400 (Bad Request)} if the casusRoleType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/casus-role-types")
    public ResponseEntity<CasusRoleType> createCasusRoleType(@RequestBody CasusRoleType casusRoleType) throws URISyntaxException {
        log.debug("REST request to save CasusRoleType : {}", casusRoleType);
        if (casusRoleType.getId() != null) {
            throw new BadRequestAlertException("A new casusRoleType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CasusRoleType result = casusRoleTypeRepository.save(casusRoleType);
        return ResponseEntity.created(new URI("/api/casus-role-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /casus-role-types} : Updates an existing casusRoleType.
     *
     * @param casusRoleType the casusRoleType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated casusRoleType,
     * or with status {@code 400 (Bad Request)} if the casusRoleType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the casusRoleType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/casus-role-types")
    public ResponseEntity<CasusRoleType> updateCasusRoleType(@RequestBody CasusRoleType casusRoleType) throws URISyntaxException {
        log.debug("REST request to update CasusRoleType : {}", casusRoleType);
        if (casusRoleType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CasusRoleType result = casusRoleTypeRepository.save(casusRoleType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, casusRoleType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /casus-role-types} : get all the casusRoleTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of casusRoleTypes in body.
     */
    @GetMapping("/casus-role-types")
    public List<CasusRoleType> getAllCasusRoleTypes() {
        log.debug("REST request to get all CasusRoleTypes");
        return casusRoleTypeRepository.findAll();
    }

    /**
     * {@code GET  /casus-role-types/:id} : get the "id" casusRoleType.
     *
     * @param id the id of the casusRoleType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the casusRoleType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/casus-role-types/{id}")
    public ResponseEntity<CasusRoleType> getCasusRoleType(@PathVariable Long id) {
        log.debug("REST request to get CasusRoleType : {}", id);
        Optional<CasusRoleType> casusRoleType = casusRoleTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(casusRoleType);
    }

    /**
     * {@code DELETE  /casus-role-types/:id} : delete the "id" casusRoleType.
     *
     * @param id the id of the casusRoleType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/casus-role-types/{id}")
    public ResponseEntity<Void> deleteCasusRoleType(@PathVariable Long id) {
        log.debug("REST request to delete CasusRoleType : {}", id);
        casusRoleTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
