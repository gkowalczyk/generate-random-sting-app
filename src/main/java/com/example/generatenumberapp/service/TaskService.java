package com.example.generatenumberapp.service;

import com.example.generatenumberapp.domain.Task;
import com.example.generatenumberapp.domain.TaskStatus;
import com.example.generatenumberapp.exception.FileWriterException;
import com.example.generatenumberapp.exception.TaskNotFoundException;
import com.example.generatenumberapp.exception.PermutationException;
import com.example.generatenumberapp.facade.CalculateCombinations;
import com.example.generatenumberapp.facade.StringGeneratorFacade;
import com.example.generatenumberapp.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service

public class TaskService {

    private final TaskRepository taskRepository;
    private final StringGeneratorFacade stringGeneratorFacade;

    @Autowired
    public TaskService(TaskRepository taskRepository, StringGeneratorFacade stringGeneratorFacade) {
        this.taskRepository = taskRepository;
        this.stringGeneratorFacade = stringGeneratorFacade;
    }

    public List<Task> getAllTasksFromDb() {
        Iterable<Task> taskIterable = taskRepository.findAll();
        List<Task> taskList = new ArrayList<>();
        taskIterable.forEach(taskList::add);
        return taskList;
    }

    public void addTask(Task task) throws PermutationException, InterruptedException, FileWriterException {

        if (CalculateCombinations.howManyCombinations(task.getCharStrings().length()) < task.getAmount()) {
            throw new PermutationException("GlobalHttpErrorHandler");
        }
        task.setTaskStatus(TaskStatus.STARTING);
        taskRepository.saveAndFlush(task);
    }


    public List<Task> getAreRunning() throws TaskNotFoundException {
        return taskRepository.findByStatus().orElseThrow(TaskNotFoundException::new);
    }
}






