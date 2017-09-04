package com.rackian.todo.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rackian.todo.model.Note;
import com.rackian.todo.util.TypeReferenceListNote;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class JsonNoteRepositorySpec {

    private List<Note> notes;
    private Note note;
    private Note otherNote;
    private File file;
    private ObjectMapper mapper;
    private JsonNoteRepository jsonNoteRepository;
    private TypeReference typeReference;

    @Before
    public void setUp() throws Exception {
        note = new Note("Title", "Content");
        otherNote = new Note("Other", "Content");
        notes = new ArrayList<>(Arrays.asList(note, otherNote));
        file = mock(File.class);
        mapper = mock(ObjectMapper.class);
        typeReference = new TypeReferenceListNote();
        jsonNoteRepository = new JsonNoteRepository(file, mapper, typeReference);
        when(mapper.readValue(file, typeReference)).thenReturn(notes);
    }

    @Test
    public void whenSaveThenMapperWriteIsCalled() throws Exception {
        Note newNote = new Note("New", "Note");
        jsonNoteRepository.save(newNote);
        List<Note> result = new ArrayList<>();
        notes.forEach(result::add);
        result.add(newNote);
        verify(mapper, times(1)).writeValue(file, result);
    }

    @Test
    public void whenSaveThenReturnNote() throws Exception {
        assertThat(jsonNoteRepository.save(note), is(note));
    }

    @Test
    public void whenDeleteThenRemoveFromFile() throws Exception {
        when(mapper.readValue(file, typeReference)).thenReturn(notes);
        jsonNoteRepository.delete(note);
        verify(mapper, times(1))
                .writeValue(file, new ArrayList<>(Collections.singletonList(otherNote)));
    }

}
