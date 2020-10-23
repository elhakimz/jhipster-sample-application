package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class PartyContactTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartyContact.class);
        PartyContact partyContact1 = new PartyContact();
        partyContact1.setId(1L);
        PartyContact partyContact2 = new PartyContact();
        partyContact2.setId(partyContact1.getId());
        assertThat(partyContact1).isEqualTo(partyContact2);
        partyContact2.setId(2L);
        assertThat(partyContact1).isNotEqualTo(partyContact2);
        partyContact1.setId(null);
        assertThat(partyContact1).isNotEqualTo(partyContact2);
    }
}
