package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class PartyContactMechPposTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartyContactMechPpos.class);
        PartyContactMechPpos partyContactMechPpos1 = new PartyContactMechPpos();
        partyContactMechPpos1.setId(1L);
        PartyContactMechPpos partyContactMechPpos2 = new PartyContactMechPpos();
        partyContactMechPpos2.setId(partyContactMechPpos1.getId());
        assertThat(partyContactMechPpos1).isEqualTo(partyContactMechPpos2);
        partyContactMechPpos2.setId(2L);
        assertThat(partyContactMechPpos1).isNotEqualTo(partyContactMechPpos2);
        partyContactMechPpos1.setId(null);
        assertThat(partyContactMechPpos1).isNotEqualTo(partyContactMechPpos2);
    }
}
