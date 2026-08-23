package com.mahmoud.task_management_api.service;

import com.mahmoud.task_management_api.dto.TaskRequest;
import com.mahmoud.task_management_api.model.Task;
import com.mahmoud.task_management_api.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskManagementService {

    private final TaskRepository taskRepository;

    public TaskManagementService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(TaskRequest taskRequest){
        return taskRepository.save(new Task(taskRequest.getTitle()));
    }
}
