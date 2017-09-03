package com.rackian.todo.command;

import com.rackian.todo.exception.NoteAlreadyExistsException;
import com.rackian.todo.model.Note;
import com.rackian.todo.service.NoteService;
import com.rackian.todo.util.MenuError;
import com.rackian.todo.util.MenuInfo;
import com.rackian.todo.view.MenuView;

public class CreateNoteCommand implements Command {

    private MenuView menuView;
    private NoteService noteService;

    public CreateNoteCommand(MenuView menuView, NoteService noteService) {
        this.menuView = menuView;
        this.noteService = noteService;
    }

    @Override
    public void execute() {
        String title = menuView.ask(MenuInfo.INSERT_TITLE.toString());
        String content = menuView.ask(MenuInfo.INSERT_CONTENT.toString());
        try {
            noteService.create(new Note(title, content));
        } catch (NoteAlreadyExistsException e) {
            menuView.showError(MenuError.NOTE_ALREADY_EXISTS.toString());
        }
    }

}
