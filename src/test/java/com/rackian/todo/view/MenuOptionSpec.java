package com.rackian.todo.view;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

public class MenuOptionsSpec {

    @Test
    public void whenMenuOptionThenShowCorrectly() throws Exception {
        assertThat("1. List all your notes.", equalTo(MenuOptions.valueOf("LIST_NOTES").toString()));
    }

    @Test
    public void whenGetTextThenTextIsReturned() throws Exception {
        assertThat("Create a new note.", equalTo(MenuOptions.valueOf("CREATE_NOTE").getText()));
    }

    @Test
    public void whenGetNumberThenNumberIsReturned() throws Exception {
        assertThat(3, equalTo(MenuOptions.valueOf("DELETE_NOTE").getNumber()));
    }

}
