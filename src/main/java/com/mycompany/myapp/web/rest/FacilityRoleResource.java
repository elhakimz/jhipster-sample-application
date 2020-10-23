package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.FacilityRole;
import com.mycompany.myapp.repository.FacilityRoleRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.FacilityRole}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FacilityRoleResource {

    private final Logger log = LoggerFactory.getLogger(FacilityRoleResource.class);

    private static final String ENTITY_NAME = "facilityRole";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FacilityRoleRepository facilityRoleRepository;

    public FacilityRoleResource(FacilityRoleRepository facilityRoleRepository) {
        this.facilityRoleRepository = facilityRoleRepository;
    }

    /**
     * {@code POST  /facility-roles} : Create a new facilityRole.
     *
     * @param facilityRole the facilityRole to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new facilityRole, or with status {@code 400 (Bad Request)} if the facilityRole has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/facility-roles")
    public ResponseEntity<FacilityRole> createFacilityRole(@RequestBody FacilityRole facilityRole) throws URISyntaxException {
        log.debug("REST request to save FacilityRole : {}", facilityRole);
        if (facilityRole.getId() != null) {
            throw new BadRequestAlertException("A new facilityRole cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FacilityRole result = facilityRoleRepository.save(facilityRole);
        return ResponseEntity.created(new URI("/api/facility-roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /facility-roles} : Updates an existing facilityRole.
     *
     * @param facilityRole the facilityRole to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated facilityRole,
     * or with status {@code 400 (Bad Request)} if the facilityRole is not valid,
     * or with status {@code 500 (Internal Server Error)} if the facilityRole couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/facility-roles")
    public ResponseEntity<FacilityRole> updateFacilityRole(@RequestBody FacilityRole facilityRole) throws URISyntaxException {
        log.debug("REST request to update FacilityRole : {}", facilityRole);
        if (facilityRole.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FacilityRole result = facilityRoleRepository.save(facilityRole);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, facilityRole.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /facility-roles} : get all the facilityRoles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of facilityRoles in body.
     */
    @GetMapping("/facility-roles")
    public List<FacilityRole> getAllFacilityRoles() {
        log.debug("REST request to get all FacilityRoles");
        return facilityRoleRepository.findAll();
    }

    /**
     * {@code GET  /facility-roles/:id} : get the "id" facilityRole.
     *
     * @param id the id of the facilityRole to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the facilityRole, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/facility-roles/{id}")
    public ResponseEntity<FacilityRole> getFacilityRole(@PathVariable Long id) {
        log.debug("REST request to get FacilityRole : {}", id);
        Optional<FacilityRole> facilityRole = facilityRoleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(facilityRole);
    }

    /**
     * {@code DELETE  /facility-roles/:id} : delete the "id" facilityRole.
     *
     * @param id the id of the facilityRole to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/facility-roles/{id}")
    public ResponseEntity<Void> deleteFacilityRole(@PathVariable Long id) {
        log.debug("REST request to delete FacilityRole : {}", id);
        facilityRoleRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
