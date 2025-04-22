package scot.oskar.hmcts.backend.service;

import scot.oskar.hmcts.backend.data.dto.TaskCreateDTO;
import scot.oskar.hmcts.backend.data.dto.TaskDTO;
import scot.oskar.hmcts.backend.data.dto.TaskUpdateDTO;
import scot.oskar.hmcts.backend.data.model.Task;

import java.util.List;

public interface TaskService {

    /**
     * Create a new task
     *
     * @param taskCreateDTO Task creation data
     * @return Created task
     */
    TaskDTO createTask(TaskCreateDTO taskCreateDTO);

    /**
     * Get task by ID
     *
     * @param id Task ID
     * @return Task if found
     */
    TaskDTO getTaskById(Long id);

    /**
     * Get all tasks
     *
     * @return List of all tasks
     */
    List<TaskDTO> getAllTasks();

    /**
     * Update task
     *
     * @param id Task ID
     * @param taskUpdateDTO Task update data
     * @return Updated task
     */
    TaskDTO updateTask(Long id, TaskUpdateDTO taskUpdateDTO);

    /**
     * Update task status
     *
     * @param id Task ID
     * @param status New status
     * @return Updated task
     */
    TaskDTO updateTaskStatus(Long id, Task.TaskStatus status);

    /**
     * Delete task
     *
     * @param id Task ID
     */
    void deleteTask(Long id);
}