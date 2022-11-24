package com.example.generatenumberapp.controller;

import com.example.generatenumberapp.domain.Task;
import com.example.generatenumberapp.service.TaskService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    private static int testCounter = 0;

    @BeforeAll
    public static void beforeAllTests() {
        System.out.println("This is the beginning of tests.");
    }

    @AfterAll
    public static void afterAllTests() {
        System.out.println("All tests are finished.");
    }

    @BeforeEach
    public void beforeEveryTest() {
        testCounter++;
        System.out.println("Preparing to execute test #" + testCounter);
    }

    @InjectMocks
    private TaskController taskController;

    @Mock
    private TaskService taskService;

    @Test
    void getAllTasks() {
        //Given
        List<Task> taskList = List.of(
                new Task(1, 3, "abc", 3),
                new Task(1, 3, "abc", 4),
                new Task(1, 3, "abc", 5));
        //When
        when(taskService.getAllTasksFromDb()).thenReturn(taskList);
        //Then
        assertEquals(3, taskController.getAllTasks().size());
        assertTrue(taskController.getAllTasks().get(0).getAmount() == 3);
    }
}