//package gr.myprojects.easytask.validation.user;
//
//import jakarta.validation.Constraint;
//import jakarta.validation.Payload;
//
//import java.lang.annotation.ElementType;
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.lang.annotation.Target;
//
//@Constraint(validatedBy = UniqueUsernameValidator.class)
//@Target(ElementType.FIELD)
//@Retention(RetentionPolicy.RUNTIME)
//public @interface UniqueUsername {
//    String message() default "Username is already in use";
//
//    Class<?>[] groups() default {}; // Used for grouping constraints
//    Class<? extends Payload>[] payload() default {}; // Used to carry additional data
//}
