package com.dirkficial.workout.domain;

import com.dirkficial.workout.domain.entity.SetStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record CreateSetRequest(
        @NotBlank(message = "Exercise name is required")
        String exerciseName,

        @NotNull(message = "Please specify target muscle group")
        @Length(max = 250)
        String muscleGroup,

        @NotNull(message = "Weight is required")
        @Min(value = 0, message = "Weight cannot be negative")
        Double weight,

        @NotNull(message = "Reps is required")
        @Min(value = 1, message = "Must do at least one rep")
        Integer reps,

        @Min(value = 1, message = "RPE must be between 1 - 10")
        @Max(value = 10)
        Double rpe,

        String notes,
        Integer tut
) {

}
