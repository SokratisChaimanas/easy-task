package gr.myprojects.easytask.dto.user;

import gr.myprojects.easytask.validation.user.PasswordMatches;
//import gr.myprojects.easytask.validation.user.UniqueUsername;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@PasswordMatches
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRegisterDTO {

//    @UniqueUsername
    @NotBlank(message = "Username should not be empty")
    @Size(min = 3, max = 20, message = "Username should be between 3-20 characters long")
    private String username;

    @NotBlank(message = "Password should not be empty")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters long, contain at least one letter, one number, and one special character.")
    private String password;

    @NotBlank(message = "Confirm Password should not be empty")
    private String confirmPassword;
}
