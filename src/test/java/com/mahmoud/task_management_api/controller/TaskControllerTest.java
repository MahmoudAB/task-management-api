package com.mahmoud.task_management_api.controller;

import com.mahmoud.task_management_api.dto.TaskRequest;
import com.mahmoud.task_management_api.model.Task;
import com.mahmoud.task_management_api.service.TaskManagementService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private TaskManagementService taskManagementService;

	@Autowired
	private ObjectMapper objectMapper;

	private static TaskRequest taskRequest;

	private static Task testTask;

	@BeforeAll
	public static void setup(){
		taskRequest = new TaskRequest("test");
		testTask = new Task(taskRequest.title());
		testTask.setId(1L);

	}
	@Test
	public void addTask() throws Exception {

		when(taskManagementService.createTask(any(TaskRequest.class))).thenReturn(testTask);

		mockMvc.perform(post("/tasks/add")
				.contentType(String.valueOf(MediaType.APPLICATION_JSON))
				.content(objectMapper.writeValueAsString(taskRequest)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title").value("test"));


	}

	@Test
	public void findById() throws Exception {
		when(taskManagementService.getTaskById(1L)).thenReturn(testTask);

		mockMvc.perform(get("/tasks/1")
						.content(objectMapper.writeValueAsString(taskRequest)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title").value("test"));
	}

}
