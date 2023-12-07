package com.bhachu.farmica.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bhachu.farmica.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WarehouseDetailTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WarehouseDetail.class);
        WarehouseDetail warehouseDetail1 = new WarehouseDetail();
        warehouseDetail1.setId(1L);
        WarehouseDetail warehouseDetail2 = new WarehouseDetail();
        warehouseDetail2.setId(warehouseDetail1.getId());
        assertThat(warehouseDetail1).isEqualTo(warehouseDetail2);
        warehouseDetail2.setId(2L);
        assertThat(warehouseDetail1).isNotEqualTo(warehouseDetail2);
        warehouseDetail1.setId(null);
        assertThat(warehouseDetail1).isNotEqualTo(warehouseDetail2);
    }
}
