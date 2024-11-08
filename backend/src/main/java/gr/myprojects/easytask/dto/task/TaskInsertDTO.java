package gr.myprojects.easytask.dto.task;

import gr.myprojects.easytask.core.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskInsertDTO {

    @NotBlank(message = "Title should not be empty")
    private String title;

    @NotBlank(message = "Task should have a description")
    private String description;

    private LocalDate dueDate;
}
