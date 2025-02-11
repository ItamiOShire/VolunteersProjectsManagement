package org.example.springboottesting.repository;

import org.example.springboottesting.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByOrganisationId(int organisationId);

    @Query("SELECT p FROM Project p WHERE p.id > 27")
    List<Project> findAllI();

    @Query("SELECT p FROM Project p JOIN p.volunteers v ON v.id = :volunteerId")
    List<Project> findByVolunteerId(@Param("volunteerId")Long volunteerId);

    @Query (value = """
            WITH All_responses AS (
            SELECT * FROM Project WHERE FREETEXT(descr, :word)
            UNION ALL
            SELECT * FROM Project WHERE title LIKE %:word%
            UNION ALL
            SELECT p.* FROM Project p JOIN Project_Tag pt ON pt.projectId = p.ID
            JOIN Tag t ON t.ID = pt.tagId WHERE t.descr LIKE %:word%
            )
            SELECT DISTINCT * FROM All_responses
            """, nativeQuery = true)
    Set<Project> findProjectsByWord(@Param("word") String word);

}
