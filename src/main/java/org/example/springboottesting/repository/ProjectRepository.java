package org.example.springboottesting.repository;

import org.example.springboottesting.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByOrganisationId(int organisationId);

    @Query("SELECT p FROM Project p JOIN p.volunteers v ON v.id = :volunteerId")
    List<Project> findByVolunteerId(@Param("volunteerId")Long volunteerId);

}
