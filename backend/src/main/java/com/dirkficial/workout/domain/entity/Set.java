package com.dirkficial.workout.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "sets")
public class Set {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "exercise_name", nullable = false)
    private String exerciseName;

    @Column(name = "weight", nullable = false)
    private Double weight;

    @Column(name = "reps", nullable = false)
    private Integer reps;

    @Column(name = "rpe")
    private Double rpe;

    @Column(name = "notes", length = 1000)
    private String notes;

    @Column(name = "tut")
    private Integer tut;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SetStatus status;

    private boolean personalRecord;

    private String muscleGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_id", nullable = false)
    @JsonIgnore
    private Workout workout;

    public Set() {
    }

    public Set(/*Long id,*/ String exerciseName, Double weight, Integer reps, Double rpe, String notes, Integer tut, SetStatus status, Workout workout) {
//        this.id = id;
        this.exerciseName = exerciseName;
        this.weight = weight;
        this.reps = reps;
        this.rpe = rpe;
        this.notes = notes;
        this.tut = tut;
        this.status = status;
        this.workout = workout;
    }

    public Double getVolume() {
        if (reps == null || weight == null) {
            return 0.0;
        }
        return reps * weight;
    }

    public Double getEstimated1RM() {
        if (reps == null || weight == null) {
            return 0.0;
        }
        Double PR = Math.round(weight * (1 + (reps/30)) * 2) / 2.0;
        return PR;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Set set = (Set) o;
        return Objects.equals(id, set.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Set{" +
                "exerciseName=" + exerciseName +
                ", status=" + status +
                ", tut=" + tut +
                ", notes='" + notes + '\'' +
                ", rpe=" + rpe +
                ", reps=" + reps +
                ", weight=" + weight +
                ", id=" + id +
                '}';
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public boolean isPersonalRecord() {
        return personalRecord;
    }

    public void setPersonalRecord(boolean personalRecord) {
        this.personalRecord = personalRecord;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public Double getRpe() {
        return rpe;
    }

    public void setRpe(Double rpe) {
        this.rpe = rpe;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getTut() {
        return tut;
    }

    public void setTut(Integer tut) {
        this.tut = tut;
    }

    public SetStatus getStatus() {
        return status;
    }

    public void setStatus(SetStatus status) {
        this.status = status;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }
}
