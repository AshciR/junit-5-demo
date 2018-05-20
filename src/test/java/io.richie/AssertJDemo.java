package io.richie;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AssertJDemo{

    @Test
    void stringAssertions(){
        assertThat("Foo").isNotBlank();
    }

}
