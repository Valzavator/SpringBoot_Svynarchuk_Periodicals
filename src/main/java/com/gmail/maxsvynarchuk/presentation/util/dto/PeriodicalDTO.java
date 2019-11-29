package com.gmail.maxsvynarchuk.presentation.util.dto;

import com.gmail.maxsvynarchuk.persistence.entity.Periodical;
import com.gmail.maxsvynarchuk.util.type.PeriodicalStatus;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;


/**
 * Data transfer object for creation and edition Periodical
 *
 * @see Periodical
 */
@Data
public class PeriodicalDTO {
    private Long id;

    @Size(max = 255)
    @Pattern(regexp = "^[\\p{L}\\p{Digit}\\p{javaWhitespace}]+$")
    private String name;

    @Digits(integer=10, fraction = 2)
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100000000.0", inclusive = false)
    @NotNull
    private BigDecimal price;

    @Size(max = 1000)
    @Pattern(regexp = "^[\\p{L}\\p{Digit}\\p{Punct}\\p{javaWhitespace}]+$")
    private String description;

    private PeriodicalStatus status;

    @Min(1)
    @NotNull
    private Integer frequencyId;

    @Min(1)
    @NotNull
    private Integer periodicalTypeId;

    @Min(1)
    @NotNull
    private Long publisherId;
}
