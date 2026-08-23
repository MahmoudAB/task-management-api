package com.mahmoud.task_management_api.service;

import com.mahmoud.task_management_api.dto.TaskRequest;
import com.mahmoud.task_management_api.model.Task;
import com.mahmoud.task_management_api.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskManagementServiceTest {

    @InjectMocks
    private TaskManagementService taskManagementService;

    @Mock
    private TaskRepository taskRepository;

    @Test
    public void saveTaskTest(){
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setTitle("title");

        Task savedTask = new Task(taskRequest.getTitle());

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        Task actualTask = taskManagementService.createTask(taskRequest);
        assertEquals(taskRequest.getTitle(), actualTask.getTitle());
    }

}
