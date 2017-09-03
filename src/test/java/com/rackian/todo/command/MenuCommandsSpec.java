package com.rackian.todo.command;

import com.rackian.todo.service.NoteService;
import com.rackian.todo.util.MenuOption;
import com.rackian.todo.view.MenuView;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

public class MenuCommandsSpec {

    private NoteService service;
    private MenuView view;
    private Map<MenuOption, Command> map;
    private MenuCommands commands;

    @Before
    public void setUp() throws Exception {
        map = spy(HashMap.class);
        service = mock(NoteService.class);
        view = mock(MenuView.class);
        commands = new MenuCommands(map, service, view);
    }

    @Test
    public void whenExecuteThenRedirectExecute() throws Exception {
        Command command = mock(Command.class);
        when(map.get(MenuOption.LIST_NOTES)).thenReturn(command);
        commands.execute(MenuOption.LIST_NOTES);
        verify(map.get(MenuOption.LIST_NOTES), times(1)).execute();
    }

}
