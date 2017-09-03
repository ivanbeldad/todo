package com.rackian.todo.controller;

import com.rackian.todo.command.Commands;
import com.rackian.todo.util.MenuOption;
import com.rackian.todo.view.MenuView;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;


public class MenuControllerSpec {

    private MenuView view;
    private MenuController controller;
    private Commands<MenuOption> commands;

    @Before
    public void setUp() throws Exception {
        view = mock(MenuView.class);

        commands = mock(Commands.class);
        controller = new MenuController(view, commands);
        doThrow(RuntimeException.class).when(commands).execute(MenuOption.EXIT);
    }

    @Test
    public void whenInitThenShowMainMenu() throws Exception {
        when(view.showMainMenu()).thenReturn(MenuOption.EXIT);
        controller.init();
        verify(view, times(1)).showMainMenu();
    }

    @Test
    public void whenAnyOptionThenShowMainMenu() throws Exception {
        when(view.showMainMenu()).thenReturn(MenuOption.LIST_NOTES).thenReturn(MenuOption.EXIT);
        controller = spy(controller);
        controller.init();
        verify(controller, times(2)).init();
    }

    @Test
    public void whenInvalidOptionThenShowMainMenu() throws Exception {
        when(view.showMainMenu()).thenReturn(null).thenReturn(MenuOption.EXIT);
        controller = spy(controller);
        controller.init();
        verify(controller, times(2)).init();
    }

}
