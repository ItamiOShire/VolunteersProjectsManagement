package org.example.springboottesting.service;

import org.example.springboottesting.model.Tag;
import org.example.springboottesting.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> getAllTags() {

        return tagRepository.findAll();

    }
}
