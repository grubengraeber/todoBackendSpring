package com.grubengraeber.todo.controller;

import com.grubengraeber.todo.repository.TodoListRepository;
import com.grubengraeber.todo.TodoApplication;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(classes = TodoApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RequiredArgsConstructor
class TodoListControllerTest {

    @LocalServerPort
    private int port;

    // TODO Write Tests for TodoListController

    private final TodoListRepository todoListRepository;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
        todoListRepository.deleteAll();
    }

    @Test
    void getAll() {
    }

    @Test
    void add() {
    }

    @Test
    void getList() {
    }

    @Test
    void delete() {
    }

    @Test
    void updateName() {
    }
}