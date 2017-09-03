package com.rackian.todo;

import com.rackian.todo.controller.MenuController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {

    private ApplicationContext context;

    public Application(ApplicationContext context) {
        this.context = context;
    }

    public void launch() {
        MenuController menuController = context.getBean("menuController", MenuController.class);
        menuController.init();
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        new Application(context).launch();
    }

}
