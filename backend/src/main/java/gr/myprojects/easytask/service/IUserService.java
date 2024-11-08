package gr.myprojects.easytask.service;

import gr.myprojects.easytask.core.exceptions.AppServerException;
import gr.myprojects.easytask.core.exceptions.EntityAlreadyExistsException;
import gr.myprojects.easytask.core.exceptions.EntityNotFoundException;
import gr.myprojects.easytask.dto.user.UserReadOnlyDTO;
import gr.myprojects.easytask.dto.user.UserRegisterDTO;

import java.util.Optional;

public interface IUserService {
    UserReadOnlyDTO registerUser(UserRegisterDTO registerDTO) throws EntityAlreadyExistsException, AppServerException;
    Optional<UserReadOnlyDTO> findByUsername(String username) throws AppServerException;
}
