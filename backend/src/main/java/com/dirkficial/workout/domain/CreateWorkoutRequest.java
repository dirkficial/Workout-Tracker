package com.dirkficial.workout.domain;

import com.dirkficial.workout.domain.entity.WorkoutStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;
import java.util.List;

public record CreateWorkoutRequest(

        @NotBlank(message = "Name is required")
        String name,

        @NotNull
        @PastOrPresent(message = "You cannot log future workouts")
        LocalDate date,

        @Valid
        @NotNull
        List<CreateSetRequest> sets
) {

}
