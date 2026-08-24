package com.mahmoud.task_management_api.controller;

import com.mahmoud.task_management_api.dto.TaskRequest;
import com.mahmoud.task_management_api.model.Task;
import com.mahmoud.task_management_api.service.TaskManagementService;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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

	@Test
	void addTask() throws Exception{
		TaskRequest taskRequest = new TaskRequest("test");

		Task testTask = new Task(taskRequest.getTitle());
		testTask.setId(1L);

		when(taskManagementService.createTask(any(TaskRequest.class))).thenReturn(testTask);

		mockMvc.perform(post("/tasks/add")
				.contentType(String.valueOf(MediaType.APPLICATION_JSON))
				.content(objectMapper.writeValueAsString(taskRequest)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title").value("test"));


	}

}
