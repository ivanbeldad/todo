package com.rackian.todo;

import com.rackian.todo.view.Menu;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class ApplicationSpec {

    @Test
    public void whenLaunchThenInitMenu() throws Exception {
        Menu menu = mock(Menu.class);
        doNothing().when(menu).init();
        Application application = new Application(menu);
        application.launch(new String[]{});
        verify(menu, times(1)).init();
    }

}
