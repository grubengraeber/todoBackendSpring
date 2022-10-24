package com.grubengraeber.todo.service;

import com.grubengraeber.todo.exception.exceptions.NoListFoundException;
import com.grubengraeber.todo.exception.exceptions.NoListItemFoundException;
import com.grubengraeber.todo.model.ListItem;
import com.grubengraeber.todo.model.TodoList;
import com.grubengraeber.todo.repository.TodoListItemRepository;
import com.grubengraeber.todo.repository.TodoListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoListItemService {

    private final TodoListItemRepository todoListItemRepository;
    private final TodoListRepository todoListRepository;

    public void add(String name, long todoListId) {
        TodoList todoList = todoListRepository.findById(todoListId)
                .orElseThrow(() -> new NoListFoundException(todoListId));
        ListItem listItem = new ListItem();
        listItem.setTodoList(todoList);
        listItem.setName(name);
        listItem.setActive(true);
        todoListItemRepository.save(listItem);
    }

    public void delete(long id) {
        try {
            todoListItemRepository.deleteById(id);
        } catch (RuntimeException runtimeException) {
            throw new NoListItemFoundException(id);
        }
    }

    public ListItem getById(long id) {
        return todoListItemRepository.findById(id).orElseThrow(()-> new NoListItemFoundException(id));
    }

    public void updateName(long id, String newName) {
        Optional<ListItem> optionalListItem = todoListItemRepository.findById(id);
        ListItem listItem = optionalListItem.orElseThrow(() -> new NoListItemFoundException(id));
        listItem.setName(newName);
        todoListItemRepository.save(listItem);
    }

    public void updateActive(long id, boolean active) {
        Optional<ListItem> optionalListItem = todoListItemRepository.findById(id);
        ListItem listItem = optionalListItem.orElseThrow(() -> new NoListItemFoundException(id));
        listItem.setActive(active);
        todoListItemRepository.save(listItem);
    }

    public List<ListItem> getAllByList(long listId, boolean active) {
        return todoListItemRepository.findAllByTodoListIdAndActive(listId, active);
    }

}
