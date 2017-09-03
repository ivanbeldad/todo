package com.rackian.todo;

import com.rackian.todo.controller.MenuController;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import static org.mockito.Mockito.*;

public class ApplicationSpec {

    private ApplicationContext context;
    private MenuController controller;

    @Before
    public void setUp() throws Exception {
        context = mock(ApplicationContext.class);
        controller = mock(MenuController.class);
        when(context.getBean("menuController", MenuController.class)).thenReturn(controller);
    }

    @Test
    public void whenLaunchThenContextCreateController() throws Exception {
        new Application(context).launch();
        verify(context, times(1)).getBean("menuController", MenuController.class);
    }

    @Test
    public void whenLaunchThenControllerInitIsCalled() throws Exception {
        new Application(context).launch();
        verify(controller, times(1)).init();
    }

}
