package com.dirkficial.workout.domain.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "workouts")
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id; /* Used Long for readability and easier debugging. */

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @CreationTimestamp
    @Column(name = "createdAt", updatable = false, nullable = false)
    private Instant createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private WorkoutStatus status;

    public Workout() {
    }

    public Workout(String name, LocalDate date, WorkoutStatus status) {
//        this.id = id; Setting an ID is not necessary since the Database generates the ID.
        this.name = name;
        this.date = date;
//        this.createdAt = createdAt; Same for Instant variable.
        this.status = status;
    }

    /* Helper method to add set to list of sets and link the child to parent */
    public void addSet(com.dirkficial.workout.domain.entity.Set set) {
        this.sets.add(set);
        set.setWorkout(this);
    }

    public Map<String, Double> getVolumeByMuscle() {
        if (sets == null || sets.isEmpty()) {
            return Map.of();
        }

        return sets.stream()
                .filter(set -> set.getMuscleGroup() != null)
                .collect(Collectors.groupingBy(
                        Set::getMuscleGroup,
                        Collectors.summingDouble(Set::getVolume)
                ));
    }

    public Double getTotalVolume() {
        if (sets == null || sets.isEmpty()) {
            return 0.0;
        }
        return sets.stream().
                    mapToDouble(Set::getVolume).
                    sum();
    }

    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<com.dirkficial.workout.domain.entity.Set> sets = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Workout workout = (Workout) o;
        return Objects.equals(id, workout.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Workout{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", createdAt=" + createdAt +
                ", status=" + status +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public WorkoutStatus getStatus() {
        return status;
    }

    public void setStatus(WorkoutStatus status) {
        this.status = status;
    }

    public List<Set> getSets() {
        return sets;
    }

    public void setSets(List<com.dirkficial.workout.domain.entity.Set> sets) {
        this.sets = sets;
    }

}
