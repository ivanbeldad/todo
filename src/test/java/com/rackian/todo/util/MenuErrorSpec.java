package com.rackian.todo.util;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MenuErrorSpec {

    @Test
    public void whenGetToStringThenGetMessage() throws Exception {
        String expected = "The input value is not a valid option. Try again.";
        assertThat(MenuError.valueOf("INVALID_OPTION").toString(), is(expected));
    }
}
