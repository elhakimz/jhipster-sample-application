package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CommEvent;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CommEvent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommEventRepository extends JpaRepository<CommEvent, Long> {
}
