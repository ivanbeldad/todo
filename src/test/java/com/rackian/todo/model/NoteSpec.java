package com.rackian.todo.model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

public class NoteSpec {

    private Note emptyNote;
    private Note note1;
    private Note note2;

    @Before
    public void setUp() throws Exception {
        emptyNote = new Note();
        note1 = new Note("title", "content");
        note2 = new Note("title", "content");
    }

    @Test
    public void whenEmptyConstructorThenTitleAndContentAreEmpty() throws Exception {
        assertThat(emptyNote.getTitle(), is(""));
        assertThat(emptyNote.getContent(), is(""));
    }

    @Test
    public void whenTitleAndContentConstructorThenTitleAndContentAreFilled() throws Exception {
        Note note = new Note("My title", "My content");
        assertThat(note.getTitle(), is("My title"));
        assertThat(note.getContent(), is("My content"));
    }

    @Test
    public void whenTitleIsSettedThenItChanges() throws Exception {
        emptyNote.setTitle("New title");
        assertThat(emptyNote.getTitle(), is("New title"));
    }

    @Test
    public void whenContentIsSettedThenItChanges() throws Exception {
        emptyNote.setContent("New content");
        assertThat(emptyNote.getContent(), is("New content"));
    }

    @Test
    public void whenSameObjectThenEquals() throws Exception {
        assertThat(note1, is(note1));
    }

    @Test
    public void whenDifferentObjectThenNotEquals() throws Exception {
        assertThat(note1, not(10));
    }

    @Test
    public void whenNullThenNotEquals() throws Exception {
        assertThat("Note not equals null", !note1.equals(null));
    }

    @Test
    public void whenNotesWithSameTitleAndContentAreComparedThenTheyAreEquals() throws Exception {
        assertThat("Note1 equals note2", note1.equals(note2));
        assertThat("Note2 equals note1", note2.equals(note1));
        assertThat(note1.hashCode(), is(note2.hashCode()));
    }

    @Test
    public void whenNotesWithDifferentTitleOrContentAreComparedThenTheyAreNotEquals() throws Exception {
        note2.setTitle("title different");
        assertThat("Note1 not equals note2 if different title", !note1.equals(note2));
        assertThat(note1.hashCode(), not(note2.hashCode()));
        note2.setTitle("title");
        note2.setContent("content different");
        assertThat("Note2 not equals note1 if different content", !note2.equals(note1));
        assertThat(note1.hashCode(), not(note2.hashCode()));
    }

    @Test
    public void whenToStringThenShowTitle() throws Exception {
        assertThat(note1.toString(), containsString(note1.getTitle()));
    }

    @Test
    public void whenToStringThenShowContent() throws Exception {
        assertThat(note1.toString(), containsString(note1.getContent()));
    }

}
