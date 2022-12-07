package com.grubengraeber.todo.controller;

import com.grubengraeber.todo.repository.TodoListRepository;
import com.grubengraeber.todo.TodoApplication;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static java.nio.file.Paths.get;

@SpringBootTest(classes = TodoApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RequiredArgsConstructor
class TodoListControllerTest {

    @LocalServerPort
    private int port;

    MockMvc mockMvc;

    // TODO Write Tests for TodoListController
    private final TodoListRepository todoListRepository;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    void tearDown() {
        todoListRepository.deleteAll();
    }

    @SneakyThrows
    @Test
    void getAll() {
        MvcResult mvcResult = this.mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/all")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andReturn();
        Assertions.assertEquals("OK", mvcResult.getResponse().getContentAsString());
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