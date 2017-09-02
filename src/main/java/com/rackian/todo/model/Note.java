package com.rackian.todo.model;

import java.util.Objects;

public class Note {

    private String title;
    private String content;

    public Note() {
        title = "";
        content = "";
    }

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(title, note.title) &&
                Objects.equals(content, note.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, content);
    }

    @Override
    public String toString() {
        return  "Title: " + title + "\n" +
                "Content: " + content;
    }

}
