package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ContactMechLinkTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactMechLink.class);
        ContactMechLink contactMechLink1 = new ContactMechLink();
        contactMechLink1.setId(1L);
        ContactMechLink contactMechLink2 = new ContactMechLink();
        contactMechLink2.setId(contactMechLink1.getId());
        assertThat(contactMechLink1).isEqualTo(contactMechLink2);
        contactMechLink2.setId(2L);
        assertThat(contactMechLink1).isNotEqualTo(contactMechLink2);
        contactMechLink1.setId(null);
        assertThat(contactMechLink1).isNotEqualTo(contactMechLink2);
    }
}
