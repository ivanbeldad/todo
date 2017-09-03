package com.rackian.todo.util;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MenuInfoSpec {

    @Test
    public void whenToStringThenShowMessage() throws Exception {
        String expected = "Choose one of the following options:";
        assertThat(MenuInfo.CHOOSE_OPTION.toString(), is(expected));
    }

}
