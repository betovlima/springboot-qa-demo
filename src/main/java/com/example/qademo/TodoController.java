package com.example.qademo;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
        this.service.seed();
    }

    @GetMapping
    public List<Todo> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getById(
            @Parameter(description = "ID do Todo", required = true)
            @PathVariable(name = "id") Long id
    ) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Todo> create(@Valid @RequestBody TodoCreateRequest req) {
        Todo created = service.create(req.title());
        return ResponseEntity.created(URI.create("/api/todos/" + created.id()))
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> update(@Parameter(description = "ID para salvar", required = true)
                                           @PathVariable(name = "id") Long id, @Valid @RequestBody TodoUpdateRequest req) {
        return service.update(id, req.title(), req.done())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete( @Parameter(description = "ID do Todo", required = true)
                            @PathVariable(name = "id") Long id) {
        if (!service.delete(id)) {
            throw new ResourceNotFoundException("Todo %d não encontrado".formatted(id));
        }
    }
}
