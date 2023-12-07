package com.bhachu.farmica.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bhachu.farmica.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VariableDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VariableData.class);
        VariableData variableData1 = new VariableData();
        variableData1.setId(1L);
        VariableData variableData2 = new VariableData();
        variableData2.setId(variableData1.getId());
        assertThat(variableData1).isEqualTo(variableData2);
        variableData2.setId(2L);
        assertThat(variableData1).isNotEqualTo(variableData2);
        variableData1.setId(null);
        assertThat(variableData1).isNotEqualTo(variableData2);
    }
}
