package com.bhachu.farmica.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.bhachu.farmica.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SalesDetailDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalesDetailDTO.class);
        SalesDetailDTO salesDetailDTO1 = new SalesDetailDTO();
        salesDetailDTO1.setId(1L);
        SalesDetailDTO salesDetailDTO2 = new SalesDetailDTO();
        assertThat(salesDetailDTO1).isNotEqualTo(salesDetailDTO2);
        salesDetailDTO2.setId(salesDetailDTO1.getId());
        assertThat(salesDetailDTO1).isEqualTo(salesDetailDTO2);
        salesDetailDTO2.setId(2L);
        assertThat(salesDetailDTO1).isNotEqualTo(salesDetailDTO2);
        salesDetailDTO1.setId(null);
        assertThat(salesDetailDTO1).isNotEqualTo(salesDetailDTO2);
    }
}
