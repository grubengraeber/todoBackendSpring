package com.grubengraeber.todo.repository;

import com.grubengraeber.todo.model.ListItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoListItemRepository extends JpaRepository<ListItem, Long> {
    List<ListItem> findAllByTodoListIdAndActive(Long id, boolean active);
}
