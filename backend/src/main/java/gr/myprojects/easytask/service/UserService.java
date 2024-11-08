package gr.myprojects.easytask.service;

import gr.myprojects.easytask.core.exceptions.AppServerException;
import gr.myprojects.easytask.core.exceptions.EntityAlreadyExistsException;
import gr.myprojects.easytask.dto.user.UserReadOnlyDTO;
import gr.myprojects.easytask.dto.user.UserRegisterDTO;
import gr.myprojects.easytask.mapper.Mapper;
import gr.myprojects.easytask.model.User;
import gr.myprojects.easytask.repository.TaskRepository;
import gr.myprojects.easytask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;
    private final Mapper mapper;
    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserReadOnlyDTO registerUser(UserRegisterDTO registerDTO) throws EntityAlreadyExistsException, AppServerException {
        try {
            if (userRepository.findByUsername(registerDTO.getUsername()).isPresent())
                throw new EntityAlreadyExistsException("User", "User with username: " + registerDTO.getUsername() + " already exists");

            User user = mapper.mapToUser(registerDTO);
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            userRepository.save(user);
            UserReadOnlyDTO readOnlyDTO = mapper.mapToUserReadOnlyDTO(user);
            LOGGER.info("User with UUID: {} and Username: {} was registered successfully", readOnlyDTO.getUUID(), readOnlyDTO.getUsername() );

            return readOnlyDTO;
        } catch (EntityAlreadyExistsException e) {
            LOGGER.warn("Username: {} could not inserted because it already exists", registerDTO.getUsername());
            throw e;
        } catch (Exception e) {
            LOGGER.error("Unexpected error. User with username: {} could not get inserted", registerDTO.getUsername());
            throw new AppServerException("Unexpected error while inserting user to database");
        }
    }

    @Override
    public Optional<UserReadOnlyDTO> findByUsername(String username) throws AppServerException {
        try {
            return userRepository.findByUsername(username)
                    .map(mapper::mapToUserReadOnlyDTO);

        } catch (Exception e) {
            LOGGER.error("Unexpected error. User with username: {} could not get fetched", username);
            throw new AppServerException("Unexpected error while fetch user from database");
        }
    }
}
