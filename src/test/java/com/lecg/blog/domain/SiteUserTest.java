package com.lecg.blog.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lecg.blog.web.rest.TestUtil;

public class SiteUserTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SiteUser.class);
        SiteUser siteUser1 = new SiteUser();
        siteUser1.setId(1L);
        SiteUser siteUser2 = new SiteUser();
        siteUser2.setId(siteUser1.getId());
        assertThat(siteUser1).isEqualTo(siteUser2);
        siteUser2.setId(2L);
        assertThat(siteUser1).isNotEqualTo(siteUser2);
        siteUser1.setId(null);
        assertThat(siteUser1).isNotEqualTo(siteUser2);
    }
}
