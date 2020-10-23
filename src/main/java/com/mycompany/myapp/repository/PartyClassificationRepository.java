package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PartyClassification;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PartyClassification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartyClassificationRepository extends JpaRepository<PartyClassification, Long> {
}
