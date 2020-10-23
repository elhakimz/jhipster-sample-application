package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class PartyAddressTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartyAddress.class);
        PartyAddress partyAddress1 = new PartyAddress();
        partyAddress1.setId(1L);
        PartyAddress partyAddress2 = new PartyAddress();
        partyAddress2.setId(partyAddress1.getId());
        assertThat(partyAddress1).isEqualTo(partyAddress2);
        partyAddress2.setId(2L);
        assertThat(partyAddress1).isNotEqualTo(partyAddress2);
        partyAddress1.setId(null);
        assertThat(partyAddress1).isNotEqualTo(partyAddress2);
    }
}
