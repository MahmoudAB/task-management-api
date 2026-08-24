package com.mahmoud.task_management_api.service;

import com.mahmoud.task_management_api.dto.TaskRequest;
import com.mahmoud.task_management_api.model.Task;
import com.mahmoud.task_management_api.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskManagementServiceTest {

    @InjectMocks
    private TaskManagementService taskManagementService;

    @Mock
    private TaskRepository taskRepository;

    private Task testTask;

    private TaskRequest taskRequest;

    @BeforeEach
    public void setup(){
        taskRequest= new TaskRequest("title");
        testTask = new Task(taskRequest.getTitle());
    }

    @Test
    public void saveTaskTest(){
        when(taskRepository.save(any(Task.class))).thenReturn(testTask);
        Task actualTask = taskManagementService.createTask(taskRequest);
        assertEquals(taskRequest.getTitle(), actualTask.getTitle());
    }

    @Test
    public void getTaskById(){
        testTask.setId(1L);
        when(taskRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(testTask));
        Task actualTask = taskManagementService.getTaskById(1L);
        assertEquals(1L, actualTask.getId());
    }

}
