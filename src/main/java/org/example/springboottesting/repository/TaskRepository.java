package org.example.springboottesting.repository;

import org.example.springboottesting.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> getTasksByProjectId(int projectId);

    @Query("SELECT t FROM Task t JOIN t.volunteers v JOIN v.projects p WHERE v.id = :volunteerId AND p.id = :projectId")
    List<Task>getTasksByProjectIdAndVolunteerId(@Param("projectId")int projectId,@Param("volunteerId") int volunteerId);
}
