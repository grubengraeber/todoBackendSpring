package com.smatrics.todo.repository;

import com.smatrics.todo.model.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoListRepository extends JpaRepository<TodoList, Long> {

    Optional<TodoList> findById(Long id);
    void deleteById(Long id);
    List<TodoList> findAllByActive(boolean active);
}
