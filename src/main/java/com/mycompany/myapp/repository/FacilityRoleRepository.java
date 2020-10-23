package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.FacilityRole;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FacilityRole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FacilityRoleRepository extends JpaRepository<FacilityRole, Long> {
}
