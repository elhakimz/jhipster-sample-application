package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CommWorkEffort;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CommWorkEffort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommWorkEffortRepository extends JpaRepository<CommWorkEffort, Long> {
}
