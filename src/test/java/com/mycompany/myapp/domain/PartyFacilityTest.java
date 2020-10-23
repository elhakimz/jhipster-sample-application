package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class PartyFacilityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartyFacility.class);
        PartyFacility partyFacility1 = new PartyFacility();
        partyFacility1.setId(1L);
        PartyFacility partyFacility2 = new PartyFacility();
        partyFacility2.setId(partyFacility1.getId());
        assertThat(partyFacility1).isEqualTo(partyFacility2);
        partyFacility2.setId(2L);
        assertThat(partyFacility1).isNotEqualTo(partyFacility2);
        partyFacility1.setId(null);
        assertThat(partyFacility1).isNotEqualTo(partyFacility2);
    }
}
