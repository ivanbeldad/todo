package com.rackian.todo.repository;

import com.rackian.todo.exception.NoteAlreadyExistsException;
import com.rackian.todo.exception.NoteDoesntExistsException;
import com.rackian.todo.model.Note;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MysqlNoteRepository implements NoteRepository {

    @Value("${javax.persistence.jdbc.url}")
    private String url;
    @Value("${javax.persistence.jdbc.user}")
    private String user;
    @Value("${javax.persistence.jdbc.password}")
    private String password;
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;

    public MysqlNoteRepository() {
    }

    @PostConstruct
    private void init() {
        Map<String, String> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.url", url);
        properties.put("javax.persistence.jdbc.user", user);
        properties.put("javax.persistence.jdbc.password", password);
        entityManagerFactory = Persistence
                .createEntityManagerFactory("todo", properties);
    }

    @Override
    public Note save(Note note) throws NoteAlreadyExistsException {
        entityManager = entityManagerFactory.createEntityManager();
        entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(note);
        entityTransaction.commit();
        entityManager.close();
        return note;
    }

    @Override
    public boolean exists(Note note) {
        // TODO
        return false;
    }

    @Override
    public Iterable<Note> findAll() {
        entityManager = entityManagerFactory.createEntityManager();
        entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        List notes = entityManager
                .createQuery("SELECT note FROM Note note")
                .getResultList();
        entityTransaction.commit();
        entityManager.close();
        return notes;
    }

    @Override
    public long count() {
        // TODO
        return 0;
    }

    @Override
    public void delete(Note note) throws NoteDoesntExistsException {
        // TODO
        entityManager = entityManagerFactory.createEntityManager();
        entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.remove(note);
        entityTransaction.commit();
        entityManager.close();
    }

    @Override
    public void deleteAll() {
        // TODO
    }

}
