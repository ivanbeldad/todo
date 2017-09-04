package com.rackian.todo.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rackian.todo.exception.NoteAlreadyExistsException;
import com.rackian.todo.exception.NoteDoesntExistsException;
import com.rackian.todo.model.Note;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JsonNoteRepository implements NoteRepository {

    private File file;
    private ObjectMapper mapper;
    private TypeReference typeReference;

    public JsonNoteRepository(File file, ObjectMapper mapper, TypeReference typeReference) {
        this.file = file;
        this.mapper = mapper;
        this.typeReference = typeReference;
    }

    @Override
    public Note save(Note note) throws NoteAlreadyExistsException {
        try {
            List<Note> notes = new ArrayList<>();
            findAll().forEach(notes::add);
            notes.add(note);
            mapper.writeValue(file, notes);
            return note;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean exists(Note note) {
        return false;
    }

    @Override
    public Iterable<Note> findAll() {
        try {
            List<Note> notes = mapper.readValue(file, typeReference);
            return notes;
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(Note note) throws NoteDoesntExistsException {
        List<Note> notes = new ArrayList<>();
        findAll().iterator().forEachRemaining(notes::add);
        int originalSize = notes.size();
        notes = notes.stream().filter(n -> !n.equals(note)).collect(Collectors.toList());
        if (originalSize == notes.size()) throw new NoteDoesntExistsException();
        try {
            mapper.writeValue(file, notes);
        } catch (IOException e) {
            //
        }
    }

    @Override
    public void deleteAll() {

    }

}
