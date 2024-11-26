package org.example.springboottesting.repository;

import org.example.springboottesting.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface OrganisationRepository extends JpaRepository<Organisation, Long> {

    @Query("SELECT o.orgName FROM Organisation o WHERE o.id = :id")
    public String getNameById(@Param("id") int id);
}
