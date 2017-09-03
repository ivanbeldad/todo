package com.rackian.todo.service;

import com.rackian.todo.exception.NoteAlreadyExistsException;
import com.rackian.todo.exception.NoteDoesntExistsException;
import com.rackian.todo.model.Note;

import java.util.List;

public interface NoteService {

    List<Note> notes();

    Note create(Note note) throws NoteAlreadyExistsException;

    void delete(Note note) throws NoteDoesntExistsException;

}
