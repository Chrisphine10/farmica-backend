package com.bhachu.farmica.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.bhachu.farmica.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReworkDetailDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReworkDetailDTO.class);
        ReworkDetailDTO reworkDetailDTO1 = new ReworkDetailDTO();
        reworkDetailDTO1.setId(1L);
        ReworkDetailDTO reworkDetailDTO2 = new ReworkDetailDTO();
        assertThat(reworkDetailDTO1).isNotEqualTo(reworkDetailDTO2);
        reworkDetailDTO2.setId(reworkDetailDTO1.getId());
        assertThat(reworkDetailDTO1).isEqualTo(reworkDetailDTO2);
        reworkDetailDTO2.setId(2L);
        assertThat(reworkDetailDTO1).isNotEqualTo(reworkDetailDTO2);
        reworkDetailDTO1.setId(null);
        assertThat(reworkDetailDTO1).isNotEqualTo(reworkDetailDTO2);
    }
}
