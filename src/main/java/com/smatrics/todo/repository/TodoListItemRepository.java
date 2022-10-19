package com.smatrics.todo.repository;

import com.smatrics.todo.model.ListItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoListItemRepository extends JpaRepository<ListItem, Long> {
    List<ListItem> findAllByTodoListIdAndActive(Long id, boolean active);
}
