package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.CommEventPurpose;
import com.mycompany.myapp.repository.CommEventPurposeRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CommEventPurpose}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CommEventPurposeResource {

    private final Logger log = LoggerFactory.getLogger(CommEventPurposeResource.class);

    private static final String ENTITY_NAME = "commEventPurpose";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommEventPurposeRepository commEventPurposeRepository;

    public CommEventPurposeResource(CommEventPurposeRepository commEventPurposeRepository) {
        this.commEventPurposeRepository = commEventPurposeRepository;
    }

    /**
     * {@code POST  /comm-event-purposes} : Create a new commEventPurpose.
     *
     * @param commEventPurpose the commEventPurpose to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commEventPurpose, or with status {@code 400 (Bad Request)} if the commEventPurpose has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/comm-event-purposes")
    public ResponseEntity<CommEventPurpose> createCommEventPurpose(@RequestBody CommEventPurpose commEventPurpose) throws URISyntaxException {
        log.debug("REST request to save CommEventPurpose : {}", commEventPurpose);
        if (commEventPurpose.getId() != null) {
            throw new BadRequestAlertException("A new commEventPurpose cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommEventPurpose result = commEventPurposeRepository.save(commEventPurpose);
        return ResponseEntity.created(new URI("/api/comm-event-purposes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /comm-event-purposes} : Updates an existing commEventPurpose.
     *
     * @param commEventPurpose the commEventPurpose to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commEventPurpose,
     * or with status {@code 400 (Bad Request)} if the commEventPurpose is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commEventPurpose couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/comm-event-purposes")
    public ResponseEntity<CommEventPurpose> updateCommEventPurpose(@RequestBody CommEventPurpose commEventPurpose) throws URISyntaxException {
        log.debug("REST request to update CommEventPurpose : {}", commEventPurpose);
        if (commEventPurpose.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommEventPurpose result = commEventPurposeRepository.save(commEventPurpose);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commEventPurpose.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /comm-event-purposes} : get all the commEventPurposes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commEventPurposes in body.
     */
    @GetMapping("/comm-event-purposes")
    public List<CommEventPurpose> getAllCommEventPurposes() {
        log.debug("REST request to get all CommEventPurposes");
        return commEventPurposeRepository.findAll();
    }

    /**
     * {@code GET  /comm-event-purposes/:id} : get the "id" commEventPurpose.
     *
     * @param id the id of the commEventPurpose to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commEventPurpose, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/comm-event-purposes/{id}")
    public ResponseEntity<CommEventPurpose> getCommEventPurpose(@PathVariable Long id) {
        log.debug("REST request to get CommEventPurpose : {}", id);
        Optional<CommEventPurpose> commEventPurpose = commEventPurposeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(commEventPurpose);
    }

    /**
     * {@code DELETE  /comm-event-purposes/:id} : delete the "id" commEventPurpose.
     *
     * @param id the id of the commEventPurpose to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/comm-event-purposes/{id}")
    public ResponseEntity<Void> deleteCommEventPurpose(@PathVariable Long id) {
        log.debug("REST request to delete CommEventPurpose : {}", id);
        commEventPurposeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
