package com.rackian.todo.util;

public enum MenuInfo {

    CHOOSE_OPTION("Choose one of the following options:"),
    INSERT_TITLE("Insert the title:"),
    INSERT_CONTENT("Insert the content:"),
    DELETE_NOTE("Choose one to delete it:"),
    NOTE_DELETED("Note deleted.");

    private String message;

    MenuInfo(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

}
