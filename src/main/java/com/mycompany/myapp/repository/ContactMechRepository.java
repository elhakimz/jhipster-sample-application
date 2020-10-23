package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ContactMech;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ContactMech entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactMechRepository extends JpaRepository<ContactMech, Long> {
}
