package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.CommEvtPposType;
import com.mycompany.myapp.repository.CommEvtPposTypeRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CommEvtPposType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CommEvtPposTypeResource {

    private final Logger log = LoggerFactory.getLogger(CommEvtPposTypeResource.class);

    private static final String ENTITY_NAME = "commEvtPposType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommEvtPposTypeRepository commEvtPposTypeRepository;

    public CommEvtPposTypeResource(CommEvtPposTypeRepository commEvtPposTypeRepository) {
        this.commEvtPposTypeRepository = commEvtPposTypeRepository;
    }

    /**
     * {@code POST  /comm-evt-ppos-types} : Create a new commEvtPposType.
     *
     * @param commEvtPposType the commEvtPposType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commEvtPposType, or with status {@code 400 (Bad Request)} if the commEvtPposType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/comm-evt-ppos-types")
    public ResponseEntity<CommEvtPposType> createCommEvtPposType(@RequestBody CommEvtPposType commEvtPposType) throws URISyntaxException {
        log.debug("REST request to save CommEvtPposType : {}", commEvtPposType);
        if (commEvtPposType.getId() != null) {
            throw new BadRequestAlertException("A new commEvtPposType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommEvtPposType result = commEvtPposTypeRepository.save(commEvtPposType);
        return ResponseEntity.created(new URI("/api/comm-evt-ppos-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /comm-evt-ppos-types} : Updates an existing commEvtPposType.
     *
     * @param commEvtPposType the commEvtPposType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commEvtPposType,
     * or with status {@code 400 (Bad Request)} if the commEvtPposType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commEvtPposType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/comm-evt-ppos-types")
    public ResponseEntity<CommEvtPposType> updateCommEvtPposType(@RequestBody CommEvtPposType commEvtPposType) throws URISyntaxException {
        log.debug("REST request to update CommEvtPposType : {}", commEvtPposType);
        if (commEvtPposType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommEvtPposType result = commEvtPposTypeRepository.save(commEvtPposType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commEvtPposType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /comm-evt-ppos-types} : get all the commEvtPposTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commEvtPposTypes in body.
     */
    @GetMapping("/comm-evt-ppos-types")
    public List<CommEvtPposType> getAllCommEvtPposTypes() {
        log.debug("REST request to get all CommEvtPposTypes");
        return commEvtPposTypeRepository.findAll();
    }

    /**
     * {@code GET  /comm-evt-ppos-types/:id} : get the "id" commEvtPposType.
     *
     * @param id the id of the commEvtPposType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commEvtPposType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/comm-evt-ppos-types/{id}")
    public ResponseEntity<CommEvtPposType> getCommEvtPposType(@PathVariable Long id) {
        log.debug("REST request to get CommEvtPposType : {}", id);
        Optional<CommEvtPposType> commEvtPposType = commEvtPposTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(commEvtPposType);
    }

    /**
     * {@code DELETE  /comm-evt-ppos-types/:id} : delete the "id" commEvtPposType.
     *
     * @param id the id of the commEvtPposType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/comm-evt-ppos-types/{id}")
    public ResponseEntity<Void> deleteCommEvtPposType(@PathVariable Long id) {
        log.debug("REST request to delete CommEvtPposType : {}", id);
        commEvtPposTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
