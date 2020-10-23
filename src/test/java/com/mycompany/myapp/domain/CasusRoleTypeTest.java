package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class CasusRoleTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CasusRoleType.class);
        CasusRoleType casusRoleType1 = new CasusRoleType();
        casusRoleType1.setId(1L);
        CasusRoleType casusRoleType2 = new CasusRoleType();
        casusRoleType2.setId(casusRoleType1.getId());
        assertThat(casusRoleType1).isEqualTo(casusRoleType2);
        casusRoleType2.setId(2L);
        assertThat(casusRoleType1).isNotEqualTo(casusRoleType2);
        casusRoleType1.setId(null);
        assertThat(casusRoleType1).isNotEqualTo(casusRoleType2);
    }
}
