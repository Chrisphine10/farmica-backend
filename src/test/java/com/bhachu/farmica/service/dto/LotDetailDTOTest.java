package com.bhachu.farmica.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.bhachu.farmica.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LotDetailDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LotDetailDTO.class);
        LotDetailDTO lotDetailDTO1 = new LotDetailDTO();
        lotDetailDTO1.setId(1L);
        LotDetailDTO lotDetailDTO2 = new LotDetailDTO();
        assertThat(lotDetailDTO1).isNotEqualTo(lotDetailDTO2);
        lotDetailDTO2.setId(lotDetailDTO1.getId());
        assertThat(lotDetailDTO1).isEqualTo(lotDetailDTO2);
        lotDetailDTO2.setId(2L);
        assertThat(lotDetailDTO1).isNotEqualTo(lotDetailDTO2);
        lotDetailDTO1.setId(null);
        assertThat(lotDetailDTO1).isNotEqualTo(lotDetailDTO2);
    }
}
