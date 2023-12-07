package com.bhachu.farmica.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.bhachu.farmica.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FarmicaReportDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FarmicaReportDTO.class);
        FarmicaReportDTO farmicaReportDTO1 = new FarmicaReportDTO();
        farmicaReportDTO1.setId(1L);
        FarmicaReportDTO farmicaReportDTO2 = new FarmicaReportDTO();
        assertThat(farmicaReportDTO1).isNotEqualTo(farmicaReportDTO2);
        farmicaReportDTO2.setId(farmicaReportDTO1.getId());
        assertThat(farmicaReportDTO1).isEqualTo(farmicaReportDTO2);
        farmicaReportDTO2.setId(2L);
        assertThat(farmicaReportDTO1).isNotEqualTo(farmicaReportDTO2);
        farmicaReportDTO1.setId(null);
        assertThat(farmicaReportDTO1).isNotEqualTo(farmicaReportDTO2);
    }
}
