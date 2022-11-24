package com.example.generatenumberapp.facade;

import com.example.generatenumberapp.domain.Task;
import com.example.generatenumberapp.repository.TaskRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
class StringGeneratorFacadeTest {

    @InjectMocks
    StringGeneratorFacade stringGeneratorFacade = new StringGeneratorFacade(Mockito.mock(TaskRepository.class));

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

    @Test
    void generateStringsList() {

        //Given
        Task task = new Task(1, 3, "abc", 3);
        //When
        List<String> stringList = stringGeneratorFacade.generateStringsList(task);
        String strings = stringList.get(0);

        //Then
        Assertions.assertThat(stringList).hasSize(3)
                .allMatch(string -> string.matches("[abc]*"))
                .allMatch(string -> string.length() >= 1 && string.length() <= 3);
    }
}