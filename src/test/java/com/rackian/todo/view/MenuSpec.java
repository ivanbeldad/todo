package com.rackian.todo.view;

import com.rackian.todo.exception.NoteAlreadyExistsException;
import com.rackian.todo.model.Note;
import com.rackian.todo.service.NoteService;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.*;

import java.io.*;
import java.util.*;

public class MenuSpec {

    private Menu menu;
    private NoteService noteService;
    private OutputStream outputStream;
    private InputStream inputStream;
    private Collection<Note> notes;
    private Note note;
    private List<String> inputs;

    @Before
    public void setUp() throws Exception {
        note = new Note("My title", "My content");
        Note note2 = new Note("My title 2", "My content 2");
        Note note3 = new Note("My title 3", "My content 3");
        notes = Arrays.asList(note, note2, note3);

        outputStream = new ByteArrayOutputStream();

        noteService = mock(NoteService.class);
        when(noteService.notes()).thenReturn(notes);
        when(noteService.create(note)).thenReturn(note);

        setOption(MenuOption.LIST_NOTES);
    }

    private void setOption(MenuOption option) {
        inputs = new ArrayList<>();
        inputs.add(Integer.toString(option.getNumber()));
        if (option == MenuOption.CREATE_NOTE) {
            inputs.add(note.getTitle());
            inputs.add(note.getContent());
        }
        initStream();
    }

    private void addInput(String input) {
        inputs.add(input);
        initStream();
    }

    private void initStream() {
        String result = inputs.stream().reduce("", (s1, s2) -> s1 + '\n' + s2);
        result = result.replaceFirst("\n", "");
        inputStream = new ByteArrayInputStream(result.getBytes());
        menu = new Menu(outputStream, inputStream, noteService);
    }

    @Test
    public void whenShowOptionsThenListNotesOptionIsShowed() throws Exception {
        menu.init();
        String showed = outputStream.toString();
        assertThat(showed, containsString(MenuOption.valueOf("LIST_NOTES").toString()));
    }

    @Test
    public void whenShowOptionsThenCreateNotesOptionIsShowed() throws Exception {
        menu.init();
        String showed = outputStream.toString();
        assertThat(showed, containsString(MenuOption.valueOf("CREATE_NOTE").toString()));
    }

    @Test
    public void whenShowOptionsThenDeleteNotesOptionIsShowed() throws Exception {
        menu.init();
        String showed = outputStream.toString();
        assertThat(showed, containsString(MenuOption.valueOf("DELETE_NOTE").toString()));
    }

    @Test
    public void whenCheckOptionThenInputIsReceived() throws Exception {
        setOption(MenuOption.CREATE_NOTE);
        MenuOption option = menu.checkOption();
        assertThat(option, is(MenuOption.CREATE_NOTE));
    }

    @Test
    public void whenAnotherOptionThenInputIsReceived() throws Exception {
        setOption(MenuOption.DELETE_NOTE);
        MenuOption option = menu.checkOption();
        assertThat(option, is(MenuOption.DELETE_NOTE));
    }

    @Test
    public void whenInvalidOptionThenShowErrorMesasage() throws Exception {
        inputStream = new ByteArrayInputStream("Invalid option".getBytes());
        menu = new Menu(outputStream, inputStream, noteService);
        menu.checkOption();
        assertThat(outputStream.toString(), containsString(Menu.ERROR_INVALID_OPTION));
    }

    @Test
    public void whenInitThenCheckOptionIsCalled() throws Exception {
        menu = spy(menu);
        menu.init();
        verify(menu, times(1)).checkOption();
    }

    @Test
    public void whenExecuteListNotesThenListsAreShowed() throws Exception {
        when(noteService.notes()).thenReturn(notes);

        setOption(MenuOption.LIST_NOTES);
        menu.init();

        assertThat(outputStream.toString(), containsString(notes.toArray()[0].toString()));
        assertThat(outputStream.toString(), containsString(notes.toArray()[1].toString()));
        assertThat(outputStream.toString(), containsString(notes.toArray()[2].toString()));
    }

    @Test
    public void whenCreateNoteThenShowWaitForTitle() throws Exception {
        setOption(MenuOption.CREATE_NOTE);
        menu.init();
        assertThat(outputStream.toString(), containsString(Menu.INPUT_TITLE_MESSAGE));
    }

    @Test
    public void whenCreateNoteAndInputThenShowWaitForContent() throws Exception {
        setOption(MenuOption.CREATE_NOTE);
        menu.init();
        assertThat(outputStream.toString(), containsString(Menu.INPUT_CONTENT_MESSAGE));
    }

    @Test
    public void whenCreateNoteThenServiceIsCalledWithThatValues() throws Exception {
        setOption(MenuOption.CREATE_NOTE);
        menu.init();
        verify(noteService, times(1)).create(note);
    }

    @Test
    public void whenCreateANoteAndAlreadyExistsThenShowErrorMessage() throws Exception {
        when(noteService.create(note)).thenThrow(new NoteAlreadyExistsException());
        setOption(MenuOption.CREATE_NOTE);
        try {
            menu.init();
        } catch (Exception ex) {
            // Not input when repeat init method
        }
        assertThat(outputStream.toString(), containsString(Menu.ERROR_NOTE_ALREADY_EXISTS));
    }

    @Test
    public void whenErrorCreatingNoteThenBackToInit() throws Exception {
        when(noteService.create(note)).thenThrow(new NoteAlreadyExistsException());
        setOption(MenuOption.CREATE_NOTE);
        menu = spy(menu);
        try {
            menu.init();
        } catch (Exception ex) {
            // Not input when repeat init method
        }
        verify(menu, times(2)).init();
    }

    @Test
    public void whenInvalidOptionThenShowError() throws Exception {
        setOption(MenuOption.DELETE_NOTE);
        addInput("invalid");
        menu.init();
        assertThat(outputStream.toString(), containsString(Menu.ERROR_INVALID_OPTION));
    }

    @Test
    public void whenInvalidNumberThenShowError() throws Exception {
        setOption(MenuOption.DELETE_NOTE);
        addInput("13");
        menu.init();
        assertThat(outputStream.toString(), containsString(Menu.ERROR_INVALID_OPTION));
    }

    @Test
    public void whenDeleteNoteThenNotesAreShowed() throws Exception {
        setOption(MenuOption.DELETE_NOTE);
        addInput("1");
        menu.init();
        notes.forEach(note -> assertThat(outputStream.toString(), containsString(note.getTitle())));
    }

    @Test
    public void whenDeleteOneNoteThenShowItWasDeleted() throws Exception {
        setOption(MenuOption.DELETE_NOTE);
        addInput("1");
        menu.init();
        assertThat(outputStream.toString(), containsString(Menu.NOTE_DELETED_MESSAGE));
    }

}
