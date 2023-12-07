package com.bhachu.farmica.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bhachu.farmica.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PackingZoneDetailTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PackingZoneDetail.class);
        PackingZoneDetail packingZoneDetail1 = new PackingZoneDetail();
        packingZoneDetail1.setId(1L);
        PackingZoneDetail packingZoneDetail2 = new PackingZoneDetail();
        packingZoneDetail2.setId(packingZoneDetail1.getId());
        assertThat(packingZoneDetail1).isEqualTo(packingZoneDetail2);
        packingZoneDetail2.setId(2L);
        assertThat(packingZoneDetail1).isNotEqualTo(packingZoneDetail2);
        packingZoneDetail1.setId(null);
        assertThat(packingZoneDetail1).isNotEqualTo(packingZoneDetail2);
    }
}
