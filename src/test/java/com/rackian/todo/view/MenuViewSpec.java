package com.rackian.todo.view;

import com.rackian.todo.exception.MenuOptionDoesntExistsException;
import com.rackian.todo.model.Note;
import com.rackian.todo.util.MenuError;
import com.rackian.todo.util.MenuInfo;
import com.rackian.todo.util.MenuOption;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class MenuViewSpec {

    private MenuView menuView;

    private PrintStream printStream;
    private ByteArrayOutputStream outputStreamBytes;

    private InputStream inputStream;
    private Collection<Note> notes;
    private List<String> inputs;

    @Before
    public void setUp() throws Exception {
        inputs = new ArrayList<>();

        Note note1 = new Note("My title", "My content");
        Note note2 = new Note("My title 2", "My content 2");
        Note note3 = new Note("My title 3", "My content 3");
        notes = Arrays.asList(note1, note2, note3);

        outputStreamBytes = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStreamBytes);
        initStream();
    }

    private void addOption(MenuOption option) {
        inputs.add(Integer.toString(option.getNumber()));
        initStream();
    }

    private void addInput(String input) {
        inputs.add(input);
        initStream();
    }

    private void initStream() {
        String result = inputs.stream().reduce("", (s1, s2) -> s1 + '\n' + s2);
        result = result.replaceFirst("\n", "");
        inputStream = new ByteArrayInputStream(result.getBytes());
        menuView = new MenuView(printStream, inputStream);
    }

    @Test
    public void whenShowOptionsThenListNotesOptionIsShowed() throws Exception {
        addInput("1");
        menuView.showMainMenu();
        String showed = outputStreamBytes.toString();
        assertThat(showed, containsString(MenuOption.valueOf("LIST_NOTES").toString()));
    }

    @Test
    public void whenShowOptionsThenCreateNotesOptionIsShowed() throws Exception {
        addInput("1");
        menuView.showMainMenu();
        String showed = outputStreamBytes.toString();
        assertThat(showed, containsString(MenuOption.valueOf("CREATE_NOTE").toString()));
    }

    @Test
    public void whenShowOptionsThenDeleteNotesOptionIsShowed() throws Exception {
        addInput("1");
        menuView.showMainMenu();
        String showed = outputStreamBytes.toString();
        assertThat(showed, containsString(MenuOption.valueOf("DELETE_NOTE").toString()));
    }

    @Test
    public void whenShowOptionsThenInputIsReceived() throws Exception {
        addOption(MenuOption.CREATE_NOTE);
        MenuOption option = menuView.showMainMenu();
        assertThat(option, is(MenuOption.CREATE_NOTE));
    }

    @Test
    public void whenAnotherOptionThenInputIsReceived() throws Exception {
        addOption(MenuOption.DELETE_NOTE);
        MenuOption option = menuView.showMainMenu();
        assertThat(option, is(MenuOption.DELETE_NOTE));
    }

    @Test(expected = MenuOptionDoesntExistsException.class)
    public void whenInvalidNumberThenShowErrorMessage() throws Exception {
        inputStream = new ByteArrayInputStream("34".getBytes());
        menuView = new MenuView(printStream, inputStream);
        menuView.showMainMenu();
        assertThat(outputStreamBytes.toString(), containsString(MenuError.INVALID_OPTION.toString()));
    }

    @Test(expected = NumberFormatException.class)
    public void whenNotNumberThenShowErrorMesasage() throws Exception {
        inputStream = new ByteArrayInputStream("Invalid option".getBytes());
        menuView = new MenuView(printStream, inputStream);
        menuView.showMainMenu();
        assertThat(outputStreamBytes.toString(), containsString(MenuError.INVALID_OPTION.toString()));
    }

    @Test
    public void whenExecuteListNotesThenListsAreShowed() throws Exception {
        addOption(MenuOption.LIST_NOTES);

        List<String> strings = notes.stream().map(Note::toString).collect(Collectors.toList());
        menuView.show(strings);

        assertThat(outputStreamBytes.toString(), containsString(notes.toArray()[0].toString()));
        assertThat(outputStreamBytes.toString(), containsString(notes.toArray()[1].toString()));
        assertThat(outputStreamBytes.toString(), containsString(notes.toArray()[2].toString()));
    }

    @Test
    public void whenAskThenShowWaitForAnswer() throws Exception {
        addOption(MenuOption.CREATE_NOTE);

        menuView.ask(MenuInfo.valueOf("INSERT_TITLE").toString());
        assertThat(outputStreamBytes.toString(), containsString(MenuInfo.valueOf("INSERT_TITLE").toString()));
    }

    @Test
    public void whenAskMultipleThenShowWaitForAnswer() throws Exception {
        addOption(MenuOption.CREATE_NOTE);
        addInput("");
        addInput("");

        List<String> questions = Arrays.asList(
                MenuInfo.valueOf("INSERT_TITLE").toString(),
                MenuInfo.valueOf("INSERT_CONTENT").toString());
        menuView.ask(questions);
        assertThat(outputStreamBytes.toString(), containsString(questions.get(0)));
        assertThat(outputStreamBytes.toString(), containsString(questions.get(1)));
    }

    @Test
    public void whenAskNumberThenShowQuestion() throws Exception {
        addInput("12");
        String question = "Any question";
        menuView.askNumber(question);
        assertThat(outputStreamBytes.toString(), containsString(question));
    }

    @Test
    public void whenAskNumberThenReceiveNumber() throws Exception {
        int number = 24;
        addInput(Integer.toString(number));
        assertThat(menuView.askNumber(""), is(number));
    }

    @Test(expected = NumberFormatException.class)
    public void whenAskNumberAndInvalidThenThrowException() throws Exception {
        addInput("invalid");
        menuView.askNumber("");
    }

    @Test
    public void whenAskNumberAndInvalidShowError() throws Exception {
        addInput("invalid");
        try {
            menuView.askNumber("");
        } catch (NumberFormatException e) {
        }
        assertThat(outputStreamBytes.toString(), containsString(MenuError.INVALID_OPTION.toString()));
    }

    @Test
    public void whenShowErrorThenItIsShowed() throws Exception {
        String errorMsg = "My error";
        menuView.showError(errorMsg);
        assertThat(outputStreamBytes.toString(), containsString(errorMsg));
    }
}
