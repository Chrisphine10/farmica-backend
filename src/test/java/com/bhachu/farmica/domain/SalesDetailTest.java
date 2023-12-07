package com.bhachu.farmica.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bhachu.farmica.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SalesDetailTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalesDetail.class);
        SalesDetail salesDetail1 = new SalesDetail();
        salesDetail1.setId(1L);
        SalesDetail salesDetail2 = new SalesDetail();
        salesDetail2.setId(salesDetail1.getId());
        assertThat(salesDetail1).isEqualTo(salesDetail2);
        salesDetail2.setId(2L);
        assertThat(salesDetail1).isNotEqualTo(salesDetail2);
        salesDetail1.setId(null);
        assertThat(salesDetail1).isNotEqualTo(salesDetail2);
    }
}
