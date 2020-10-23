package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.FacilityRoleType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FacilityRoleType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FacilityRoleTypeRepository extends JpaRepository<FacilityRoleType, Long> {
}
