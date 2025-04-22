package scot.oskar.hmcts.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import scot.oskar.hmcts.backend.data.dto.TaskCreateDTO;
import scot.oskar.hmcts.backend.data.dto.TaskDTO;
import scot.oskar.hmcts.backend.data.dto.TaskUpdateDTO;
import scot.oskar.hmcts.backend.data.model.Task;
import scot.oskar.hmcts.backend.exception.ApiError;
import scot.oskar.hmcts.backend.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Tag(name = "Task Management API", description = "API for managing caseworker tasks")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new task", description = "Creates a new task with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task created successfully",
                    content = @Content(schema = @Schema(implementation = TaskDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public TaskDTO createTask(@Valid @RequestBody TaskCreateDTO taskCreateDTO) {
        return taskService.createTask(taskCreateDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a task by ID", description = "Returns a task based on the ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task found",
                    content = @Content(schema = @Schema(implementation = TaskDTO.class))),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public TaskDTO getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @GetMapping
    @Operation(summary = "Get all tasks", description = "Returns a list of all tasks")
    @ApiResponse(responseCode = "200", description = "List of tasks",
            content = @Content(schema = @Schema(implementation = TaskDTO.class)))
    public List<TaskDTO> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a task", description = "Updates a task with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task updated successfully",
                    content = @Content(schema = @Schema(implementation = TaskDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public TaskDTO updateTask(@PathVariable Long id, @Valid @RequestBody TaskUpdateDTO taskUpdateDTO) {
        return taskService.updateTask(id, taskUpdateDTO);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update task status", description = "Updates the status of a task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task status updated successfully",
                    content = @Content(schema = @Schema(implementation = TaskDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid status",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public TaskDTO updateTaskStatus(
            @PathVariable Long id,
            @RequestParam Task.TaskStatus status) {
        return taskService.updateTaskStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a task", description = "Deletes a task based on the ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Task deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}