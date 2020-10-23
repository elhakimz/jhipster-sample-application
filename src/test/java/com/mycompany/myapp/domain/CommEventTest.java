package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class CommEventTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommEvent.class);
        CommEvent commEvent1 = new CommEvent();
        commEvent1.setId(1L);
        CommEvent commEvent2 = new CommEvent();
        commEvent2.setId(commEvent1.getId());
        assertThat(commEvent1).isEqualTo(commEvent2);
        commEvent2.setId(2L);
        assertThat(commEvent1).isNotEqualTo(commEvent2);
        commEvent1.setId(null);
        assertThat(commEvent1).isNotEqualTo(commEvent2);
    }
}
