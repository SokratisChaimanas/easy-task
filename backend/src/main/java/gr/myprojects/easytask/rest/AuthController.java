package gr.myprojects.easytask.rest;

import gr.myprojects.easytask.core.exceptions.AppServerException;
import gr.myprojects.easytask.core.exceptions.EntityAlreadyExistsException;
import gr.myprojects.easytask.core.exceptions.EntityInvalidArgumentException;
import gr.myprojects.easytask.dto.ResponseDTO;
import gr.myprojects.easytask.dto.user.UserLoginDTO;
import gr.myprojects.easytask.dto.user.UserReadOnlyDTO;
import gr.myprojects.easytask.dto.user.UserRegisterDTO;
import gr.myprojects.easytask.security.JwtTokenProvider;
import gr.myprojects.easytask.service.UserService;
import gr.myprojects.easytask.validation.ValidatorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final ValidatorUtil validatorUtil;

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<String>> loginUser(@RequestBody UserLoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        ResponseDTO<String> responseDTO = new ResponseDTO<>(token);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO<UserReadOnlyDTO>> registerUser(@Validated @RequestBody UserRegisterDTO registerDTO, BindingResult bindingResult)
            throws EntityAlreadyExistsException, AppServerException, EntityInvalidArgumentException {

        validatorUtil.validateEntity("User", bindingResult);

        UserReadOnlyDTO readOnlyDTO = userService.registerUser(registerDTO);

        ResponseDTO<UserReadOnlyDTO> responseDTO = new ResponseDTO<>(readOnlyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{username}")
    public ResponseEntity<ResponseDTO<Optional<UserReadOnlyDTO>>> checkUsername(@PathVariable String username)
            throws AppServerException {

        Optional<UserReadOnlyDTO> readonlyOptional = this.userService.findByUsername(username);

        ResponseDTO<Optional<UserReadOnlyDTO>> responseDTO = new ResponseDTO<>(readonlyOptional);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
}
