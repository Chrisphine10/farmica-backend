package com.bhachu.farmica.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.bhachu.farmica.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PackingZoneDetailDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PackingZoneDetailDTO.class);
        PackingZoneDetailDTO packingZoneDetailDTO1 = new PackingZoneDetailDTO();
        packingZoneDetailDTO1.setId(1L);
        PackingZoneDetailDTO packingZoneDetailDTO2 = new PackingZoneDetailDTO();
        assertThat(packingZoneDetailDTO1).isNotEqualTo(packingZoneDetailDTO2);
        packingZoneDetailDTO2.setId(packingZoneDetailDTO1.getId());
        assertThat(packingZoneDetailDTO1).isEqualTo(packingZoneDetailDTO2);
        packingZoneDetailDTO2.setId(2L);
        assertThat(packingZoneDetailDTO1).isNotEqualTo(packingZoneDetailDTO2);
        packingZoneDetailDTO1.setId(null);
        assertThat(packingZoneDetailDTO1).isNotEqualTo(packingZoneDetailDTO2);
    }
}
