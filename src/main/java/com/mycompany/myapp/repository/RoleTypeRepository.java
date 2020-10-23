package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.RoleType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RoleType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoleTypeRepository extends JpaRepository<RoleType, Long> {
}
