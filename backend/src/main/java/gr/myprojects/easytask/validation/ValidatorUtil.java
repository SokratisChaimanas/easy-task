package gr.myprojects.easytask.validation;

import gr.myprojects.easytask.core.exceptions.EntityInvalidArgumentException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

@Component
public class ValidatorUtil {

    public void validateEntity(String entity, BindingResult bindingResult) throws EntityInvalidArgumentException {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            bindingResult.getAllErrors().forEach(error -> {
                errorMessages.add(error.getDefaultMessage());
            });

            throw new EntityInvalidArgumentException (entity, String.join(", ", errorMessages));
        }

    }
}
