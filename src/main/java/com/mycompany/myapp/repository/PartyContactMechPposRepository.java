package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PartyContactMechPpos;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PartyContactMechPpos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartyContactMechPposRepository extends JpaRepository<PartyContactMechPpos, Long> {
}
