package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CommEventPurpose;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CommEventPurpose entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommEventPurposeRepository extends JpaRepository<CommEventPurpose, Long> {
}
