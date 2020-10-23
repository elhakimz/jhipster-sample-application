package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class PartyIdentificationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartyIdentification.class);
        PartyIdentification partyIdentification1 = new PartyIdentification();
        partyIdentification1.setId(1L);
        PartyIdentification partyIdentification2 = new PartyIdentification();
        partyIdentification2.setId(partyIdentification1.getId());
        assertThat(partyIdentification1).isEqualTo(partyIdentification2);
        partyIdentification2.setId(2L);
        assertThat(partyIdentification1).isNotEqualTo(partyIdentification2);
        partyIdentification1.setId(null);
        assertThat(partyIdentification1).isNotEqualTo(partyIdentification2);
    }
}
