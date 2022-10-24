package com.grubengraeber.todo.service;

import com.grubengraeber.todo.exception.exceptions.NoListFoundException;
import com.grubengraeber.todo.model.TodoList;
import com.grubengraeber.todo.repository.TodoListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoListService {

    private final TodoListRepository todoListRepository;

    public void addList(String name) {
        TodoList todoList = new TodoList();
        todoList.setName(name);
        todoList.setActive(true);
        todoList.setItems(Collections.emptyList());
        todoListRepository.save(todoList);
    }

    public TodoList getById(long id) {
        return todoListRepository.findById(id)
                .orElseThrow(() -> new NoListFoundException(id));
    }

    public void deleteList(long id) {
        try {
            todoListRepository.deleteById(id);
        } catch (RuntimeException runtimeException) {
            throw new NoListFoundException(id);
        }
    }

    public void updateName(long id, String newName) {
        Optional<TodoList> optionalTodoList = todoListRepository.findById(id);
        TodoList todoList = optionalTodoList.orElseThrow(() -> new NoListFoundException(id));
        todoList.setName(newName);
        todoListRepository.save(todoList);
    }

    public void updateActive(long id, boolean active) {
        Optional<TodoList> optionalTodoList = todoListRepository.findById(id);
        TodoList todoList = optionalTodoList.orElseThrow(() -> new NoListFoundException(id));
        todoList.setActive(active);
        todoListRepository.save(todoList);
    }

    public List<TodoList> getAll(boolean active) {
        return todoListRepository.findAllByActive(active);
    }

}
