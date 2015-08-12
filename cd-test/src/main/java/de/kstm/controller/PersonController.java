package de.kstm.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import de.kstm.model.Person;
import de.kstm.repository.PersonRepository;

@Controller
@RequestMapping("/persons")
public class PersonController {
    private PersonRepository personRepository;

    @Autowired
    public void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Person> getPersons() {
        return (List<Person>) personRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody Person getPerson(@RequestParam(value = "id") Long id) {
        return personRepository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Person> add(@RequestBody Person person) {
        Person newPerson = personRepository.save(person);

        HttpHeaders httpHeaders = null;
        try {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("{id}").buildAndExpand(newPerson.getId()).toUri();
            httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(location);
        } catch (IllegalStateException e) {

        }

        return new ResponseEntity<Person>(newPerson, httpHeaders,
                HttpStatus.CREATED);
    }
    
    @RequestMapping(value="/error")
    public ResponseEntity<String> error() {
        return new ResponseEntity<String>("Error", null, HttpStatus.NOT_FOUND);
    }
}
