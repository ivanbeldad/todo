package com.rackian.todo.command;

import com.rackian.todo.exception.NoteAlreadyExistsException;
import com.rackian.todo.model.Note;
import com.rackian.todo.service.NoteService;
import com.rackian.todo.util.MenuError;
import com.rackian.todo.util.MenuInfo;
import com.rackian.todo.util.MenuOption;
import com.rackian.todo.view.MenuView;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class CreateNoteCommandSpec {

    private CreateNoteCommand createNoteCommand;
    private MenuView view;
    private NoteService service;
    private Note note;

    @Before
    public void setUp() throws Exception {
        note = new Note("My title", "My content");
        service = mock(NoteService.class);
        when(service.create(note)).thenReturn(note);
        view = mock(MenuView.class);
        createNoteCommand = new CreateNoteCommand(view, service);
    }

    @Test
    public void whenCreateNoteThenExecuteIt() throws Exception {
        doReturn(MenuOption.CREATE_NOTE).doReturn(MenuOption.EXIT).when(view).showMainMenu();
        when(view.ask(MenuInfo.INSERT_TITLE.toString())).thenReturn(note.getTitle());
        when(view.ask(MenuInfo.INSERT_CONTENT.toString())).thenReturn(note.getContent());
        createNoteCommand.execute();
        verify(service, times(1)).create(note);
    }

    @Test
    public void whenErrorThenShowIt() throws Exception {
        when(view.ask(MenuInfo.INSERT_TITLE.toString())).thenReturn(note.getTitle());
        when(view.ask(MenuInfo.INSERT_CONTENT.toString())).thenReturn(note.getContent());
        when(service.create(note)).thenThrow(NoteAlreadyExistsException.class);
        createNoteCommand.execute();
        verify(view, times(1)).showError(MenuError.NOTE_ALREADY_EXISTS.toString());
    }

}
