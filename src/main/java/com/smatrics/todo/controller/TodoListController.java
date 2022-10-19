package com.smatrics.todo.controller;

import com.smatrics.todo.model.TodoList;
import com.smatrics.todo.model.dto.ActiveUpdate;
import com.smatrics.todo.model.dto.NameUpdate;
import com.smatrics.todo.service.TodoListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/todoLists")
@RequiredArgsConstructor
public class TodoListController {

    private final TodoListService todoListService;

    @GetMapping("/all/{active}")
    List<TodoList> getAll(@PathVariable("active") boolean active) {
        return todoListService.getAll(active);
    }

    @PostMapping("/add/{name}")
    void add(@PathVariable("name") String name) {
        todoListService.addList(name);
    }

    @GetMapping("/get/{id}")
    TodoList getList(@PathVariable("id") long id) {
        return todoListService.getById(id);
    }

    @DeleteMapping("/delete/{id}")
    void delete(@PathVariable("id") long id) {
        todoListService.deleteList(id);
    }

    @PatchMapping("/updateName/{id}")
    void updateName(@PathVariable("id") long id, @RequestBody NameUpdate nameUpdate) {
        todoListService.updateName(id, nameUpdate.getName());
    }

    @PatchMapping("/updateActive/{id}")
    void updateActive(@PathVariable("id") long id, @RequestBody ActiveUpdate activeUpdate) {
        todoListService.updateActive(id, activeUpdate.isActive());
    }

}
