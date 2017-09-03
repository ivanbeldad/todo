package com.rackian.todo.command;

import com.rackian.todo.view.MenuView;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class ExitCommandSpec {

    @Test(expected = RuntimeException.class)
    public void whenExitThenExecuteIt() {
        new ExitCommand(mock(MenuView.class)).execute();
    }

}
