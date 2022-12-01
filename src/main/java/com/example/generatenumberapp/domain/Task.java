package com.example.generatenumberapp.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long min;
    private long max;
    private String charStrings;
    private long amount;
    private String saveFileAs;
    private TaskStatus taskStatus;

    public Task(long min, long max, String charStrings, long amount) {
        this.min = min;
        this.max = max;
        this.charStrings = charStrings;
        this.amount = amount;
    }
}
