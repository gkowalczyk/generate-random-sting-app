package com.example.generatenumberapp.facade;

import com.example.generatenumberapp.domain.Task;
import com.example.generatenumberapp.domain.TaskStatus;
import com.example.generatenumberapp.exception.FileWriterException;
import com.example.generatenumberapp.repository.TaskRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
@AllArgsConstructor
@Data
@Component
public class StringGeneratorFacade {

    private final TaskRepository taskRepository;
    private final Random random = new Random();


    @Scheduled(fixedRate = 10000)
    public void execute() throws ExecutionException, InterruptedException {
        ExecutorService threadpool = Executors.newFixedThreadPool(5);
        Future<Long> futureTask;
        futureTask = (Future<Long>) threadpool.submit(() -> {
            try {
                finalGenerateStringsListTasksWithAsync();

            } catch (FileWriterException e) {
                e.printStackTrace();
            }
        });
        while (!futureTask.isDone()) {
            log.info("FutureTask is not finished yet...");
           log.info("Numbers active tasks=" + futureTask.get());
        }
        threadpool.shutdown();
    }

    public long returnMaxLongIdFromDataBase() {
        Iterable<Task> taskIterable = taskRepository.findAll();
        List<Task> taskList = new ArrayList<>();
        taskIterable.forEach(taskList::add);
        Long max = taskList.stream()
                .mapToLong(o -> o.getId())
                .max()
                .orElse(0);
        return max;
    }

    public File saveToFile(List<String> listOfStrings) throws FileWriterException {

        File file = new File(returnMaxLongIdFromDataBase() + ".txt");
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            listOfStrings.forEach(n -> {
                try {
                    fileOutputStream.write(n.getBytes());
                    fileOutputStream.write("\n".getBytes());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            fileOutputStream.flush();
        } catch (IOException e) {
            throw new FileWriterException();
        }
        return file;
    }

    public List<String> generateStringsList(Task task) {
        List<String> list = new ArrayList<>();
        int range = (int) ((task.getMax() - task.getMin()) + task.getMin());
        char[] possibleChars = task.getCharStrings().toCharArray();
        for (int i = 0; i < task.getAmount(); i++) {
            StringBuffer string = new StringBuffer();
            int length = random.nextInt(range);
            for (int j = 0; j <= length; j++) {
                char randomChar = possibleChars[random.nextInt(possibleChars.length)];
                string = string.append(randomChar);
            }
            list.add(string.toString());
        }
        return list;
    }
   // @Scheduled(fixedRate = 10000)
    // @Async
    public void finalGenerateStringsListTasksWithAsync() throws FileWriterException {

        Task optionalTask = taskRepository.findFirstByTaskStatus(TaskStatus.STARTING).orElse(null);
        if (Objects.nonNull(optionalTask)) {
            log.info("Processing..........................");
            optionalTask.setTaskStatus(TaskStatus.INPROGRESS);
            taskRepository.saveAndFlush(optionalTask);
            List<String> randomWords = generateStringsList(optionalTask);
            log.info("Saving file start..............");
            saveToFile(randomWords);
            optionalTask.setSaveFileAs(returnMaxLongIdFromDataBase() + ".txt");
            log.info("Saving file end..............");
            optionalTask.setTaskStatus(TaskStatus.FINISHED);
            taskRepository.saveAndFlush(optionalTask);
        }
        log.info("Async task schedule end");
    }
}