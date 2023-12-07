package com.bhachu.farmica.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.bhachu.farmica.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BatchDetailDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BatchDetailDTO.class);
        BatchDetailDTO batchDetailDTO1 = new BatchDetailDTO();
        batchDetailDTO1.setId(1L);
        BatchDetailDTO batchDetailDTO2 = new BatchDetailDTO();
        assertThat(batchDetailDTO1).isNotEqualTo(batchDetailDTO2);
        batchDetailDTO2.setId(batchDetailDTO1.getId());
        assertThat(batchDetailDTO1).isEqualTo(batchDetailDTO2);
        batchDetailDTO2.setId(2L);
        assertThat(batchDetailDTO1).isNotEqualTo(batchDetailDTO2);
        batchDetailDTO1.setId(null);
        assertThat(batchDetailDTO1).isNotEqualTo(batchDetailDTO2);
    }
}
