package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class FacilityRoleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FacilityRole.class);
        FacilityRole facilityRole1 = new FacilityRole();
        facilityRole1.setId(1L);
        FacilityRole facilityRole2 = new FacilityRole();
        facilityRole2.setId(facilityRole1.getId());
        assertThat(facilityRole1).isEqualTo(facilityRole2);
        facilityRole2.setId(2L);
        assertThat(facilityRole1).isNotEqualTo(facilityRole2);
        facilityRole1.setId(null);
        assertThat(facilityRole1).isNotEqualTo(facilityRole2);
    }
}
