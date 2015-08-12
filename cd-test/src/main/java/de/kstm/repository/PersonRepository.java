package de.kstm.repository;

import org.springframework.data.repository.CrudRepository;

import de.kstm.model.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {
	
}
