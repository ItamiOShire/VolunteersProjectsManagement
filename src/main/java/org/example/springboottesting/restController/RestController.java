package org.example.springboottesting.restController;

import org.example.springboottesting.model.Person;
import org.example.springboottesting.model.Users;
import org.example.springboottesting.repository.PersonRepository;
import org.example.springboottesting.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;


@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    private PersonRepository personRepository;
    
    @Autowired
    private UsersRepository usersRepository;

    @PostMapping("/sendData")
    public ResponseEntity<String> receiveData(@RequestBody Person data) {
        //Person person = new Person(data.getFirstName(), data.getLastName());
        System.out.println(data.getFirstName() + " " + data.getLastName());
        return ResponseEntity.ok("Dane zostały wyslane");
    }

    @GetMapping("/getData")
    public ResponseEntity<Optional<Users>> sendData() {
        List<Person> person = personRepository.findAll();
        Optional<Users> user = usersRepository.findByEmail("test@test.com");

        return ResponseEntity.ok(user);
    }

    @GetMapping("/projekt/{id}")
    public ModelAndView getProjectDetails(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("volunteer_project_desc");
        modelAndView.addObject("projectId", id);
        return modelAndView;
    }


}
