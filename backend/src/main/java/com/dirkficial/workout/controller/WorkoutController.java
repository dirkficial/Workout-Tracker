package com.dirkficial.workout.controller;

import com.dirkficial.workout.domain.CreateWorkoutRequest;
import com.dirkficial.workout.domain.entity.Set;
import com.dirkficial.workout.domain.entity.Workout;
import com.dirkficial.workout.service.WorkoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @PostMapping
    public ResponseEntity<Workout> recordWorkout(@RequestBody CreateWorkoutRequest request) {
        Workout savedWorkout = workoutService.createWorkout(request);
        // Returns HTTP 200 OK and the saved data
        return ResponseEntity.ok(savedWorkout);
    }

    @GetMapping("/history")
    public ResponseEntity<Set> getExerciseHistory(@RequestParam String exercise) {
        Optional<Set> lastSetBox = workoutService.getLastSetForExercise(exercise);

        // Logic: Open the box. If empty, return 404 (Not Found).
        return lastSetBox
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
