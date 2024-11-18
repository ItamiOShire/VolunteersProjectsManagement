package org.example.springboottesting.repository;

import org.example.springboottesting.model.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
    public Optional<Volunteer> findVolunteerByEmail(String email);
}
