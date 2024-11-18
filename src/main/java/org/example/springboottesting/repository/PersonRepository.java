package org.example.springboottesting.repository;

import org.example.springboottesting.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
