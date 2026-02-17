package com.dirkficial.workout.repository;

import com.dirkficial.workout.domain.entity.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SetRepository extends JpaRepository<Set, Long> {

    /* Useful to view a full Workout history */
    List<Set> findByWorkoutId(Long workoutId);

    // Logic:
    // "Find First" -> LIMIT 1
    // "ByExerciseName" -> WHERE exercise_name = ?
    // "OrderByWorkoutDate" -> JOIN workouts w ON s.workout_id = w.id ORDER BY w.date
    // "Desc" -> DESC (Newest first)
    Optional<Set> findFirstByExerciseNameOrderByWorkoutDateDesc(String exerciseName);

    /* Another way of doing the same thing as above */
//    @Query("SELECT s FROM Set s WHERE s.exerciseName = :name ORDER BY s.workout.date DESC LIMIT 1")
//    Optional<Set> findLatestSet(@Param("name") String exerciseName);

//    Finds the highest weight in the weight column for a specific exercise
    @Query("SELECT MAX(s.weight) FROM Set s WHERE LOWER(s.exerciseName) = LOWER(:exerciseName)")
    Double findMaxWeightByExerciseName(@Param("exerciseName") String exerciseName);
}
