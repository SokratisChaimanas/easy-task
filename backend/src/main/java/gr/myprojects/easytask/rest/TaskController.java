package gr.myprojects.easytask.rest;

import gr.myprojects.easytask.core.exceptions.AppServerException;
import gr.myprojects.easytask.core.exceptions.EntityInvalidArgumentException;
import gr.myprojects.easytask.core.exceptions.EntityNotAuthorizedException;
import gr.myprojects.easytask.core.exceptions.EntityNotFoundException;
import gr.myprojects.easytask.dto.ResponseDTO;
import gr.myprojects.easytask.dto.task.TaskInsertDTO;
import gr.myprojects.easytask.dto.task.TaskReadOnlyDTO;
import gr.myprojects.easytask.service.TaskService;
import gr.myprojects.easytask.validation.ValidatorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final ValidatorUtil validatorUtil;

    @GetMapping("")
    public ResponseEntity<ResponseDTO<List<TaskReadOnlyDTO>>> getAllTasks()
            throws AppServerException, EntityNotFoundException {
        List<TaskReadOnlyDTO> tasksList = taskService.getAllTasksForUser();

        ResponseDTO<List<TaskReadOnlyDTO>> responseDTO = new ResponseDTO<>(tasksList);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PostMapping("")
    public ResponseEntity<ResponseDTO<TaskReadOnlyDTO>> addTask(@RequestBody @Validated TaskInsertDTO insertDTO, BindingResult bindingResult)
            throws EntityInvalidArgumentException, AppServerException, EntityNotFoundException {
        validatorUtil.validateEntity("Task", bindingResult);

        TaskReadOnlyDTO readOnlyDTO = taskService.insertTask(insertDTO);
        ResponseDTO<TaskReadOnlyDTO> responseDTO = new ResponseDTO<>(readOnlyDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/complete/{taskUuid}")
    public ResponseEntity<ResponseDTO<TaskReadOnlyDTO>> completeTask(@PathVariable String taskUuid)
            throws EntityNotAuthorizedException, AppServerException, EntityNotFoundException {

        TaskReadOnlyDTO readOnlyDTO = taskService.completeTask(taskUuid);
        ResponseDTO<TaskReadOnlyDTO> responseDTO = new ResponseDTO<>(readOnlyDTO);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
}
