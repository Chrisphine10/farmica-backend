package com.bhachu.farmica.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.bhachu.farmica.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VariableDataDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VariableDataDTO.class);
        VariableDataDTO variableDataDTO1 = new VariableDataDTO();
        variableDataDTO1.setId(1L);
        VariableDataDTO variableDataDTO2 = new VariableDataDTO();
        assertThat(variableDataDTO1).isNotEqualTo(variableDataDTO2);
        variableDataDTO2.setId(variableDataDTO1.getId());
        assertThat(variableDataDTO1).isEqualTo(variableDataDTO2);
        variableDataDTO2.setId(2L);
        assertThat(variableDataDTO1).isNotEqualTo(variableDataDTO2);
        variableDataDTO1.setId(null);
        assertThat(variableDataDTO1).isNotEqualTo(variableDataDTO2);
    }
}
