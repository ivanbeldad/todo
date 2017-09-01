package com.rackian.todo.service;

import com.rackian.todo.exception.NoteAlreadyExistsException;
import com.rackian.todo.exception.NoteDoesntExistsException;
import com.rackian.todo.model.Note;
import com.rackian.todo.repository.NoteRepository;

import java.util.ArrayList;
import java.util.Collection;

public class NoteServiceImpl implements NoteService {

    private NoteRepository repository;

    public NoteServiceImpl(NoteRepository repository) {
        this.repository = repository;
    }

    @Override
    public Collection<Note> notes() {
        Collection<Note> notes = new ArrayList<>();
        repository.findAll().forEach(notes::add);
        return notes;
    }

    @Override
    public Note create(Note note) throws NoteAlreadyExistsException {
        return repository.save(note);
    }

    @Override
    public void delete(Note note) throws NoteDoesntExistsException {
        repository.delete(note);
    }

}
