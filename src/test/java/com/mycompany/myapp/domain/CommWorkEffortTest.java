package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class CommWorkEffortTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommWorkEffort.class);
        CommWorkEffort commWorkEffort1 = new CommWorkEffort();
        commWorkEffort1.setId(1L);
        CommWorkEffort commWorkEffort2 = new CommWorkEffort();
        commWorkEffort2.setId(commWorkEffort1.getId());
        assertThat(commWorkEffort1).isEqualTo(commWorkEffort2);
        commWorkEffort2.setId(2L);
        assertThat(commWorkEffort1).isNotEqualTo(commWorkEffort2);
        commWorkEffort1.setId(null);
        assertThat(commWorkEffort1).isNotEqualTo(commWorkEffort2);
    }
}
