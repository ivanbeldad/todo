package com.rackian.todo.controller;

import com.rackian.todo.command.Commands;
import com.rackian.todo.exception.MenuOptionDoesntExistsException;
import com.rackian.todo.util.MenuOption;
import com.rackian.todo.view.MenuView;

import java.util.Collections;

public class MenuController {

    private MenuView menuView;
    private Commands<MenuOption> commands;

    public MenuController(MenuView menuView, Commands<MenuOption> commands) {
        this.menuView = menuView;
        this.commands = commands;
    }

    public void init() {
        try {
            MenuOption option = menuView.showMainMenu();
            commands.execute(option);
            init();
        } catch (MenuOptionDoesntExistsException|NumberFormatException|NullPointerException e) {
            init();
        } catch (RuntimeException e) {
            menuView.show(Collections.singletonList("Finished"));
        }
    }

}
