package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class CasusRoleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CasusRole.class);
        CasusRole casusRole1 = new CasusRole();
        casusRole1.setId(1L);
        CasusRole casusRole2 = new CasusRole();
        casusRole2.setId(casusRole1.getId());
        assertThat(casusRole1).isEqualTo(casusRole2);
        casusRole2.setId(2L);
        assertThat(casusRole1).isNotEqualTo(casusRole2);
        casusRole1.setId(null);
        assertThat(casusRole1).isNotEqualTo(casusRole2);
    }
}
