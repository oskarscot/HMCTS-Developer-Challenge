package scot.oskar.hmcts.backend.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import scot.oskar.hmcts.backend.data.model.Task;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskUpdateDTO {

    private String title;
    private String description;

    @NotNull(message = "Status is required")
    private Task.TaskStatus status;

    @FutureOrPresent(message = "Due date must be in the present or future")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dueDate;

    public void updateEntity(Task task) {
        if (this.title != null) {
            task.setTitle(this.title);
        }

        // description is nullable
        task.setDescription(this.description);

        task.setStatus(this.status);

        if (this.dueDate != null) {
            task.setDueDate(this.dueDate);
        }
    }
}