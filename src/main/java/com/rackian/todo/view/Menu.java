package com.rackian.todo.view;

import com.rackian.todo.service.NoteService;

import java.io.PrintStream;

public class Menu {

    private PrintStream printStream;
    private NoteService noteService;

    public Menu(PrintStream printStream, NoteService noteService) {
        this.printStream = printStream;
        this.noteService = noteService;
    }

    public void showOptions() {
        printStream.println(MenuOptions.LIST_NOTES);
        printStream.println(MenuOptions.CREATE_NOTE);
        printStream.println(MenuOptions.DELETE_NOTE);
    }

    public PrintStream getPrintStream() {
        return printStream;
    }

    public NoteService getNoteService() {
        return noteService;
    }
}