package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ContactMechPposType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ContactMechPposType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactMechPposTypeRepository extends JpaRepository<ContactMechPposType, Long> {
}
