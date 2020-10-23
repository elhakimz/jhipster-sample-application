package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PartyContact;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PartyContact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartyContactRepository extends JpaRepository<PartyContact, Long> {
}
