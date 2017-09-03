package com.rackian.todo.repository;

import com.rackian.todo.exception.NoteAlreadyExistsException;
import com.rackian.todo.exception.NoteDoesntExistsException;
import com.rackian.todo.model.Note;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CollectionNoteRepositorySpec {

    private List<Note> notes;
    private NoteRepository repository;
    private Note note;
    private Note noteDifferent;

    @Before
    public void setUp() throws Exception {
        notes = new ArrayList<>();
        repository = new CollectionNoteRepository(notes);
        note = new Note("One note", "Content of the note");
        noteDifferent = new Note("Other title", "Other content");
    }

    @Test
    public void whenSaveThenIsStored() throws Exception {
        repository.save(note);
        assertThat(notes, hasItem(note));
    }

    @Test
    public void whenSaveThenTheNoteIsReturned() throws Exception {
        assertThat(note, equalTo(repository.save(note)));
    }

    @Test(expected = NoteAlreadyExistsException.class)
    public void whenSaveANoteThatAlreadyExistsThenNoteAlreadyExistsExceptionIsThrowned() throws Exception {
        repository.save(note);
        repository.save(note);
    }

    @Test
    public void whenNoteExistsThenReturnTrue() throws Exception {
        repository.save(note);
        assertThat(repository.exists(note), is(true));
    }

    @Test
    public void whenNoteNotExistsThenReturnFalse() throws Exception {
        repository.save(noteDifferent);
        assertThat(repository.exists(note), is(false));
    }

    @Test
    public void whenFindAllThenAllNotesAreReturned() throws Exception {
        repository.save(note);
        repository.save(noteDifferent);
        assertThat(repository.findAll(), containsInAnyOrder(note, noteDifferent));
    }

    @Test
    public void whenCountWithoutNotesThen0IsReturned() throws Exception {
        assertThat(repository.count(), is(0L));
    }

    @Test
    public void whenCountThenTheNumberOfNotesIsReturned() throws Exception {
        repository.save(note);
        assertThat(repository.count(), is(1L));
        repository.save(noteDifferent);
        assertThat(repository.count(), is(2L));
    }

    @Test
    public void whenNoteIsDeletedThenItIsRemoved() throws Exception {
        notes.add(note);
        notes.add(noteDifferent);
        repository.delete(note);
        assertThat(repository.findAll(), not(hasItem(note)));
    }

    @Test(expected = NoteDoesntExistsException.class)
    public void whenNotExistingNoteIsDeletedThenThrowNoteDoesntExistsException() throws Exception {
        repository.delete(note);
    }

    @Test
    public void whenDeleteAllIfEmptyThenEmpty() throws Exception {
        repository.deleteAll();
        assertThat(notes, empty());
    }

    @Test
    public void whenDeleteAllThenAllNotesAreRemoved() throws Exception {
        notes.add(note);
        notes.add(noteDifferent);
        repository.deleteAll();
        assertThat(notes, empty());
    }

}
