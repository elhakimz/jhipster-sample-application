package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PartyRole;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PartyRole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartyRoleRepository extends JpaRepository<PartyRole, Long> {
}
