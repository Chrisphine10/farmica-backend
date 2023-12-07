package com.bhachu.farmica.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bhachu.farmica.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReworkCommentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReworkComment.class);
        ReworkComment reworkComment1 = new ReworkComment();
        reworkComment1.setId(1L);
        ReworkComment reworkComment2 = new ReworkComment();
        reworkComment2.setId(reworkComment1.getId());
        assertThat(reworkComment1).isEqualTo(reworkComment2);
        reworkComment2.setId(2L);
        assertThat(reworkComment1).isNotEqualTo(reworkComment2);
        reworkComment1.setId(null);
        assertThat(reworkComment1).isNotEqualTo(reworkComment2);
    }
}
