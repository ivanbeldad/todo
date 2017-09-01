package com.rackian.todo.service;

import com.rackian.todo.exception.NoteAlreadyExistsException;
import com.rackian.todo.exception.NoteDoesntExistsException;
import com.rackian.todo.model.Note;

import java.util.Collection;

public interface NoteService {

    Collection<Note> notes();

    Note create(Note note) throws NoteAlreadyExistsException;

    void delete(Note note) throws NoteDoesntExistsException;

}
