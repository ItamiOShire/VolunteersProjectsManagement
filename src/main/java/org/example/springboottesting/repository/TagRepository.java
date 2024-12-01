package org.example.springboottesting.repository;

import org.example.springboottesting.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query("SELECT t FROM Tag t WHERE t.id IN :ids")
    Set<Tag> findAllByIdSorted(@Param("ids") List<Long> ids);
}
