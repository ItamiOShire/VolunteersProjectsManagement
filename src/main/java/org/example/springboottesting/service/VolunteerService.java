package org.example.springboottesting.service;

import org.example.springboottesting.DTO.VolunteerDTO;
import org.example.springboottesting.model.Task;
import org.example.springboottesting.model.Volunteer;
import org.example.springboottesting.repository.TaskRepository;
import org.example.springboottesting.repository.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public VolunteerService(VolunteerRepository volunteerRepository, TaskRepository taskRepository) {
        this.volunteerRepository = volunteerRepository;
        this.taskRepository = taskRepository;
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

    public List<VolunteerDTO> getVolunteersByJoinedProject(int projectId) {

        Set<Volunteer> volunteers = volunteerRepository.findVolunteerByProjectId(projectId);

        List<VolunteerDTO> volunteersDTO = new ArrayList<>();

        for (Volunteer volunteer : volunteers) {
            VolunteerDTO volunteerDTO = new VolunteerDTO();

            volunteerDTO.setFnameAndLname(volunteer.getFirstName() + " " + volunteer.getLastName());
            volunteerDTO.setEmail(volunteer.getEmail());

            volunteersDTO.add(volunteerDTO);
        }

        return volunteersDTO;

    }

    public List<VolunteerDTO> getVolunteersWithTasksByProject(int projectId, int taskId) {

        Set<Volunteer> volunteers = volunteerRepository.findVolunteerWithTasksByProjectId(projectId, taskId);

        List<VolunteerDTO> volunteersDTO = new ArrayList<>();

        for (Volunteer volunteer : volunteers) {
            VolunteerDTO volunteerDTO = new VolunteerDTO();

            volunteerDTO.setFnameAndLname(volunteer.getFirstName() + " " + volunteer.getLastName());
            volunteerDTO.setEmail(volunteer.getEmail());
            volunteerDTO.setId(volunteer.getId());

            volunteersDTO.add(volunteerDTO);
        }

        return volunteersDTO;

    }

    public boolean addVolunteerToTask(Long volunteerId, Long taskId) {

        Volunteer volunteer = volunteerRepository.findById(volunteerId).orElse(null);
        Task task = taskRepository.findById(taskId).orElse(null);

        if (volunteer != null && task != null) {

            volunteer.getTasks().add(task);
            volunteerRepository.save(volunteer);
            return true;
        }

        return false;

    }
}
