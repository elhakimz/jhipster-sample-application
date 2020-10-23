package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.StatusType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StatusType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatusTypeRepository extends JpaRepository<StatusType, Long> {
}
