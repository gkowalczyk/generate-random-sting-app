package com.example.generatenumberapp.facade;

import com.example.generatenumberapp.domain.Task;
import com.example.generatenumberapp.domain.TaskStatus;
import com.example.generatenumberapp.exception.FileWriterException;
import com.example.generatenumberapp.repository.TaskRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
}