package org.example.springboottesting.repository;

import org.example.springboottesting.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> getTasksByProjectId(int projectId);

    @Query(value = """
            SELECT t.* FROM Volunteer v
                    JOIN Volunteer_Project vp ON
                    vp.volunteerID = v.ID
                    JOIN Project p
                    ON vp.projectID = p.ID
                    JOIN Task t
                    ON t.projectID = p.ID
                    JOIN Volunteer_Task vt
                    ON vt.taskID = t.ID AND vt.volunteerID = v.ID
                    WHERE vp.volunteerID = :volunteerId AND vp.projectID = :projectId
""", nativeQuery = true)
    List<Task>getTasksByProjectIdAndVolunteerId(@Param("projectId")int projectId,@Param("volunteerId") int volunteerId);

    @Query(value = """

SELECT t.* FROM Volunteer v
                    JOIN Volunteer_Project vp ON
                    vp.volunteerID = v.ID
                    JOIN Project p
                    ON vp.projectID = p.ID
                    JOIN Task t
                    ON t.projectID = p.ID
                    WHERE vp.volunteerID = :volunteerId AND vp.projectID = :projectId
					AND t.ID NOT IN (
						SELECT vtt.taskID FROM Volunteer_Task vtt
						)

""", nativeQuery = true)
    List<Task> getTasksByProjectIdAndVolunteerIdNotAttendedByVolunteer(@Param("projectId")int projectId, @Param("volunteerId") int volunteerId);

    @Query("SELECT v.suggestedTasks FROM Volunteer v JOIN v.projects p WHERE v.id = :volunteerId AND p.id = :projectId")
    List<Task> getVolunteerSuggestedTasksByVolunteerIdAndProjectId(@Param("volunteerId") int volunteerId, @Param("projectId") int projectId);

}

