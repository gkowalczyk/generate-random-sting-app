package com.example.generatenumberapp.domain;

import lombok.Data;

@Data
public class TaskDto {
    private long min;
    private long max;
    private String charStrings;
    private long amount;
}
