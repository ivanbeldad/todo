package com.rackian.todo.service;

import com.rackian.todo.model.Note;
import com.rackian.todo.repository.NoteRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class NoteServiceImplSpec {

    private NoteServiceImpl noteService;
    private NoteRepository noteRepository;
    private Collection<Note> notes;
    private Note note;

    @Before
    public void setUp() throws Exception {
        noteRepository = mock(NoteRepository.class);
        noteService = new NoteServiceImpl(noteRepository);
        note = new Note("Note 1", "Content");
        Note note2 = new Note("Note 2", "Content");
        Note note3 = new Note("Note 3", "Content");
        notes = Arrays.asList(note, note2, note3);
    }

    @Test
    public void whenNotesThenReturnAllNotes() throws Exception {
        when(noteRepository.findAll()).thenReturn(notes);
        assertThat(noteService.notes(), is(notes));
    }

    @Test
    public void whenCreateNoteThenNoteIsCreated() throws Exception {
        noteService.create(note);
        verify(noteRepository, times(1)).save(note);
    }

    @Test
    public void whenNoteIsCreatedThenIsReturned() throws Exception {
        when(noteRepository.save(note)).thenReturn(note);
        assertThat(noteService.create(note), is(note));
    }

    @Test
    public void whenDeleteThenNoteDoesntExistsAnymore() throws Exception {
        noteService.delete(note);
        verify(noteRepository, times(1)).delete(note);
    }

}
