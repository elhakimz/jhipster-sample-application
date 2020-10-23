package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class CasusTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Casus.class);
        Casus casus1 = new Casus();
        casus1.setId(1L);
        Casus casus2 = new Casus();
        casus2.setId(casus1.getId());
        assertThat(casus1).isEqualTo(casus2);
        casus2.setId(2L);
        assertThat(casus1).isNotEqualTo(casus2);
        casus1.setId(null);
        assertThat(casus1).isNotEqualTo(casus2);
    }
}
