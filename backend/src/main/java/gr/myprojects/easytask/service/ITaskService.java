package gr.myprojects.easytask.service;

import gr.myprojects.easytask.core.exceptions.AppServerException;
import gr.myprojects.easytask.core.exceptions.EntityNotAuthorizedException;
import gr.myprojects.easytask.core.exceptions.EntityNotFoundException;
import gr.myprojects.easytask.dto.task.TaskInsertDTO;
import gr.myprojects.easytask.dto.task.TaskReadOnlyDTO;

import java.util.List;

public interface ITaskService {
    TaskReadOnlyDTO insertTask(TaskInsertDTO insertDTO) throws AppServerException, EntityNotFoundException;
//    TaskReadOnlyDTO updateTask(TaskUpdateDTO updateDTO) throws AppServerException, EntityNotFoundException;
    List<TaskReadOnlyDTO> getAllTasksForUser() throws AppServerException, EntityNotFoundException;
    TaskReadOnlyDTO completeTask(String uuid) throws AppServerException, EntityNotFoundException, EntityNotAuthorizedException;
}
