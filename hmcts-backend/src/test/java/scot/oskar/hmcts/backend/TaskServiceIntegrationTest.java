package scot.oskar.hmcts.backend;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import scot.oskar.hmcts.backend.data.dto.TaskCreateDTO;
import scot.oskar.hmcts.backend.data.dto.TaskDTO;
import scot.oskar.hmcts.backend.data.dto.TaskUpdateDTO;
import scot.oskar.hmcts.backend.data.model.Task;
import scot.oskar.hmcts.backend.exception.ResourceNotFoundException;
import scot.oskar.hmcts.backend.repository.TaskRepository;
import scot.oskar.hmcts.backend.service.TaskService;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TaskServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    private TaskCreateDTO taskCreateDTO;
    private LocalDateTime dueDate;

    @BeforeEach
    void setUp() {
        dueDate = LocalDateTime.now().plusDays(1);

        taskCreateDTO = TaskCreateDTO.builder()
                .title("Integration Test Task")
                .description("Task for Integration Testing")
                .status(Task.TaskStatus.PENDING)
                .dueDate(dueDate)
                .build();
    }

    @AfterEach
    void tearDown() {
        taskRepository.deleteAll();
    }

    @Test
    void shouldCreateAndRetrieveTask() {
        // Create a new task
        TaskDTO createdTask = taskService.createTask(taskCreateDTO);

        // Verify the task was created with correct data
        assertThat(createdTask).isNotNull();
        assertThat(createdTask.getId()).isNotNull();
        assertThat(createdTask.getTitle()).isEqualTo("Integration Test Task");
        assertThat(createdTask.getStatus()).isEqualTo(Task.TaskStatus.PENDING);
        assertThat(createdTask.getCreatedAt()).isNotNull();

        // Get the task by ID
        TaskDTO retrievedTask = taskService.getTaskById(createdTask.getId());

        // Verify the retrieved task matches the created one
        assertThat(retrievedTask).isNotNull();
        assertThat(retrievedTask.getId()).isEqualTo(createdTask.getId());
        assertThat(retrievedTask.getTitle()).isEqualTo(createdTask.getTitle());
        assertThat(retrievedTask.getStatus()).isEqualTo(createdTask.getStatus());
    }

    @Test
    void shouldUpdateTask() {
        // Create a task
        TaskDTO createdTask = taskService.createTask(taskCreateDTO);

        // Create update DTO
        TaskUpdateDTO updateDTO = TaskUpdateDTO.builder()
                .title("Updated Task Title")
                .description("Updated Task Description")
                .status(Task.TaskStatus.IN_PROGRESS)
                .dueDate(dueDate.plusDays(1))
                .build();

        // Update the task
        TaskDTO updatedTask = taskService.updateTask(createdTask.getId(), updateDTO);

        // Verify the update was applied
        assertThat(updatedTask).isNotNull();
        assertThat(updatedTask.getId()).isEqualTo(createdTask.getId());
        assertThat(updatedTask.getTitle()).isEqualTo("Updated Task Title");
        assertThat(updatedTask.getDescription()).isEqualTo("Updated Task Description");
        assertThat(updatedTask.getStatus()).isEqualTo(Task.TaskStatus.IN_PROGRESS);
    }

    @Test
    void shouldUpdateTaskStatus() {
        // Create a task
        TaskDTO createdTask = taskService.createTask(taskCreateDTO);

        // Update just the status
        TaskDTO updatedTask = taskService.updateTaskStatus(createdTask.getId(), Task.TaskStatus.COMPLETED);

        // Verify only the status was updated
        assertThat(updatedTask).isNotNull();
        assertThat(updatedTask.getId()).isEqualTo(createdTask.getId());
        assertThat(updatedTask.getTitle()).isEqualTo(createdTask.getTitle());
        assertThat(updatedTask.getStatus()).isEqualTo(Task.TaskStatus.COMPLETED);
    }

    @Test
    void shouldDeleteTask() {
        // Create a task
        TaskDTO createdTask = taskService.createTask(taskCreateDTO);

        // Delete the task
        taskService.deleteTask(createdTask.getId());

        // Verify the task is deleted
        assertThatThrownBy(() -> taskService.getTaskById(createdTask.getId()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void shouldGetAllTasks() {
        // Create multiple tasks
        taskService.createTask(taskCreateDTO);

        TaskCreateDTO task2 = TaskCreateDTO.builder()
                .title("Another Task")
                .status(Task.TaskStatus.IN_PROGRESS)
                .dueDate(dueDate)
                .build();
        taskService.createTask(task2);

        // Get all tasks
        List<TaskDTO> allTasks = taskService.getAllTasks();

        // Verify
        assertThat(allTasks).isNotNull();
        assertThat(allTasks).hasSize(2);
    }
}
