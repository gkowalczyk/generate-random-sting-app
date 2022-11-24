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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@AllArgsConstructor
@Data
@Component
public class StringGeneratorFacade {

    private final TaskRepository taskRepository;
    private final Random random = new Random();

    @Async("asyncExecutor")
    @Scheduled(fixedRate = 10000)
        public void finalGenerateStringsListTasksWithAsync() throws FileWriterException {
        Task optionalTask = taskRepository.findFirstByTaskStatus(TaskStatus.WAITING).orElse(null);
        if (optionalTask != null) {
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
                    fileOutputStream.write(n.getBytes(StandardCharsets.UTF_8));
                    fileOutputStream.write("\n".getBytes(StandardCharsets.UTF_8));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
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
}