package scot.oskar.hmcts.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import scot.oskar.hmcts.backend.data.dto.TaskCreateDTO;
import scot.oskar.hmcts.backend.data.dto.TaskDTO;
import scot.oskar.hmcts.backend.data.dto.TaskUpdateDTO;
import scot.oskar.hmcts.backend.data.model.Task;
import scot.oskar.hmcts.backend.exception.ResourceNotFoundException;
import scot.oskar.hmcts.backend.repository.TaskRepository;
import scot.oskar.hmcts.backend.repository.TaskServiceImpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;
    private TaskCreateDTO taskCreateDTO;
    private TaskUpdateDTO taskUpdateDTO;
    private LocalDateTime dueDate;

    @BeforeEach
    void setUp() {
        dueDate = LocalDateTime.now().plusDays(1);

        task = Task.builder()
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
    void createTask_ShouldReturnCreatedTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskDTO result = taskService.createTask(taskCreateDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(task.getId());
        assertThat(result.getTitle()).isEqualTo(task.getTitle());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void getTaskById_ShouldReturnTask_WhenTaskExists() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        TaskDTO result = taskService.getTaskById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(task.getId());
        assertThat(result.getTitle()).isEqualTo(task.getTitle());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void getTaskById_ShouldThrowException_WhenTaskDoesNotExist() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.getTaskById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Task not found with id: 1");

        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void getAllTasks_ShouldReturnAllTasks() {
        when(taskRepository.findAll()).thenReturn(Arrays.asList(task));

        List<TaskDTO> result = taskService.getAllTasks();

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(task.getId());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void updateTask_ShouldReturnUpdatedTask_WhenTaskExists() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskDTO result = taskService.updateTask(1L, taskUpdateDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(task.getId());
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void updateTask_ShouldThrowException_WhenTaskDoesNotExist() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.updateTask(1L, taskUpdateDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Task not found with id: 1");

        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void updateTaskStatus_ShouldReturnUpdatedTask_WhenTaskExists() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskDTO result = taskService.updateTaskStatus(1L, Task.TaskStatus.COMPLETED);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(task.getId());
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void deleteTask_ShouldDeleteTask_WhenTaskExists() {
        when(taskRepository.existsById(1L)).thenReturn(true);
        doNothing().when(taskRepository).deleteById(1L);

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).existsById(1L);
        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteTask_ShouldThrowException_WhenTaskDoesNotExist() {
        when(taskRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> taskService.deleteTask(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Task not found with id: 1");

        verify(taskRepository, times(1)).existsById(1L);
        verify(taskRepository, never()).deleteById(anyLong());
    }
}