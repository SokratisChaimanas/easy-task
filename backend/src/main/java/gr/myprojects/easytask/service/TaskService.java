package gr.myprojects.easytask.service;

import gr.myprojects.easytask.core.enums.TaskStatus;
import gr.myprojects.easytask.core.exceptions.AppServerException;
import gr.myprojects.easytask.core.exceptions.EntityNotAuthorizedException;
import gr.myprojects.easytask.core.exceptions.EntityNotFoundException;
import gr.myprojects.easytask.dto.task.TaskInsertDTO;
import gr.myprojects.easytask.dto.task.TaskReadOnlyDTO;
import gr.myprojects.easytask.mapper.Mapper;
import gr.myprojects.easytask.model.Task;
import gr.myprojects.easytask.model.User;
import gr.myprojects.easytask.repository.TaskRepository;
import gr.myprojects.easytask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService implements ITaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final Mapper mapper;
    private final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TaskReadOnlyDTO insertTask(TaskInsertDTO insertDTO) throws AppServerException, EntityNotFoundException {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new EntityNotFoundException("User", "User with username: " + username + " could not be found in the database"));

            Task task = mapper.mapToTask(insertDTO);

            user.addTask(task);
            taskRepository.save(task);
            LOGGER.info("Task with id: {} was inserted successfully", task.getId());
            return mapper.mapTaskToReadOnlyDTO(task);
        } catch (EntityNotFoundException e) {
            LOGGER.error("Task could not get inserted. Exception Message: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            LOGGER.error("Unexpected Error. Task with title: {} could not get inserted", insertDTO.getTitle());
            throw new AppServerException("Unexpected error while inserting task");
        }
    }

    @Override
    public List<TaskReadOnlyDTO> getAllTasksForUser() throws AppServerException, EntityNotFoundException {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new EntityNotFoundException("User", "User with username: " + username + " could not be found in the database"));

            List<Task> taskList = taskRepository.findByUserId(user.getId());
//                    .stream().filter(task ->
//                    task.getStatus().equals(TaskStatus.ACTIVE)).toList();

            return mapper.mapToTaskReadOnlyDTO(taskList);
        } catch (EntityNotFoundException e) {
            LOGGER.error("Tasks could not get fetched. Exception Message: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            LOGGER.error("Unexpected Error. Tasks could not get fetched");
            throw new AppServerException("Unexpected error while fetching task");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TaskReadOnlyDTO completeTask(String uuid) throws AppServerException, EntityNotFoundException, EntityNotAuthorizedException {
        String activeUsername = "";
        try {
            activeUsername = SecurityContextHolder.getContext().getAuthentication().getName();

            Task task = taskRepository.findByUuid(uuid)
                    .orElseThrow(() -> new EntityNotFoundException("Task", "Task with UUID: " + uuid + " was not found"));

            if (!task.getUser().getUsername().equals(activeUsername)) throw new EntityNotAuthorizedException("User", "User with username: " + activeUsername + " is not autorized for this action");

            task.setStatus(TaskStatus.COMPLETED);
            LOGGER.info("Task with UUID: {} was successfully completed", uuid);
            taskRepository.save(task);
            return mapper.mapTaskToReadOnlyDTO(task);
        } catch (EntityNotFoundException e) {
            LOGGER.error("Task could not get completed. Task with UUID: {} could not get found", uuid);
            throw e;
        } catch (EntityNotAuthorizedException e) {
            LOGGER.error("Task could not get completed. Unauthorized user {} tried to access the task", activeUsername);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Task could not get completed. Unexpected error occurred while completing task with UUID: {}", uuid);
            throw new AppServerException("Unexpected error while completing task with UUID: " + uuid);
        }
    }
}
