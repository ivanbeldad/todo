package com.rackian.todo;

import com.rackian.todo.view.Menu;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Application application = context.getBean("application", Application.class);
        application.launch(args);
    }

    private Menu menu;

    public Application(Menu menu) {
        this.menu = menu;
    }

    public void launch(String[] args) {
        menu.init();
    }

}
