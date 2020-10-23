package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CasusRole;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CasusRole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CasusRoleRepository extends JpaRepository<CasusRole, Long> {
}
