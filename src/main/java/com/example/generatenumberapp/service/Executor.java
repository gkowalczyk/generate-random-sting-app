package com.example.generatenumberapp.service;

import com.example.generatenumberapp.domain.Task;
import com.example.generatenumberapp.domain.TaskStatus;
import com.example.generatenumberapp.exception.FileWriterException;
import com.example.generatenumberapp.facade.StringGeneratorFacade;
import com.example.generatenumberapp.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class Executor {

    private final TaskRepository taskRepository;
    private final StringGeneratorFacade stringGeneratorFacade;

    public Executor(TaskRepository taskRepository, StringGeneratorFacade stringGeneratorFacade) throws FileWriterException {
        this.taskRepository = taskRepository;
        this.stringGeneratorFacade = stringGeneratorFacade;
    }

    @Scheduled(fixedRate = 10000)
    @Async("asyncExecutor")
    public void executor() throws FileWriterException{


        Task optionalTask = taskRepository.findFirstByTaskStatus(TaskStatus.STARTING).orElse(null);
        if (Objects.nonNull(optionalTask)) {
            log.info("Processing..........................");
            log.info("Current Thread= " + Thread.currentThread().getName());
            optionalTask.setTaskStatus(TaskStatus.INPROGRESS);
            taskRepository.saveAndFlush(optionalTask);
            List<String> randomWords = stringGeneratorFacade.generateStringsList(optionalTask);
            log.info("Saving file start..............");
            stringGeneratorFacade.saveToFile(randomWords);
            optionalTask.setSaveFileAs(stringGeneratorFacade.returnMaxLongIdFromDataBase() + ".txt");
            log.info("Saving file end..............");
            optionalTask.setTaskStatus(TaskStatus.FINISHED);
            taskRepository.saveAndFlush(optionalTask);
        }
    }
}