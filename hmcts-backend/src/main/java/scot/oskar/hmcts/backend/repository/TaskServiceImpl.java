package scot.oskar.hmcts.backend.repository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import scot.oskar.hmcts.backend.data.dto.TaskCreateDTO;
import scot.oskar.hmcts.backend.data.dto.TaskDTO;
import scot.oskar.hmcts.backend.data.dto.TaskUpdateDTO;
import scot.oskar.hmcts.backend.data.model.Task;
import scot.oskar.hmcts.backend.exception.ResourceNotFoundException;
import scot.oskar.hmcts.backend.service.TaskService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    @Transactional
    public TaskDTO createTask(TaskCreateDTO taskCreateDTO) {
        Task task = taskCreateDTO.toEntity();
        Task savedTask = taskRepository.save(task);
        return TaskDTO.fromEntity(savedTask);
    }

    @Override
    @Transactional
    public TaskDTO getTaskById(Long id) {
        Task task = findTaskById(id);
        return TaskDTO.fromEntity(task);
    }

    @Override
    @Transactional
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(TaskDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TaskDTO updateTask(Long id, TaskUpdateDTO taskUpdateDTO) {
        Task task = findTaskById(id);
        taskUpdateDTO.updateEntity(task);
        Task updatedTask = taskRepository.save(task);
        return TaskDTO.fromEntity(updatedTask);
    }

    @Override
    @Transactional
    public TaskDTO updateTaskStatus(Long id, Task.TaskStatus status) {
        Task task = findTaskById(id);
        task.setStatus(status);
        Task updatedTask = taskRepository.save(task);
        return TaskDTO.fromEntity(updatedTask);
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }

    private Task findTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
    }
}
