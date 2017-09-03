package com.rackian.todo.command;

import com.rackian.todo.model.Note;
import com.rackian.todo.service.NoteService;
import com.rackian.todo.view.MenuView;

import java.util.Collection;
import java.util.stream.Collectors;

public class ListNotesCommand implements Command {

    private MenuView menuView;
    private NoteService noteService;

    public ListNotesCommand(MenuView menuView, NoteService noteService) {
        this.menuView = menuView;
        this.noteService = noteService;
    }

    @Override
    public void execute() {
        Collection<Note> notes = noteService.notes();
        menuView.show(notes.stream().map(Note::toString).collect(Collectors.toList()));
    }

}
