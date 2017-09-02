package com.rackian.todo.view;

import com.rackian.todo.exception.MenuOptionDoesntExistsException;
import com.rackian.todo.exception.NoteAlreadyExistsException;
import com.rackian.todo.exception.NoteDoesntExistsException;
import com.rackian.todo.model.Note;
import com.rackian.todo.service.NoteService;
import com.rackian.todo.util.Command;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.*;

public class Menu {

    final public static String CHOOSE_MESSAGE = "Choose one of the following options:";
    final public static String NOTE_DELETED_MESSAGE = "Note deleted.";
    final public static String INPUT_TITLE_MESSAGE = "Insert the title:";
    final public static String INPUT_CONTENT_MESSAGE = "Insert the content:";
    final public static String ERROR_INVALID_OPTION = "The input value is not a valid option. Try again.";
    final public static String ERROR_NOTE_ALREADY_EXISTS = "The note already exists.";
    final public static String ERROR_NOTE_DOESNT_EXISTS = "The note doesnt exists.";

    private Scanner scanner;
    private PrintStream printStream;
    private NoteService noteService;

    public Menu(OutputStream outputStream, InputStream inputStream, NoteService noteService) {
        scanner = new Scanner(inputStream);
        printStream = new PrintStream(outputStream);
        this.noteService = noteService;
    }

    private void showOptions() {
        printStream.println(CHOOSE_MESSAGE);
        printStream.println(MenuOption.LIST_NOTES);
        printStream.println(MenuOption.CREATE_NOTE);
        printStream.println(MenuOption.DELETE_NOTE);
    }

    // TODO: Make private
    public MenuOption checkOption() {
        try {
            return MenuOption.getByNumber(Integer.parseInt(scanner.nextLine()));
        } catch (MenuOptionDoesntExistsException|NumberFormatException e) {
            printStream.println(ERROR_INVALID_OPTION);
            return null;
        }
    }

    private void executeOptionCommand(MenuOption option) {
        Map<MenuOption, Command> commands = new HashMap<>();
        commands.put(MenuOption.LIST_NOTES, () -> {
            Collection<Note> notes = noteService.notes();
            notes.forEach(note -> printStream.println(note));
        });
        commands.put(MenuOption.CREATE_NOTE, () -> {
            printStream.println(INPUT_TITLE_MESSAGE);
            String title = scanner.nextLine();
            printStream.println(INPUT_CONTENT_MESSAGE);
            String content = scanner.nextLine();
            try {
                noteService.create(new Note(title, content));
            } catch (NoteAlreadyExistsException e) {
                printStream.println(ERROR_NOTE_ALREADY_EXISTS);
                init();
            }
        });
        commands.put(MenuOption.DELETE_NOTE, () -> {
            Collection<Note> notes = noteService.notes();
            Map<Integer, Command> deleteCommands = new HashMap<>();
            int index = 1;
            for (Note currentNote : notes) {
                printStream.println(index + ". " + currentNote.getTitle());
                deleteCommands.put(index, () -> {
                    try {
                        noteService.delete(currentNote);
                        printStream.println(NOTE_DELETED_MESSAGE);
                    } catch (NoteDoesntExistsException e) {
                        printStream.println(ERROR_NOTE_DOESNT_EXISTS);
                    }
                });
                index++;
            }
            try {
                Integer selectedNote = Integer.parseInt(scanner.nextLine());
                deleteCommands.get(selectedNote).execute();
            } catch (NumberFormatException|NullPointerException e) {
                printStream.println(ERROR_INVALID_OPTION);
            }
        });
        commands.get(option).execute();
    }

    public void init() {
        showOptions();
        MenuOption option = checkOption();
        executeOptionCommand(option);
    }

}
