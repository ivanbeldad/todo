package com.rackian.todo.util;

import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PrettyObjectMapperSpec {

    @Test
    public void whenCreatedThenIndentIsEnabled() throws Exception {
        assertThat(new PrettyObjectMapper().isEnabled(SerializationFeature.INDENT_OUTPUT), is(true));
    }

}
