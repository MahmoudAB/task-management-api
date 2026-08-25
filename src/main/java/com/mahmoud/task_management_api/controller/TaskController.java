package com.mahmoud.task_management_api.controller;

import com.mahmoud.task_management_api.dto.TaskRequest;
import com.mahmoud.task_management_api.model.Task;
import com.mahmoud.task_management_api.service.TaskManagementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskManagementService taskManagementService;

    public TaskController(TaskManagementService taskManagementService) {
        this.taskManagementService = taskManagementService;
    }

    @PostMapping("/add")
    public Task addTask(@RequestBody TaskRequest taskRequest){
        return this.taskManagementService.createTask(taskRequest);
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable String id){
        return this.taskManagementService.getTaskById(Long.valueOf(id));
    }

    @GetMapping("all")
    public List<Task> getAllTasks(){
        return this.taskManagementService.getAllTasks();
    }




}
