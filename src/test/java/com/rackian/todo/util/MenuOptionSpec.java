package com.rackian.todo.util;

import com.rackian.todo.exception.MenuOptionDoesntExistsException;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class MenuOptionSpec {

    @Test
    public void whenMenuOptionThenShowCorrectly() throws Exception {
        assertThat("1. List all your notes.", equalTo(MenuOption.valueOf("LIST_NOTES").toString()));
    }

    @Test
    public void whenGetTextThenTextIsReturned() throws Exception {
        assertThat("Create a new note.", equalTo(MenuOption.valueOf("CREATE_NOTE").getText()));
    }

    @Test
    public void whenGetNumberThenNumberIsReturned() throws Exception {
        assertThat(3, equalTo(MenuOption.valueOf("DELETE_NOTE").getNumber()));
    }

    @Test
    public void whenGetByNumberThenOptionIsReturned() throws Exception {
        assertThat(MenuOption.getByNumber(1), is(MenuOption.LIST_NOTES));
    }

    @Test
    public void whenGetByNumberThenOtherOptionIsReturned() throws Exception {
        assertThat(MenuOption.getByNumber(3), is(MenuOption.DELETE_NOTE));
    }

    @Test(expected = MenuOptionDoesntExistsException.class)
    public void whenGetByNumberAndDoesntExistsThenThrowAndException() throws Exception {
        MenuOption.getByNumber(5);
    }

}
