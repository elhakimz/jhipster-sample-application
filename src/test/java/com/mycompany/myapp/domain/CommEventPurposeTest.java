package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class CommEventPurposeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommEventPurpose.class);
        CommEventPurpose commEventPurpose1 = new CommEventPurpose();
        commEventPurpose1.setId(1L);
        CommEventPurpose commEventPurpose2 = new CommEventPurpose();
        commEventPurpose2.setId(commEventPurpose1.getId());
        assertThat(commEventPurpose1).isEqualTo(commEventPurpose2);
        commEventPurpose2.setId(2L);
        assertThat(commEventPurpose1).isNotEqualTo(commEventPurpose2);
        commEventPurpose1.setId(null);
        assertThat(commEventPurpose1).isNotEqualTo(commEventPurpose2);
    }
}
