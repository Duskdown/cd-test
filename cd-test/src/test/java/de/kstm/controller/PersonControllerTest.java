package de.kstm.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import de.kstm.model.Person;
import de.kstm.repository.PersonRepository;

public class PersonControllerTest {
    @Mock
    private PersonRepository repositoryMock;

    private PersonController personController;

    @Before
    public void setUp() {
        repositoryMock = Mockito.mock(PersonRepository.class);
        personController = new PersonController();
        personController.setPersonRepository(repositoryMock);
    }

    @Test
    public void get_all_users_test() {
        // arrange
        List<Person> persons = getMockPersons();
        Mockito.when(repositoryMock.findAll()).thenReturn(persons);

        // act
        List<Person> result = personController.getPersons();

        // assert
        Assert.assertEquals(persons.size(), result.size());
        Assert.assertEquals(persons.get(0), result.get(0));
        Assert.assertEquals(persons.get(1), result.get(1));
        Mockito.verify(repositoryMock, Mockito.times(1)).findAll();
    }

    @Test
    public void get_user_by_id_test() {
        // arrange
        List<Person> persons = getMockPersons();
        Mockito.when(repositoryMock.findOne(Mockito.anyLong())).thenReturn(persons.get(0));

        // act
        Person result = personController.getPerson(1l);

        // assert
        Assert.assertEquals(persons.get(0), result);
        Mockito.verify(repositoryMock, Mockito.times(1)).findOne(1l);
    }
    
    @Test
    public void post_new_user_test() {
        // arrange
        List<Person> persons = getMockPersons();
        Person newPerson = persons.get(0);
        Mockito.when(repositoryMock.save(Mockito.any(Person.class))).thenReturn(newPerson);
        
        // act
        ResponseEntity<Person> result = personController.add(newPerson);
        
        // assert
        Assert.assertEquals(HttpStatus.CREATED, result.getStatusCode());
        Assert.assertEquals(newPerson, result.getBody());
        Assert.assertTrue(result.getHeaders().getLocation() == null);
        Mockito.verify(repositoryMock, Mockito.times(1)).save(newPerson);
    }
    
    private List<Person> getMockPersons() {
        List<Person> persons = new ArrayList<Person>();

        Person person1 = new Person();
        person1.setId(1);
        person1.setFirstname("firstname1");
        person1.setName("name1");

        Person person2 = new Person();
        person2.setId(2);
        person2.setFirstname("firstname2");
        person2.setName("name2");

        persons.add(person1);
        persons.add(person2);

        return persons;
    }
}
