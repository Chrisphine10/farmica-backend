package com.bhachu.farmica.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bhachu.farmica.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StyleReportTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StyleReport.class);
        StyleReport styleReport1 = new StyleReport();
        styleReport1.setId(1L);
        StyleReport styleReport2 = new StyleReport();
        styleReport2.setId(styleReport1.getId());
        assertThat(styleReport1).isEqualTo(styleReport2);
        styleReport2.setId(2L);
        assertThat(styleReport1).isNotEqualTo(styleReport2);
        styleReport1.setId(null);
        assertThat(styleReport1).isNotEqualTo(styleReport2);
    }
}
