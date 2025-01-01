package org.example.springboottesting.repository;

import org.example.springboottesting.model.Task;
import org.example.springboottesting.model.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
    Optional<Volunteer> findVolunteerByEmail(String email);

    @Query("SELECT v FROM Volunteer v JOIN v.projects p WHERE p.id = :projectId ")
    Set<Volunteer> findVolunteerByProjectId(@Param("projectId")int projectId);

    @Query("SELECT DISTINCT v FROM Volunteer v  JOIN v.projects p JOIN v.suggestedTasks t WHERE p.id = :projectId AND t.id = :taskId")
    Set<Volunteer> findVolunteerWithSuggestedTasksByProjectId(@Param("projectId") int projectId, @Param("taskId") int taskId);

    @Query("SELECT DISTINCT v FROM Volunteer v  JOIN v.projects p LEFT JOIN v.tasks t WHERE p.id = :projectId AND (t IS NULL OR NOT EXISTS (" +
            "SELECT 1 FROM Task tn WHERE tn.id = :taskId AND tn MEMBER OF v.tasks))")
    Set<Volunteer> findVolunteerWithTasksByProjectId(@Param("projectId") int projectId, @Param("taskId") int taskId);

    @Query("SELECT DISTINCT v FROM Volunteer v JOIN v.projects p LEFT JOIN v.tasks t WHERE p.id = :projectId AND t.id = :taskId")
    Set<Volunteer> findVolunteerWithTasksToDeleteByProjectId(@Param("projectId") int projectId, @Param("taskId") int taskId);

}
