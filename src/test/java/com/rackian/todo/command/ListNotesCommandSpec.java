package com.rackian.todo.command;

import com.rackian.todo.model.Note;
import com.rackian.todo.service.NoteService;
import com.rackian.todo.util.MenuOption;
import com.rackian.todo.view.MenuView;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

public class ListNotesCommandSpec {

    private ListNotesCommand command;
    private NoteService service;
    private MenuView view;
    private List<Note> notes;

    @Before
    public void setUp() throws Exception {
        service = mock(NoteService.class);
        view = mock(MenuView.class);
        command = new ListNotesCommand(view, service);
        notes = new ArrayList<>(Arrays.asList(
                new Note("Note 1", "Content 1"),
                new Note("Note 2", "Content 2")
        ));
    }

    @Test
    public void whenListThenExecuteIt() throws Exception {
        doReturn(MenuOption.LIST_NOTES).doReturn(MenuOption.EXIT).when(view).showMainMenu();
        when(service.notes()).thenReturn(notes);
        command.execute();
        verify(service, times(1)).notes();
        verify(view, times(1))
                .show(notes.stream().map(Note::toString).collect(Collectors.toList()));
    }

}
