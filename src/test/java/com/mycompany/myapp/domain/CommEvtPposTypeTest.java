package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class CommEvtPposTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommEvtPposType.class);
        CommEvtPposType commEvtPposType1 = new CommEvtPposType();
        commEvtPposType1.setId(1L);
        CommEvtPposType commEvtPposType2 = new CommEvtPposType();
        commEvtPposType2.setId(commEvtPposType1.getId());
        assertThat(commEvtPposType1).isEqualTo(commEvtPposType2);
        commEvtPposType2.setId(2L);
        assertThat(commEvtPposType1).isNotEqualTo(commEvtPposType2);
        commEvtPposType1.setId(null);
        assertThat(commEvtPposType1).isNotEqualTo(commEvtPposType2);
    }
}
