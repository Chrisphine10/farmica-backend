package com.bhachu.farmica.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.bhachu.farmica.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StyleReportDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StyleReportDTO.class);
        StyleReportDTO styleReportDTO1 = new StyleReportDTO();
        styleReportDTO1.setId(1L);
        StyleReportDTO styleReportDTO2 = new StyleReportDTO();
        assertThat(styleReportDTO1).isNotEqualTo(styleReportDTO2);
        styleReportDTO2.setId(styleReportDTO1.getId());
        assertThat(styleReportDTO1).isEqualTo(styleReportDTO2);
        styleReportDTO2.setId(2L);
        assertThat(styleReportDTO1).isNotEqualTo(styleReportDTO2);
        styleReportDTO1.setId(null);
        assertThat(styleReportDTO1).isNotEqualTo(styleReportDTO2);
    }
}
