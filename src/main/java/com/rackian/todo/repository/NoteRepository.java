package com.rackian.todo.repository;

import com.rackian.todo.exception.NoteAlreadyExistsException;
import com.rackian.todo.exception.NoteDoesntExistsException;
import com.rackian.todo.model.Note;

public interface NoteRepository {

    Note save(Note note) throws NoteAlreadyExistsException;

//    Iterable<Note> save(Iterable<Note> iterable);

//    Note findOne(String id);

    boolean exists(Note note);

    Iterable<Note> findAll();

//    Iterable<Note> findAll(Iterable<String> iterable);

    long count();

//    void delete(String id);

    void delete(Note note) throws NoteDoesntExistsException;

//    void delete(Iterable<Note> iterable);

    void deleteAll();

}
