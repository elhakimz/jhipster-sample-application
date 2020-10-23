package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class FacilityRoleTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FacilityRoleType.class);
        FacilityRoleType facilityRoleType1 = new FacilityRoleType();
        facilityRoleType1.setId(1L);
        FacilityRoleType facilityRoleType2 = new FacilityRoleType();
        facilityRoleType2.setId(facilityRoleType1.getId());
        assertThat(facilityRoleType1).isEqualTo(facilityRoleType2);
        facilityRoleType2.setId(2L);
        assertThat(facilityRoleType1).isNotEqualTo(facilityRoleType2);
        facilityRoleType1.setId(null);
        assertThat(facilityRoleType1).isNotEqualTo(facilityRoleType2);
    }
}
