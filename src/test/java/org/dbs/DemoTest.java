package org.dbs;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DemoTest {

    @Test
    void helloWorld() {
        assertThat(new Demo().helloWorld()).isEqualTo("Hello world!");
    }
}