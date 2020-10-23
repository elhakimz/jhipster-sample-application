package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PartyFacility;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PartyFacility entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartyFacilityRepository extends JpaRepository<PartyFacility, Long> {
}
