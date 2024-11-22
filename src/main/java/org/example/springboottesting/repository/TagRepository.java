package org.example.springboottesting.repository;

import org.example.springboottesting.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query("SELECT t FROM Tag t WHERE t.id IN :ids")
    List<Tag> findAllByIdSorted(@Param("ids") List<Long> ids);
}
