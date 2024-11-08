//package gr.myprojects.easytask.validation.user;
//
//import gr.myprojects.easytask.core.exceptions.AppServerException;
//import gr.myprojects.easytask.service.UserService;
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorContext;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
//    private final UserService userService;
//
//    @Override
//    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
//        try {
//            return userService.findByUsername(username).isEmpty();
//        } catch (AppServerException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
