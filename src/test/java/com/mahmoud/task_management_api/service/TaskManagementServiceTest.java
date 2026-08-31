package com.mahmoud.task_management_api.service;

import com.mahmoud.task_management_api.dto.TaskRequest;
import com.mahmoud.task_management_api.exception.TaskNotFoundException;
import com.mahmoud.task_management_api.entity.Task;
import com.mahmoud.task_management_api.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

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
        testTask = new Task(taskRequest.title());
    }

    @Test
    public void saveTaskTest(){
        when(taskRepository.save(any(Task.class))).thenReturn(testTask);
        Task actualTask = taskManagementService.createTask(taskRequest);
        assertEquals(taskRequest.title(), actualTask.getTitle());
    }

    @Test
    public void getTaskById(){
        testTask.setId(1L);
        when(taskRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(testTask));
        Task actualTask = taskManagementService.getTaskById(1L);
        assertEquals(1L, actualTask.getId());
    }

    @Test
    public void getTaskByIdNotFoundTest(){
        Long id = 1L;
        when(taskRepository.findById(any(Long.class))).thenThrow(new TaskNotFoundException(id));
        assertThrows(TaskNotFoundException.class, () -> taskManagementService.getTaskById(id));
    }

    @Test
    public void findAllTasksTest(){
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("task 1"));
        tasks.add(new Task("task 2"));
        tasks.add(new Task("task 3"));
        when(taskRepository.findAll()).thenReturn(tasks);
        List<Task> expectedTasks = taskManagementService.getAllTasks();
        assertEquals(3, expectedTasks.size());
        assertEquals("task 1", expectedTasks.getFirst().getTitle());
    }

    @Test
    public void updateTaskTest(){
        Long id = 1L;
        TaskRequest updateRequest = new TaskRequest("updated title");
        testTask.setId(id);
        when(taskRepository.findById(id)).thenReturn(Optional.of(testTask));
        when(taskRepository.save(testTask)).thenReturn(testTask);

        Task updatedTask = taskManagementService.updateTask(id, updateRequest);

        assertEquals(id, updatedTask.getId());
        assertEquals("updated title", updatedTask.getTitle());
        verify(taskRepository).save(testTask);
    }

    @Test
    public void updateTaskNotFoundTest(){
        Long id = 1L;
        TaskRequest updateRequest = new TaskRequest("updated title");
        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class,
                () -> taskManagementService.updateTask(id, updateRequest));
    }

    @Test
    public void toggleTaskCompletedTest(){
        Long id = 1L;
        testTask.setId(id);
        when(taskRepository.findById(id)).thenReturn(Optional.of(testTask));
        when(taskRepository.save(testTask)).thenReturn(testTask);

        Task completedTask = taskManagementService.toggleTaskCompleted(id);

        assertEquals(true, completedTask.isCompleted());
        verify(taskRepository).save(testTask);

        Task incompleteTask = taskManagementService.toggleTaskCompleted(id);

        assertEquals(false, incompleteTask.isCompleted());
        verify(taskRepository, org.mockito.Mockito.times(2)).save(testTask);
    }

    @Test
    public void toggleTaskCompletedNotFoundTest(){
        Long id = 1L;
        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class,
                () -> taskManagementService.toggleTaskCompleted(id));
    }

}
