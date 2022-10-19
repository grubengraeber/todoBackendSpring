package com.smatrics.todo.controller;

import com.smatrics.todo.TodoApplication;
import com.smatrics.todo.repository.TodoListRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

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
        todoListRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
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