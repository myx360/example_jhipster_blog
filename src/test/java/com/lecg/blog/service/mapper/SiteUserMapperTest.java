package com.lecg.blog.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SiteUserMapperTest {

    private SiteUserMapper siteUserMapper;

    @BeforeEach
    public void setUp() {
        siteUserMapper = new SiteUserMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(siteUserMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(siteUserMapper.fromId(null)).isNull();
    }
}
