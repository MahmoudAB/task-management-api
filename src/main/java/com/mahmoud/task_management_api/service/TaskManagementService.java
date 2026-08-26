package com.mahmoud.task_management_api.service;

import com.mahmoud.task_management_api.dto.TaskRequest;
import com.mahmoud.task_management_api.exception.TaskNotFoundException;
import com.mahmoud.task_management_api.model.Task;
import com.mahmoud.task_management_api.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskManagementService {

    private final TaskRepository taskRepository;

    public TaskManagementService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(TaskRequest taskRequest){
        return taskRepository.save(new Task(taskRequest.title()));
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }

    public List<Task> getAllTasks() {
        return this.taskRepository.findAll();
    }

    public Task updateTask(Long id, TaskRequest taskRequest) {
        Task task = getTaskById(id);
        task.setTitle(taskRequest.title());
        return taskRepository.save(task);
    }
}
