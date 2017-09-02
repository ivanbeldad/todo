package com.rackian.todo.view;

import com.rackian.todo.exception.MenuOptionDoesntExistsException;

public enum MenuOption {

    LIST_NOTES(1, "List all your notes."),
    CREATE_NOTE(2, "Create a new note."),
    DELETE_NOTE(3, "Delete an existing note.");

    public static MenuOption getByNumber(int number) throws MenuOptionDoesntExistsException {
        for (MenuOption option : MenuOption.values()) {
            if (option.number == number) return option;
        }
        throw new MenuOptionDoesntExistsException();
    }

    private String text;
    private int number;

    MenuOption(int number, String text) {
        this.text = text;
        this.number = number;
    }

    @Override
    public String toString() {
        return Integer.toString(number) + ". " + text;
    }

    public String getText() {
        return text;
    }

    public int getNumber() {
        return number;
    }

}
