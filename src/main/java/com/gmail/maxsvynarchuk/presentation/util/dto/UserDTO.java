package com.gmail.maxsvynarchuk.presentation.util.dto;

import com.gmail.maxsvynarchuk.persistence.entity.User;
import com.gmail.maxsvynarchuk.util.type.Gender;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;
import java.time.LocalDate;

/**
 * Data transfer object for creation User
 *
 * @see User
 */
@Data
public class UserDTO {
    private Long id;

    @Size(max = 255)
    @Pattern(regexp = "^\\p{Lu}[\\p{L}&&[^\\p{Lu}]]+$")
    private String firstName;

    @Size(max = 255)
    @Pattern(regexp = "^\\p{Lu}[\\p{L}&&[^\\p{Lu}]]+$")
    private String lastName;

    @Size(max = 255)
    @Email(regexp = "^(.+@.+\\..+)$")
    private String email;

    @Size(min = 5, max = 255)
    @NotBlank
    private String password;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Gender gender;

    private Integer roleId;
}
