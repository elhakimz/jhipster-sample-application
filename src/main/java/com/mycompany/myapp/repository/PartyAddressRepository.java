package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PartyAddress;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PartyAddress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartyAddressRepository extends JpaRepository<PartyAddress, Long> {
}
