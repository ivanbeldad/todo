package com.rackian.todo.command;

import com.rackian.todo.model.Note;
import com.rackian.todo.service.NoteService;
import com.rackian.todo.util.MenuError;
import com.rackian.todo.util.MenuInfo;
import com.rackian.todo.util.MenuOption;
import com.rackian.todo.view.MenuView;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class DeleteNoteCommandSpec {

    private DeleteNoteCommand command;
    private MenuView view;
    private NoteService service;
    private List<Note> notes;

    @Before
    public void setUp() throws Exception {
        view = mock(MenuView.class);
        service = mock(NoteService.class);
        notes = new ArrayList<>(Arrays.asList(
                new Note("Note 1", "Content 1"),
                new Note("Note 2", "Content 2")
        ));
        command = new DeleteNoteCommand(view, service);
    }

    @Test
    public void whenDeleteNoteThenExecuteIt() throws Exception {
        doReturn(MenuOption.DELETE_NOTE).doReturn(MenuOption.EXIT).when(view).showMainMenu();
        when(view.askNumber(MenuInfo.DELETE_NOTE.toString())).thenReturn(2);
        when(service.notes()).thenReturn(notes);
        command.execute();
        verify(service, times(1)).delete(notes.get(1));
    }

    @Test
    public void whenIndexOutThenShowError() throws Exception {
        doReturn(MenuOption.DELETE_NOTE).doReturn(MenuOption.EXIT).when(view).showMainMenu();
        when(view.askNumber(MenuInfo.DELETE_NOTE.toString())).thenReturn(4);
        when(service.notes()).thenReturn(notes);
        command.execute();
        verify(view, times(1)).showError(MenuError.NOTE_DOESNT_EXISTS.toString());
    }

}
