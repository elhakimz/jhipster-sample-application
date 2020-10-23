package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.FacilityRoleType;
import com.mycompany.myapp.repository.FacilityRoleTypeRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.FacilityRoleType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FacilityRoleTypeResource {

    private final Logger log = LoggerFactory.getLogger(FacilityRoleTypeResource.class);

    private static final String ENTITY_NAME = "facilityRoleType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FacilityRoleTypeRepository facilityRoleTypeRepository;

    public FacilityRoleTypeResource(FacilityRoleTypeRepository facilityRoleTypeRepository) {
        this.facilityRoleTypeRepository = facilityRoleTypeRepository;
    }

    /**
     * {@code POST  /facility-role-types} : Create a new facilityRoleType.
     *
     * @param facilityRoleType the facilityRoleType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new facilityRoleType, or with status {@code 400 (Bad Request)} if the facilityRoleType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/facility-role-types")
    public ResponseEntity<FacilityRoleType> createFacilityRoleType(@RequestBody FacilityRoleType facilityRoleType) throws URISyntaxException {
        log.debug("REST request to save FacilityRoleType : {}", facilityRoleType);
        if (facilityRoleType.getId() != null) {
            throw new BadRequestAlertException("A new facilityRoleType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FacilityRoleType result = facilityRoleTypeRepository.save(facilityRoleType);
        return ResponseEntity.created(new URI("/api/facility-role-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /facility-role-types} : Updates an existing facilityRoleType.
     *
     * @param facilityRoleType the facilityRoleType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated facilityRoleType,
     * or with status {@code 400 (Bad Request)} if the facilityRoleType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the facilityRoleType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/facility-role-types")
    public ResponseEntity<FacilityRoleType> updateFacilityRoleType(@RequestBody FacilityRoleType facilityRoleType) throws URISyntaxException {
        log.debug("REST request to update FacilityRoleType : {}", facilityRoleType);
        if (facilityRoleType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FacilityRoleType result = facilityRoleTypeRepository.save(facilityRoleType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, facilityRoleType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /facility-role-types} : get all the facilityRoleTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of facilityRoleTypes in body.
     */
    @GetMapping("/facility-role-types")
    public List<FacilityRoleType> getAllFacilityRoleTypes() {
        log.debug("REST request to get all FacilityRoleTypes");
        return facilityRoleTypeRepository.findAll();
    }

    /**
     * {@code GET  /facility-role-types/:id} : get the "id" facilityRoleType.
     *
     * @param id the id of the facilityRoleType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the facilityRoleType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/facility-role-types/{id}")
    public ResponseEntity<FacilityRoleType> getFacilityRoleType(@PathVariable Long id) {
        log.debug("REST request to get FacilityRoleType : {}", id);
        Optional<FacilityRoleType> facilityRoleType = facilityRoleTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(facilityRoleType);
    }

    /**
     * {@code DELETE  /facility-role-types/:id} : delete the "id" facilityRoleType.
     *
     * @param id the id of the facilityRoleType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/facility-role-types/{id}")
    public ResponseEntity<Void> deleteFacilityRoleType(@PathVariable Long id) {
        log.debug("REST request to delete FacilityRoleType : {}", id);
        facilityRoleTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
