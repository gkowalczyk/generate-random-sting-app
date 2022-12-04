package com.example.generatenumberapp.repository;

import com.example.generatenumberapp.domain.Task;
import com.example.generatenumberapp.domain.TaskStatus;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {


    Optional<Task> findFirstByTaskStatus(TaskStatus taskStatus);

    @Query(" SELECT t FROM Task t WHERE taskStatus = 1")
    Optional<List<Task>> findByStatus();
}
