package com.example.qademo;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TodoService {
    private final Map<Long, Todo> store = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong(0);

    public List<Todo> findAll() {
        return new ArrayList<>(store.values())
                .stream()
                .sorted(Comparator.comparing(Todo::id))
                .toList();
    }

    public Optional<Todo> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public Todo create(String title) {
        Long id = seq.incrementAndGet();
        Todo t = new Todo(id, title, false);
        store.put(id, t);
        return t;
    }

    public Optional<Todo> update(Long id, String title, Boolean done) {
        Todo current = store.get(id);
        if (current == null) return Optional.empty();
        String newTitle = title != null ? title : current.title();
        boolean newDone = done != null ? done : current.done();
        Todo updated = new Todo(id, newTitle, newDone);
        store.put(id, updated);
        return Optional.of(updated);
    }

    public boolean delete(Long id) {
        return store.remove(id) != null;
    }

    // Seed for demo
    public void seed() {
        if (store.isEmpty()) {
            create("Ler requisitos");
            create("Escrever casos de teste");
            create("Automatizar cenários críticos");
        }
    }
}
