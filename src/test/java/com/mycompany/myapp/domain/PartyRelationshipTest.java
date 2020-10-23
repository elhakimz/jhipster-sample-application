package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class PartyRelationshipTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartyRelationship.class);
        PartyRelationship partyRelationship1 = new PartyRelationship();
        partyRelationship1.setId(1L);
        PartyRelationship partyRelationship2 = new PartyRelationship();
        partyRelationship2.setId(partyRelationship1.getId());
        assertThat(partyRelationship1).isEqualTo(partyRelationship2);
        partyRelationship2.setId(2L);
        assertThat(partyRelationship1).isNotEqualTo(partyRelationship2);
        partyRelationship1.setId(null);
        assertThat(partyRelationship1).isNotEqualTo(partyRelationship2);
    }
}
