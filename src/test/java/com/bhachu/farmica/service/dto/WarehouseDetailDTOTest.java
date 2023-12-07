package com.bhachu.farmica.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.bhachu.farmica.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WarehouseDetailDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WarehouseDetailDTO.class);
        WarehouseDetailDTO warehouseDetailDTO1 = new WarehouseDetailDTO();
        warehouseDetailDTO1.setId(1L);
        WarehouseDetailDTO warehouseDetailDTO2 = new WarehouseDetailDTO();
        assertThat(warehouseDetailDTO1).isNotEqualTo(warehouseDetailDTO2);
        warehouseDetailDTO2.setId(warehouseDetailDTO1.getId());
        assertThat(warehouseDetailDTO1).isEqualTo(warehouseDetailDTO2);
        warehouseDetailDTO2.setId(2L);
        assertThat(warehouseDetailDTO1).isNotEqualTo(warehouseDetailDTO2);
        warehouseDetailDTO1.setId(null);
        assertThat(warehouseDetailDTO1).isNotEqualTo(warehouseDetailDTO2);
    }
}
