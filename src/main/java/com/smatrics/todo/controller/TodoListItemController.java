package com.smatrics.todo.controller;

import com.smatrics.todo.model.ListItem;
import com.smatrics.todo.model.dto.ActiveUpdate;
import com.smatrics.todo.model.dto.NameUpdate;
import com.smatrics.todo.service.TodoListItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/todoListItems/{listId}")
@RequiredArgsConstructor
public class TodoListItemController {

    private final TodoListItemService todoListItemService;

    @GetMapping("/all/{active}")
    List<ListItem> getAll(@PathVariable("listId") long listId, @PathVariable("active") boolean active) {
        return todoListItemService.getAllByList(listId, active);
    }

    @PostMapping("/add/{name}")
    void add(@PathVariable("listId") long listId, @PathVariable("name") String name) {
            todoListItemService.add(name, listId);
    }

    @GetMapping("/get/{id}")
    ListItem getItem(@PathVariable("listId") long listId, @PathVariable("id") long id) {
        return todoListItemService.getById(id);
    }

    @DeleteMapping("/delete/{id}")
    void delete(@PathVariable("listId") long listId, @PathVariable("id") long id) {
        todoListItemService.delete(id);
    }

    @PatchMapping("/updateName/{id}")
    void updateName(@PathVariable("listId") long listId, @PathVariable("id") long id,
                          @RequestBody NameUpdate nameUpdate) {
        todoListItemService.updateName(id, nameUpdate.getName());
    }

    @PatchMapping("/updateActive/{id}")
    void updateActive(@PathVariable("listId") long listId, @PathVariable("id") long id,
                            @RequestBody ActiveUpdate activeUpdate) {
        todoListItemService.updateActive(id, activeUpdate.isActive());

    }
}
