package com.lecg.blog.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lecg.blog.web.rest.TestUtil;

public class SiteUserDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SiteUserDTO.class);
        SiteUserDTO siteUserDTO1 = new SiteUserDTO();
        siteUserDTO1.setId(1L);
        SiteUserDTO siteUserDTO2 = new SiteUserDTO();
        assertThat(siteUserDTO1).isNotEqualTo(siteUserDTO2);
        siteUserDTO2.setId(siteUserDTO1.getId());
        assertThat(siteUserDTO1).isEqualTo(siteUserDTO2);
        siteUserDTO2.setId(2L);
        assertThat(siteUserDTO1).isNotEqualTo(siteUserDTO2);
        siteUserDTO1.setId(null);
        assertThat(siteUserDTO1).isNotEqualTo(siteUserDTO2);
    }
}
