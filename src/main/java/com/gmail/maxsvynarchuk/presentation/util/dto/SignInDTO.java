package com.gmail.maxsvynarchuk.presentation.util.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Data transfer object for email and password
 */
@Data
@AllArgsConstructor
public class SignInDTO {
    @Size(max = 255)
    @Email(regexp = "^(.+@.+\\..+)$")
    private String email;

    @Size(min = 5, max = 255)
    @NotBlank
    private String password;
}
