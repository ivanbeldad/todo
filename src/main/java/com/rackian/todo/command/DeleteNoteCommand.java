package com.rackian.todo.command;

import com.rackian.todo.exception.NoteDoesntExistsException;
import com.rackian.todo.model.Note;
import com.rackian.todo.service.NoteService;
import com.rackian.todo.util.MenuError;
import com.rackian.todo.util.MenuInfo;
import com.rackian.todo.view.MenuView;

public class DeleteNoteCommand implements Command {

    private MenuView menuView;
    private NoteService noteService;

    public DeleteNoteCommand(MenuView menuView, NoteService noteService) {
        this.menuView = menuView;
        this.noteService = noteService;
    }

    @Override
    public void execute() {
        try {
            int number = menuView.askNumber(MenuInfo.DELETE_NOTE.toString()) - 1;
            Note note = noteService.notes().get(number);
            noteService.delete(note);
        } catch (NoteDoesntExistsException|IndexOutOfBoundsException e) {
            menuView.showError(MenuError.NOTE_DOESNT_EXISTS.toString());
        }
    }

}
