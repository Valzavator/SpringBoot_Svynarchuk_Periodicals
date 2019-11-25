package com.gmail.maxsvynarchuk.presentation.util.validator;

import com.gmail.maxsvynarchuk.persistence.entity.User;
import com.gmail.maxsvynarchuk.presentation.controller.AuthorizationController;
import com.gmail.maxsvynarchuk.presentation.util.constants.Attributes;
import com.gmail.maxsvynarchuk.presentation.util.validator.impl.EmailValidator;
import com.gmail.maxsvynarchuk.presentation.util.validator.impl.PasswordValidator;
import com.gmail.maxsvynarchuk.presentation.util.validator.impl.UserNameValidator;

import java.util.HashMap;
import java.util.Map;

/**
 * Manager for validation data in controllers methods
 *
 * @author Maksym Svynarhcuk
 */
public class ValidatorManager {
    /**
     * Validation data in {@link AuthorizationController#postSignIn}
     */
    public static Map<String, Boolean> validateSignInParameters(User userDTO) {
        Map<String, Boolean> errors = new HashMap<>();

        validateField(new EmailValidator(),
                userDTO.getEmail(),
                Attributes.ERROR_EMAIL,
                errors);
        validateField(new PasswordValidator(),
                userDTO.getPassword(),
                Attributes.ERROR_PASSWORD,
                errors);

        return errors;
    }

    /**
     * Validation data in {@link AuthorizationController#}
     */
    public static Map<String, Boolean> validateSignUpParameters(User userDTO) {
        Map<String, Boolean> errors = new HashMap<>();

        validateField(new EmailValidator(),
                userDTO.getEmail(),
                Attributes.ERROR_EMAIL,
                errors);
        validateField(new PasswordValidator(),
                userDTO.getPassword(),
                Attributes.ERROR_PASSWORD,
                errors);
        validateField(new UserNameValidator(),
                userDTO.getFirstName(),
                Attributes.ERROR_FIRST_NAME,
                errors);
        validateField(new UserNameValidator(),
                userDTO.getLastName(),
                Attributes.ERROR_LAST_NAME,
                errors);

        return errors;
    }

//    /**
//     * Validation data in {@link PostCreatePeriodicalCommand#execute} and {@link PostEditPeriodicalCommand#execute}
//     */
//    public static Map<String, Boolean> validatePeriodicalParameters(Periodical periodicalDTO) {
//        Map<String, Boolean> errors = new HashMap<>();
//
//        validateField(new TitleValidator(),
//                periodicalDTO.getName(),
//                Attributes.ERROR_PERIODICAL_NAME,
//                errors);
//        validateField(new DescriptionValidator(),
//                periodicalDTO.getDescription(),
//                Attributes.ERROR_PERIODICAL_DESCRIPTION,
//                errors);
//        validateField(new PeriodicalPriceValidator(),
//                periodicalDTO.getPrice(),
//                Attributes.ERROR_PERIODICAL_PRICE,
//                errors);
//
//        return errors;
//    }
//
//    /**
//     * Validation data in {@link PostCreateIssueCommand#execute}
//     */
//    public static Map<String, Boolean> validateIssueParameters(PeriodicalIssue periodicalIssueDTO) {
//        Map<String, Boolean> errors = new HashMap<>();
//
//        validateField(new TitleValidator(),
//                periodicalIssueDTO.getName(),
//                Attributes.ERROR_ISSUE_NAME,
//                errors);
//        validateField(new IssueNumberValidator(),
//                periodicalIssueDTO.getIssueNumber(),
//                Attributes.ERROR_ISSUE_NUMBER,
//                errors);
//        validateField(new DescriptionValidator(),
//                periodicalIssueDTO.getDescription(),
//                Attributes.ERROR_ISSUE_DESCRIPTION,
//                errors);
//
//        return errors;
//    }

    /**
     * Performs validation of given field with provided validator.
     * If error occurs add {@code ValidationResult} object with error message to list of validation results.
     *
     * @param validator      implementation of {@code Validator}
     * @param field          field for validation
     * @param errorAttribute attribute name for presentation
     * @param errors         container for all {@code ValidationResult}
     * @param <T>            type of field for validation
     * @see Validator
     */
    private static <T> void validateField(Validator<T> validator,
                                          T field,
                                          String errorAttribute,
                                          Map<String, Boolean> errors) {
        if (!validator.isValid(field)) {
            errors.put(errorAttribute, true);
        }
    }
}
