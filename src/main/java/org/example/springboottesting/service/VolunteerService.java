package org.example.springboottesting.service;

import org.example.springboottesting.DTO.VolunteerDTO;
import org.example.springboottesting.model.Volunteer;
import org.example.springboottesting.repository.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;

    @Autowired
    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }


    public VolunteerDTO getVolunteerDetails(Long id) {

        VolunteerDTO volunteerDTO = new VolunteerDTO();

        Volunteer volunteer = volunteerRepository.findById(id).orElse(null);

        if (volunteer != null) {

            volunteerDTO.setAge(
                    LocalDate.now().getYear() - volunteer.getBirthDate().getYear()
            );
            volunteerDTO.setEmail(volunteer.getEmail());
            volunteerDTO.setPhone(volunteer.getPhoneNumber());
            volunteerDTO.setFnameAndLname(
                    volunteer.getFirstName() +
                            " " +
                            volunteer.getLastName()
            );

            return volunteerDTO;

        }

        return null;

    }
}
