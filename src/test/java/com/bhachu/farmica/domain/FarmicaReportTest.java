package com.bhachu.farmica.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bhachu.farmica.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FarmicaReportTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FarmicaReport.class);
        FarmicaReport farmicaReport1 = new FarmicaReport();
        farmicaReport1.setId(1L);
        FarmicaReport farmicaReport2 = new FarmicaReport();
        farmicaReport2.setId(farmicaReport1.getId());
        assertThat(farmicaReport1).isEqualTo(farmicaReport2);
        farmicaReport2.setId(2L);
        assertThat(farmicaReport1).isNotEqualTo(farmicaReport2);
        farmicaReport1.setId(null);
        assertThat(farmicaReport1).isNotEqualTo(farmicaReport2);
    }
}
