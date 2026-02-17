package com.dirkficial.workout.service;

import com.dirkficial.workout.domain.CreateSetRequest;
import com.dirkficial.workout.domain.CreateWorkoutRequest;
import com.dirkficial.workout.domain.entity.Set;
import com.dirkficial.workout.domain.entity.SetStatus;
import com.dirkficial.workout.domain.entity.Workout;
import com.dirkficial.workout.domain.entity.WorkoutStatus;
import com.dirkficial.workout.repository.SetRepository;
import com.dirkficial.workout.repository.WorkoutRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WorkoutService {

    private WorkoutRepository workoutRepository;
    private SetRepository setRepository;

    public WorkoutService(WorkoutRepository workoutRepository, SetRepository setRepository) {
        this.workoutRepository = workoutRepository;
        this.setRepository = setRepository;
    }

    public Workout createWorkout(CreateWorkoutRequest request) {

        /* Parent */
        Workout workout = new Workout(
                request.name(),
                request.date(),
                WorkoutStatus.COMPLETED
        );

        /* Map DTO -> Entity */
        for (CreateSetRequest setRequest : request.sets()) {

            Set newSet = new Set();

            newSet.setExerciseName(setRequest.exerciseName());
            newSet.setMuscleGroup(setRequest.muscleGroup());
            newSet.setWeight(setRequest.weight());
            newSet.setReps(setRequest.reps());
            newSet.setRpe(setRequest.rpe());
            newSet.setNotes(setRequest.notes());
            newSet.setTut(setRequest.tut());
            newSet.setStatus(SetStatus.COMPLETED);

            Double currentMax = setRepository.findMaxWeightByExerciseName(setRequest.exerciseName());

            if (currentMax == null) {
                currentMax = 0.0;
            }

            if (setRequest.weight() > currentMax) {
                newSet.setPersonalRecord(true);
            } else {
                newSet.setPersonalRecord(false);
            }

            newSet.setWorkout(workout);
            workout.addSet(newSet);
        }
        return workoutRepository.save(workout);
    }

    /* Get History
    * Finds the last time the exercise was performed */
    public Optional<Set> getLastSetForExercise(String exerciseName) {
        return setRepository.findFirstByExerciseNameOrderByWorkoutDateDesc(exerciseName);
    }
}
