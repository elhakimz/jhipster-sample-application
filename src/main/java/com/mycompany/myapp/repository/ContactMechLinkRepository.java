package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ContactMechLink;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ContactMechLink entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactMechLinkRepository extends JpaRepository<ContactMechLink, Long> {
}
