package com.gmail.maxsvynarchuk.presentation.util.dto;

import com.gmail.maxsvynarchuk.persistence.entity.PeriodicalIssue;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Data transfer object for creation PeriodicalIssue
 *
 * @see PeriodicalIssue
 */
@Data
public class PeriodicalIssueDTO {
    private Long id;

    @Size(max = 255)
    @Pattern(regexp = "^[\\p{L}\\p{Digit}\\p{javaWhitespace}]+$")
    private String name;

    @Size(max = 10)
    @Pattern(regexp = "^[\\p{L}\\p{Digit}\\p{javaWhitespace}]+$")
    private String issueNumber;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    private LocalDate publicationDate;

    @Size(max = 1000)
    @Pattern(regexp = "^[\\p{L}\\p{Digit}\\p{Punct}\\p{javaWhitespace}]+$")
    private String description;

    @Min(1)
    @NotNull
    private Long periodicalId;
}
