package com.rackian.todo.command;

import com.rackian.todo.exception.ExitException;
import com.rackian.todo.view.MenuView;

import java.util.Collections;

public class ExitCommand implements Command {

    private MenuView view;

    public ExitCommand(MenuView view) {
        this.view = view;
    }

    @Override
    public void execute() {
        view.show(Collections.singletonList("Exiting application..."));
        throw new ExitException();
    }

}
