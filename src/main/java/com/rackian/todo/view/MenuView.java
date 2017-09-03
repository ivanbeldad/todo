package com.rackian.todo.view;

import com.rackian.todo.exception.MenuOptionDoesntExistsException;
import com.rackian.todo.util.MenuError;
import com.rackian.todo.util.MenuInfo;
import com.rackian.todo.util.MenuOption;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuView {

    private PrintStream printStream;
    private Scanner scanner;

    public MenuView(PrintStream printStream, InputStream inputStream) {
        this.printStream = printStream;
        this.scanner = new Scanner(inputStream);
    }

    public MenuOption showMainMenu() throws MenuOptionDoesntExistsException {
        printStream.println(MenuInfo.CHOOSE_OPTION);
        for (MenuOption option : MenuOption.values()) {
            printStream.println(option.toString());
        }
        printStream.println();
        String input = scanner.nextLine();
        printStream.println();
        try {
            return MenuOption.getByNumber(Integer.parseInt(input));
        } catch (MenuOptionDoesntExistsException|NumberFormatException e) {
            printStream.println(MenuError.INVALID_OPTION);
            printStream.println();
            throw e;
        }
    }

    public void show(List<String> items) {
        items.forEach(printStream::println);
        printStream.println();
    }

    public String ask(String question) {
        printStream.println(question);
        String answer = scanner.nextLine();
        printStream.println();
        return answer;
    }

    public List<String> ask(List<String> questions) {
        List<String> answers = new ArrayList<>();
        questions.forEach(question -> {
            printStream.println(question);
            answers.add(scanner.nextLine());
        });
        printStream.println();
        return answers;
    }

    public int askNumber(String question) {
        printStream.println(question);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            printStream.println(MenuError.INVALID_OPTION);
            printStream.println();
            throw e;
        }
    }

    public void showError(String msg) {
        printStream.println(msg);
    }

}
