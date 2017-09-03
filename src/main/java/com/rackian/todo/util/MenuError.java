package com.rackian.todo.util;

public enum MenuError {

    INVALID_OPTION("The input value is not a valid option. Try again."),
    NOTE_ALREADY_EXISTS("The note already exists."),
    NOTE_DOESNT_EXISTS("The note doesnt exists.");

    private String message;

    MenuError(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

}
