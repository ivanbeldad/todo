package com.rackian.todo.repository;

import com.rackian.todo.exception.NoteAlreadyExistsException;
import com.rackian.todo.exception.NoteDoesntExistsException;
import com.rackian.todo.model.Note;

import java.util.Collection;
import java.util.stream.Collectors;

public class CollectionNoteRepository implements NoteRepository {

    private Collection<Note> notes;

    public CollectionNoteRepository(Collection<Note> notes) {
        this.notes = notes;
    }

    @Override
    public Note save(Note note) throws NoteAlreadyExistsException {
        if (this.exists(note)) {
            throw new NoteAlreadyExistsException();
        }
        notes.add(note);
        return note;
    }

    @Override
    public boolean exists(Note note) {
        return notes.stream().anyMatch(n -> n.equals(note));
    }

    @Override
    public Iterable<Note> findAll() {
        return notes;
    }

    @Override
    public long count() {
        return notes.size();
    }

    @Override
    public void delete(Note note) throws NoteDoesntExistsException {
        int count = notes.size();
        notes = notes.stream().filter(n -> !(n.equals(note))).collect(Collectors.toList());
        if (notes.size() == count) {
            throw new NoteDoesntExistsException();
        }
    }

    @Override
    public void deleteAll() {
        notes.clear();
    }

}
