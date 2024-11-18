package org.example.springboottesting.repository;

import org.example.springboottesting.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface OrganisationRepository extends JpaRepository<Organisation, Long> {
}
