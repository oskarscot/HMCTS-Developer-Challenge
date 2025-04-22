package scot.oskar.hmcts.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import scot.oskar.hmcts.backend.data.model.Task;
import scot.oskar.hmcts.backend.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TaskRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private TaskRepository taskRepository;

    private Task task1;
    private Task task2;
    private LocalDateTime now;
    private LocalDateTime future;
    private LocalDateTime past;

    @BeforeEach
    void setUp() {
        // Clear the repository before each test
        taskRepository.deleteAll();

        now = LocalDateTime.now();
        future = now.plusDays(7);
        past = now.minusDays(7);

        task1 = Task.builder()
                .title("Test Task 1")
                .description("Test Description 1")
                .status(Task.TaskStatus.PENDING)
                .dueDate(future)
                .build();

        task2 = Task.builder()
                .title("Test Task 2")
                .description("Test Description 2")
                .status(Task.TaskStatus.IN_PROGRESS)
                .dueDate(past)
                .build();
    }

    @Test
    void shouldSaveAndRetrieveTask() {
        // Save task
        Task savedTask = taskRepository.save(task1);

        // Find by ID
        Task foundTask = taskRepository.findById(savedTask.getId()).orElse(null);

        // Assert
        assertThat(foundTask).isNotNull();
        assertThat(foundTask.getId()).isEqualTo(savedTask.getId());
        assertThat(foundTask.getTitle()).isEqualTo("Test Task 1");
        assertThat(foundTask.getStatus()).isEqualTo(Task.TaskStatus.PENDING);
        assertThat(foundTask.getCreatedAt()).isNotNull();
        assertThat(foundTask.getUpdatedAt()).isNotNull();
    }

    @Test
    void shouldFindByDueDateBefore() {
        // Save tasks
        taskRepository.save(task1); // future due date
        taskRepository.save(task2); // past due date

        // Find tasks with due date before now
        List<Task> overdueTasks = taskRepository.findByDueDateBefore(now);

        // Assert
        assertThat(overdueTasks).hasSize(1);
        assertThat(overdueTasks.get(0).getTitle()).isEqualTo("Test Task 2");
    }

    @Test
    void shouldFindByStatus() {
        // Save tasks
        taskRepository.save(task1); // PENDING
        taskRepository.save(task2); // IN_PROGRESS

        // Find tasks by status
        List<Task> pendingTasks = taskRepository.findByStatus(Task.TaskStatus.PENDING);
        List<Task> inProgressTasks = taskRepository.findByStatus(Task.TaskStatus.IN_PROGRESS);

        // Assert
        assertThat(pendingTasks).hasSize(1);
        assertThat(pendingTasks.get(0).getTitle()).isEqualTo("Test Task 1");

        assertThat(inProgressTasks).hasSize(1);
        assertThat(inProgressTasks.get(0).getTitle()).isEqualTo("Test Task 2");
    }

    @Test
    void shouldFindByStatusAndDueDateBefore() {
        // Save tasks
        taskRepository.save(task1); // PENDING, future
        taskRepository.save(task2); // IN_PROGRESS, past

        // Create another task with PENDING status but past due date
        Task task3 = Task.builder()
                .title("Test Task 3")
                .description("Test Description 3")
                .status(Task.TaskStatus.PENDING)
                .dueDate(past)
                .build();
        taskRepository.save(task3);

        // Find PENDING tasks that are overdue
        List<Task> pendingOverdueTasks = taskRepository.findByStatusAndDueDateBefore(
                Task.TaskStatus.PENDING, now);

        // Assert
        assertThat(pendingOverdueTasks).hasSize(1);
        assertThat(pendingOverdueTasks.get(0).getTitle()).isEqualTo("Test Task 3");
    }
}
