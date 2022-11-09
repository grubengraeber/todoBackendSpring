package com.grubengraeber.todo.service;

import com.grubengraeber.todo.exception.exceptions.EmptyNameException;
import com.grubengraeber.todo.exception.exceptions.NoListFoundException;
import com.grubengraeber.todo.model.ListItem;
import com.grubengraeber.todo.model.TodoList;
import com.grubengraeber.todo.repository.TodoListRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application-test.yml")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class TodoListServiceTest {
    public static int staticID = 1;

    private final TodoListService todoListService;
    private final TodoListRepository todoListRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TodoListServiceTest(TodoListService todoListService, TodoListRepository todoListRepository,
                               JdbcTemplate jdbcTemplate) {
        this.todoListService = todoListService;
        this.todoListRepository = todoListRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public static Stream<Arguments> getUpdateNameTestStream() {
        return Stream.of(
                Arguments.of("Todo-List 0 new name",
                        "Name of Todo List should be the updated name.", false),
                Arguments.of("Todo-List 0",
                        "Name should be able to be the same name as before.", false),
                Arguments.of("",
                        "If new name is empty, EmptyNameException should be thrown.", true),
                Arguments.of(String.valueOf(1),
                        "Name of Todo List should be able to be a number in String format.", false),
                Arguments.of("&%§$!()\n",
                        "Name of Todo List should be able to have special characters.", false),
                Arguments.of(String.valueOf(true),
                        "Name of Todo List should be able to be a boolean in String format.", false)
        );
    }

    public static Stream<Arguments> getUpdateActiveTestStream() {
        return Stream.of(
                Arguments.of(true, true),
                Arguments.of(true, false),
                Arguments.of(false, true),
                Arguments.of(false, false)
        );
    }

    public static Stream<Arguments> getAddNameTestStream() {
        return Stream.of(
                Arguments.of("Todo-List 0 new name",
                        "Name of Todo List should be the entered name.", false),
                Arguments.of("",
                        "If name is empty, EmptyNameException should be thrown.", true),
                Arguments.of(String.valueOf(1),
                        "Name of Todo List should be able to be a number in String format.", false),
                Arguments.of("&%§$!()\n",
                        "Name of Todo List should be able to have special characters.", false),
                Arguments.of(String.valueOf(true),
                        "Name of Todo List should be able to be a boolean in String format.", false)
        );
    }

    public static Stream<Arguments> getGetByIdTestStream() {
        return Stream.of(
                Arguments.of(-5,
                        "Should throw NoListFoundException with negative id.", true),
                Arguments.of(staticID + 2,
                        "Should be the same TodoList that has been created in the beforeEach section.", false),
                Arguments.of(100,
                        "Should throw NoListFoundException for id too high.", true),
                Arguments.of(0,
                        "Should throw NoListFoundException for 0.", true)
        );
    }

    public static Stream<Arguments> getDeleteListTestStream() {
        return Stream.of(
                Arguments.of(-5,
                        "Should throw NoListFoundException with negative id.", true),
                Arguments.of(staticID + 2,
                        "Should delete the same TodoList that has been created in the beforeEach section, " +
                                "so the whole database should be empty.", false),
                Arguments.of(100,
                        "Should throw NoListFoundException for id too high.", true),
                Arguments.of(0,
                        "Should throw NoListFoundException for 0.", true)
        );
    }

    @BeforeEach
    void setUp() {
        long id = ++staticID;
        jdbcTemplate.execute("insert into TODO_LIST(ID, NAME, ACTIVE) " +
                "values (" + id + ", 'Todo-List 0', true)");
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("DELETE FROM TODO_LIST");
    }

    @ParameterizedTest
    @Order(6)
    @MethodSource("getAddNameTestStream")
    void addList(String name, String message, boolean emptyNameTest) {
        todoListRepository.deleteAll();

        if (!emptyNameTest) {
            todoListService.addList(name);

            TodoList actualTodoList = todoListRepository.findAll().get(0);
            String actualName = actualTodoList.getName();
            assertEquals(name, actualName, message);

            boolean expectedStatus = true;
            boolean actualStatus = actualTodoList.isActive();
            assertEquals(expectedStatus, actualStatus, "Active status should be true after creating new List.");

            new TodoList();
            Long actualId = actualTodoList.getId();
            assertNotNull(actualId, "Id should be initialized after creating Todo list.");

            List<ListItem> actualItemList = actualTodoList.getItems();
            assertNotNull(actualItemList, "Item list should be initialized after creating Todo list.");

            Date actualCreated = actualTodoList.getCreated();
            Date now = Date.from(Instant.now());
            assertTrue(actualCreated.compareTo(now) < 1, "Date of creation shouldn't be after now.");
            return;
        }
        assertThrows(EmptyNameException.class, () -> {
            todoListService.addList(name);
        });
    }

    @ParameterizedTest
    @Order(4)
    @MethodSource("getGetByIdTestStream")
    void getById(long id, String message, boolean exceptionShouldBeThrown) {
        if (!exceptionShouldBeThrown) {
            String expectedName = "Todo-List 0";
            TodoList actualTodoList = todoListService.getById(id);
            String actualName = actualTodoList.getName();

            assertEquals(expectedName, actualName, message);
            return;
        }
        assertThrows(NoListFoundException.class, () -> {
            todoListService.getById(id);
        });
    }

    @ParameterizedTest
    @Order(2)
    @MethodSource("getDeleteListTestStream")
    void deleteList(long id, String message, boolean exceptionShouldBeThrown) {
        if (!exceptionShouldBeThrown) {
            todoListService.deleteList(id);

            assertTrue(todoListRepository.findAll().isEmpty(), message);
            return;
        }
        assertThrows(NoListFoundException.class, () -> {
            todoListService.deleteList(id);
        });
    }

    @ParameterizedTest
    @Order(3)
    @MethodSource("getUpdateNameTestStream")
    void updateName(String newName, String message, boolean emptyNameTest) {
        long id = staticID;

        if (emptyNameTest) {
            assertThrows(EmptyNameException.class, () -> {
                todoListService.updateName(id, newName);
            });
            return;
        }
        todoListService.updateName(id, newName);

        TodoList todoList = todoListRepository.findById(id).orElse(null);
        assertNotNull(todoList, "Todo list should exist.");
        String actual = todoList.getName();
        assertEquals(newName, actual, message);
    }

    @ParameterizedTest
    @Order(1)
    @MethodSource("getUpdateActiveTestStream")
    void updateActive(boolean newActive1, boolean newActive2) {
        long id = staticID;

        todoListService.updateActive(id, newActive1);
        TodoList todoList1 = todoListRepository.findById(id).orElse(null);

        assertNotNull(todoList1, "Todo list should exist.");

        boolean actual1 = todoList1.isActive();
        assertEquals(newActive1, actual1, "Status of Todo List should be the updated active status.");

        todoListService.updateActive(id, newActive2);
        TodoList todoList2 = todoListRepository.findById(id).orElse(null);

        assertNotNull(todoList2, "Todo list should exist.");

        boolean actual2 = todoList2.isActive();
        assertEquals(newActive2, actual2, "Status of Todo List should be the updated active status.");
    }

    @Test
    @Order(5)
    void getAll() {
        long id = ++staticID;
        String name = "Todo-List 1";
        boolean active = false;
        List<ListItem> items = List.of();
        TodoList todoList = new TodoList();
        todoList.setId(id);
        todoList.setName(name);
        todoList.setActive(active);
        todoList.setItems(items);
        todoListRepository.save(todoList);

        List<TodoList> allInactive = todoListService.getAll(false);
        List<TodoList> allActive = todoListService.getAll(true);

        assertTrue(allActive.stream().allMatch(TodoList::isActive), "Should just get all active Lists.");
        assertFalse(allInactive.stream().anyMatch(TodoList::isActive), "Should just get all inactive Lists.");
    }
}