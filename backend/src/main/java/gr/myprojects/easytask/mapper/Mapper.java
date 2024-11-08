package gr.myprojects.easytask.mapper;

import gr.myprojects.easytask.core.enums.TaskStatus;
import gr.myprojects.easytask.dto.task.TaskInsertDTO;
import gr.myprojects.easytask.dto.task.TaskReadOnlyDTO;
import gr.myprojects.easytask.dto.user.UserReadOnlyDTO;
import gr.myprojects.easytask.dto.user.UserRegisterDTO;
import gr.myprojects.easytask.model.Task;
import gr.myprojects.easytask.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Mapper {
    public User mapToUser(UserRegisterDTO registerDTO) {
        return new User(null, registerDTO.getUsername(), registerDTO.getPassword(), null);
    }

    public UserReadOnlyDTO mapToUserReadOnlyDTO(User user) {
        return new UserReadOnlyDTO(user.getUuid(), user.getUsername());
    }

    public Task mapToTask(TaskInsertDTO insertDTO) {
        return Task.builder()
                .id(null)
                .title(insertDTO.getTitle())
                .description(insertDTO.getDescription())
                .dueDate(insertDTO.getDueDate())
                .status(TaskStatus.ACTIVE)
                .build();
    }

    public TaskReadOnlyDTO mapTaskToReadOnlyDTO(Task task) {
        return TaskReadOnlyDTO.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .dueDate(task.getDueDate())
                .status(task.getStatus().name())
                .uuid(task.getUuid())
                .build();
    }

    public List<TaskReadOnlyDTO> mapToTaskReadOnlyDTO(List<Task> tasks) {
        List<TaskReadOnlyDTO> readOnlyDTOList = new ArrayList<>();

        for (Task task : tasks) {
            readOnlyDTOList.add(mapTaskToReadOnlyDTO(task));
        }
        return readOnlyDTOList;
    }

}
