package com.example.generatenumberapp.controller;

import com.example.generatenumberapp.domain.Task;
import com.example.generatenumberapp.exception.TaskNotFoundException;
import com.example.generatenumberapp.service.TaskService;
import com.example.generatenumberapp.exception.PermutationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/v1")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/run")
    public ResponseEntity<List<Task>> getRunningTasks() throws TaskNotFoundException {
        return ResponseEntity.ok(taskService.getAreRunning());
    }

    @GetMapping("/getall")
    public List<Task> getAllTasks() {
        return taskService.getAllTasksFromDb();
    }

    @PostMapping
    public ResponseEntity<Task> addTask(@RequestBody Task task) throws PermutationException {
        return ResponseEntity.ok(taskService.addTask(task));
    }
}
