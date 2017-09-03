package com.rackian.todo.command;

public interface Commands<K> {
    void execute(K key);
}
