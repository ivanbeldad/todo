package com.rackian.todo.repository;

import com.rackian.todo.exception.NoteAlreadyExistsException;
import com.rackian.todo.exception.NoteDoesntExistsException;
import com.rackian.todo.model.Note;

public interface NoteRepository {

    Note save(Note note) throws NoteAlreadyExistsException;

    boolean exists(Note note);

    Iterable<Note> findAll();

    long count();

    void delete(Note note) throws NoteDoesntExistsException;

    void deleteAll();

}
