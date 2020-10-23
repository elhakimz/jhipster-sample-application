package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Casus;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Casus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CasusRepository extends JpaRepository<Casus, Long> {
}
