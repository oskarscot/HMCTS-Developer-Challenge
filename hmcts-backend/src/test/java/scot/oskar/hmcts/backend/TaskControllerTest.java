package scot.oskar.hmcts.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import scot.oskar.hmcts.backend.data.dto.TaskCreateDTO;
import scot.oskar.hmcts.backend.data.dto.TaskDTO;
import scot.oskar.hmcts.backend.data.dto.TaskUpdateDTO;
import scot.oskar.hmcts.backend.data.model.Task;
import scot.oskar.hmcts.backend.exception.ResourceNotFoundException;
import scot.oskar.hmcts.backend.service.TaskService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TaskControllerTest extends AbstractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TaskService taskService;

    private TaskDTO taskDTO;
    private TaskCreateDTO taskCreateDTO;
    private TaskUpdateDTO taskUpdateDTO;
    private LocalDateTime dueDate;
    private String dueDateStr;

    @BeforeEach
    void setUp() {
        dueDate = LocalDateTime.now().plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        dueDateStr = dueDate.format(formatter);

        taskDTO = TaskDTO.builder()
                .id(1L)
                .title("Test Task")
                .description("Test Description")
                .status(Task.TaskStatus.PENDING)
                .dueDate(dueDate)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        taskCreateDTO = TaskCreateDTO.builder()
                .title("New Task")
                .description("New Description")
                .status(Task.TaskStatus.PENDING)
                .dueDate(dueDate)
                .build();

        taskUpdateDTO = TaskUpdateDTO.builder()
                .title("Updated Task")
                .description("Updated Description")
                .status(Task.TaskStatus.IN_PROGRESS)
                .dueDate(dueDate)
                .build();
    }

    @Test
    void createTask_ShouldReturn201_WhenValidInput() throws Exception {
        when(taskService.createTask(any(TaskCreateDTO.class))).thenReturn(taskDTO);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskCreateDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test Task")));

        verify(taskService, times(1)).createTask(any(TaskCreateDTO.class));
    }

    @Test
    void getTaskById_ShouldReturn200_WhenTaskExists() throws Exception {
        when(taskService.getTaskById(1L)).thenReturn(taskDTO);

        mockMvc.perform(get("/api/tasks/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test Task")));

        verify(taskService, times(1)).getTaskById(1L);
    }

    @Test
    void getTaskById_ShouldReturn404_WhenTaskDoesNotExist() throws Exception {
        when(taskService.getTaskById(1L)).thenThrow(new ResourceNotFoundException("Task not found with id: 1"));

        mockMvc.perform(get("/api/tasks/{id}", 1L))
                .andExpect(status().isNotFound());

        verify(taskService, times(1)).getTaskById(1L);
    }

    @Test
    void getAllTasks_ShouldReturn200_WithListOfTasks() throws Exception {
        List<TaskDTO> tasks = Collections.singletonList(taskDTO);
        when(taskService.getAllTasks()).thenReturn(tasks);

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Test Task")));

        verify(taskService, times(1)).getAllTasks();
    }

    @Test
    void updateTask_ShouldReturn200_WhenTaskExists() throws Exception {
        when(taskService.updateTask(eq(1L), any(TaskUpdateDTO.class))).thenReturn(taskDTO);

        mockMvc.perform(put("/api/tasks/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskUpdateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test Task")));

        verify(taskService, times(1)).updateTask(eq(1L), any(TaskUpdateDTO.class));
    }

    @Test
    void updateTaskStatus_ShouldReturn200_WhenTaskExists() throws Exception {
        when(taskService.updateTaskStatus(eq(1L), any(Task.TaskStatus.class))).thenReturn(taskDTO);

        mockMvc.perform(patch("/api/tasks/{id}/status", 1L)
                        .param("status", "COMPLETED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test Task")));

        verify(taskService, times(1)).updateTaskStatus(eq(1L), eq(Task.TaskStatus.COMPLETED));
    }

    @Test
    void deleteTask_ShouldReturn204_WhenTaskExists() throws Exception {
        doNothing().when(taskService).deleteTask(1L);

        mockMvc.perform(delete("/api/tasks/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(taskService, times(1)).deleteTask(1L);
    }
}
