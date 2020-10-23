package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.CommEvent;
import com.mycompany.myapp.repository.CommEventRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CommEvent}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CommEventResource {

    private final Logger log = LoggerFactory.getLogger(CommEventResource.class);

    private static final String ENTITY_NAME = "commEvent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommEventRepository commEventRepository;

    public CommEventResource(CommEventRepository commEventRepository) {
        this.commEventRepository = commEventRepository;
    }

    /**
     * {@code POST  /comm-events} : Create a new commEvent.
     *
     * @param commEvent the commEvent to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commEvent, or with status {@code 400 (Bad Request)} if the commEvent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/comm-events")
    public ResponseEntity<CommEvent> createCommEvent(@RequestBody CommEvent commEvent) throws URISyntaxException {
        log.debug("REST request to save CommEvent : {}", commEvent);
        if (commEvent.getId() != null) {
            throw new BadRequestAlertException("A new commEvent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommEvent result = commEventRepository.save(commEvent);
        return ResponseEntity.created(new URI("/api/comm-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /comm-events} : Updates an existing commEvent.
     *
     * @param commEvent the commEvent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commEvent,
     * or with status {@code 400 (Bad Request)} if the commEvent is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commEvent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/comm-events")
    public ResponseEntity<CommEvent> updateCommEvent(@RequestBody CommEvent commEvent) throws URISyntaxException {
        log.debug("REST request to update CommEvent : {}", commEvent);
        if (commEvent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommEvent result = commEventRepository.save(commEvent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commEvent.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /comm-events} : get all the commEvents.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commEvents in body.
     */
    @GetMapping("/comm-events")
    public List<CommEvent> getAllCommEvents() {
        log.debug("REST request to get all CommEvents");
        return commEventRepository.findAll();
    }

    /**
     * {@code GET  /comm-events/:id} : get the "id" commEvent.
     *
     * @param id the id of the commEvent to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commEvent, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/comm-events/{id}")
    public ResponseEntity<CommEvent> getCommEvent(@PathVariable Long id) {
        log.debug("REST request to get CommEvent : {}", id);
        Optional<CommEvent> commEvent = commEventRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(commEvent);
    }

    /**
     * {@code DELETE  /comm-events/:id} : delete the "id" commEvent.
     *
     * @param id the id of the commEvent to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/comm-events/{id}")
    public ResponseEntity<Void> deleteCommEvent(@PathVariable Long id) {
        log.debug("REST request to delete CommEvent : {}", id);
        commEventRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
