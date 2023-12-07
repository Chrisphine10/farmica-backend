package com.bhachu.farmica.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bhachu.farmica.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LotDetailTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LotDetail.class);
        LotDetail lotDetail1 = new LotDetail();
        lotDetail1.setId(1L);
        LotDetail lotDetail2 = new LotDetail();
        lotDetail2.setId(lotDetail1.getId());
        assertThat(lotDetail1).isEqualTo(lotDetail2);
        lotDetail2.setId(2L);
        assertThat(lotDetail1).isNotEqualTo(lotDetail2);
        lotDetail1.setId(null);
        assertThat(lotDetail1).isNotEqualTo(lotDetail2);
    }
}
