package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CasusRoleType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CasusRoleType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CasusRoleTypeRepository extends JpaRepository<CasusRoleType, Long> {
}
