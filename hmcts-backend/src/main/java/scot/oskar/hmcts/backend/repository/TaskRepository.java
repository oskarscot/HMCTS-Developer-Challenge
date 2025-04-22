package scot.oskar.hmcts.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import scot.oskar.hmcts.backend.data.model.Task;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Find tasks with due date before the given date
    List<Task> findByDueDateBefore(LocalDateTime dueDate);

    // Find tasks by status
    List<Task> findByStatus(Task.TaskStatus status);

    // Find tasks by status and due date before the given date
    List<Task> findByStatusAndDueDateBefore(Task.TaskStatus status, LocalDateTime dueDate);
}
