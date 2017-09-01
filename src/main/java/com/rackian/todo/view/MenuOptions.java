package com.rackian.todo.view;

public enum MenuOptions {

    LIST_NOTES(1, "List all your notes."),
    CREATE_NOTE(2, "Create a new note."),
    DELETE_NOTE(3, "Delete an existing note.");

    private String text;
    private int number;

    MenuOptions(int number, String text) {
        this.text = text;
        this.number = number;
    }

    @Override
    public String toString() {
        return "" + number + ". " + text;
    }

    public String getText() {
        return text;
    }

    public int getNumber() {
        return number;
    }

}
