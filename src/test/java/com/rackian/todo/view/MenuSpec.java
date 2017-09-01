package com.rackian.todo.view;

import com.rackian.todo.service.NoteService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class MenuSpec {

    private Menu menu;
    private NoteService noteService;
    private PrintStream printStream;
    private OutputStream outputStream;

    @Before
    public void setUp() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        noteService = context.getBean("noteService", NoteService.class);
        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
        menu = new Menu(printStream, noteService);
    }

    @Test
    public void whenShowOptionsThenListNotesOptionsIsShowed() throws Exception {
        menu.showOptions();
        String showed = outputStream.toString();
        assertThat(showed, containsString(MenuOptions.valueOf("LIST_NOTES").toString()));
    }

    @Test
    public void whenShowOptionsThenCreateNotesOptionsIsShowed() throws Exception {
        menu.showOptions();
        String showed = outputStream.toString();
        assertThat(showed, containsString(MenuOptions.valueOf("CREATE_NOTE").toString()));
    }

    @Test
    public void whenShowOptionsThenDeleteNotesOptionsIsShowed() throws Exception {
        menu.showOptions();
        String showed = outputStream.toString();
        assertThat(showed, containsString(MenuOptions.valueOf("DELETE_NOTE").toString()));
    }

}
