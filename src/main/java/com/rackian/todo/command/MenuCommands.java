package com.rackian.todo.command;

import com.rackian.todo.service.NoteService;
import com.rackian.todo.util.MenuOption;
import com.rackian.todo.view.MenuView;

import java.util.Map;

public class MenuCommands implements Commands<MenuOption> {

    private NoteService service;
    private MenuView view;
    private Map<MenuOption, Command> commands;

    public MenuCommands(Map<MenuOption, Command> commands, NoteService service, MenuView view) {
        this.service = service;
        this.view = view;
        this.commands = commands;
        init();
    }

    private void init() {
        commands.put(MenuOption.LIST_NOTES, new ListNotesCommand(view, service));
        commands.put(MenuOption.CREATE_NOTE, new CreateNoteCommand(view, service));
        commands.put(MenuOption.DELETE_NOTE, new DeleteNoteCommand(view, service));
        commands.put(MenuOption.EXIT, new ExitCommand(view));
    }

    @Override
    public void execute(MenuOption key) throws RuntimeException {
        commands.get(key).execute();
    }

}
