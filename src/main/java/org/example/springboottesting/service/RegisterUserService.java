package org.example.springboottesting.service;

import jakarta.transaction.Transactional;
import org.example.springboottesting.model.Organisation;
import org.example.springboottesting.model.Users;
import org.example.springboottesting.model.Volunteer;
import org.example.springboottesting.repository.OrganisationRepository;
import org.example.springboottesting.repository.UsersRepository;
import org.example.springboottesting.repository.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final VolunteerRepository volunteerRepository;
    private final OrganisationRepository organisationRepository;

    @Autowired
    public RegisterUserService(UsersRepository usersRepository, VolunteerRepository volunteerRepository, PasswordEncoder passwordEncoder, OrganisationRepository organisationRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.volunteerRepository = volunteerRepository;
        this.organisationRepository = organisationRepository;
    }

    @Transactional
    public Users registerVolunteer(Volunteer volunteer, Users user) {

        if (usersRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email address already in use");
        }

        volunteer.setPassword(passwordEncoder.encode(volunteer.getPassword()));
        Volunteer volunteerTemp = this.volunteerRepository.save(volunteer);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("VOLUNTEER");
        user.setId(volunteerTemp.getId());

        return usersRepository.save(user);
    }

    @Transactional
    public Users registerOrganisation(Organisation organisation, Users user) {

        if (usersRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email address already in use");
        }

        organisation.setPassword(passwordEncoder.encode(organisation.getPassword()));
        Organisation organisationTemp = this.organisationRepository.save(organisation);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ORGANISATION");
        user.setId(organisationTemp.getId());

        return usersRepository.save(user);
    }
}
