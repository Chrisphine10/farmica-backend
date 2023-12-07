package com.bhachu.farmica.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.bhachu.farmica.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReworkCommentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReworkCommentDTO.class);
        ReworkCommentDTO reworkCommentDTO1 = new ReworkCommentDTO();
        reworkCommentDTO1.setId(1L);
        ReworkCommentDTO reworkCommentDTO2 = new ReworkCommentDTO();
        assertThat(reworkCommentDTO1).isNotEqualTo(reworkCommentDTO2);
        reworkCommentDTO2.setId(reworkCommentDTO1.getId());
        assertThat(reworkCommentDTO1).isEqualTo(reworkCommentDTO2);
        reworkCommentDTO2.setId(2L);
        assertThat(reworkCommentDTO1).isNotEqualTo(reworkCommentDTO2);
        reworkCommentDTO1.setId(null);
        assertThat(reworkCommentDTO1).isNotEqualTo(reworkCommentDTO2);
    }
}
