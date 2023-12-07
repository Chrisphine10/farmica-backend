package com.bhachu.farmica.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bhachu.farmica.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BatchDetailTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BatchDetail.class);
        BatchDetail batchDetail1 = new BatchDetail();
        batchDetail1.setId(1L);
        BatchDetail batchDetail2 = new BatchDetail();
        batchDetail2.setId(batchDetail1.getId());
        assertThat(batchDetail1).isEqualTo(batchDetail2);
        batchDetail2.setId(2L);
        assertThat(batchDetail1).isNotEqualTo(batchDetail2);
        batchDetail1.setId(null);
        assertThat(batchDetail1).isNotEqualTo(batchDetail2);
    }
}
