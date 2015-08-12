package de.kstm.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String firstname;

    public Person() {
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        if (obj instanceof Person) {
            Person person = (Person) obj;
            result = person.getFirstname().equals(this.getFirstname())
                    && person.getId() == this.getId()
                    && person.getName().equals(this.getName());
        }
        return result;
    }
}
