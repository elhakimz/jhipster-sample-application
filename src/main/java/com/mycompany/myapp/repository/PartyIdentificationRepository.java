package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PartyIdentification;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PartyIdentification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartyIdentificationRepository extends JpaRepository<PartyIdentification, Long> {
}
