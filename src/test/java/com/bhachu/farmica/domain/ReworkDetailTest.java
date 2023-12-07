package com.bhachu.farmica.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bhachu.farmica.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReworkDetailTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReworkDetail.class);
        ReworkDetail reworkDetail1 = new ReworkDetail();
        reworkDetail1.setId(1L);
        ReworkDetail reworkDetail2 = new ReworkDetail();
        reworkDetail2.setId(reworkDetail1.getId());
        assertThat(reworkDetail1).isEqualTo(reworkDetail2);
        reworkDetail2.setId(2L);
        assertThat(reworkDetail1).isNotEqualTo(reworkDetail2);
        reworkDetail1.setId(null);
        assertThat(reworkDetail1).isNotEqualTo(reworkDetail2);
    }
}
