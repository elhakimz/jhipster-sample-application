package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ContactMechPposTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactMechPposType.class);
        ContactMechPposType contactMechPposType1 = new ContactMechPposType();
        contactMechPposType1.setId(1L);
        ContactMechPposType contactMechPposType2 = new ContactMechPposType();
        contactMechPposType2.setId(contactMechPposType1.getId());
        assertThat(contactMechPposType1).isEqualTo(contactMechPposType2);
        contactMechPposType2.setId(2L);
        assertThat(contactMechPposType1).isNotEqualTo(contactMechPposType2);
        contactMechPposType1.setId(null);
        assertThat(contactMechPposType1).isNotEqualTo(contactMechPposType2);
    }
}
