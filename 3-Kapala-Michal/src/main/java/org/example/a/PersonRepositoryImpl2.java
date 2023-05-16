package org.example.a;

import org.example.Person;
import org.example.PersonExistsEx;
import org.example.PersonNotFoundEx;
import org.example.jaxws.server_topdown.PersonExistsEx_Exception;
import org.example.jaxws.server_topdown.PersonNotFoundEx_Exception;

import java.util.ArrayList;
import java.util.List;

public class PersonRepositoryImpl2 implements PersonRepository2 {
    private List<Person> personList;
    public PersonRepositoryImpl2() {
        personList = new ArrayList<>();
        personList.add(new Person(1, "Mariusz" , 9));
        personList.add(new Person(2, "Andrzej", 10));
        personList.add(new Person(3, "Michał", 11));
        personList.add(new Person(4, "Robert", 12));
        personList.add(new Person(5, "Tomasz", 13));
    }
    public List<Person> getAllPersons() {
        return personList;
    }
    public Person getPerson(int id) throws PersonNotFoundEx_Exception {
        for (Person thePerson : personList) {
            if (thePerson.getId() == id) {
                return thePerson;
            }
        }
        throw new PersonNotFoundEx_Exception();
    }
    public Person addPerson(int id, String name, int age) throws
            PersonExistsEx_Exception {
        for (Person thePerson : personList) {
            if (thePerson.getId() == id) {
                throw new PersonExistsEx_Exception();
            }
        }
        Person person = new Person(id, name, age);
        personList.add(person);
        return person;
    }
    public boolean deletePerson(int id) throws PersonNotFoundEx_Exception {
        for (int i = 0; i < personList.size(); i++) {
            if (personList.get(i).getId() == id) {
                personList.remove(i);
                return true;
            }
        }
        throw new PersonNotFoundEx_Exception();
    }
    public Person updatePerson(int id, String name, int age) throws PersonNotFoundEx_Exception {
        for (Person thePerson : personList) {
            if (thePerson.getId() == id) {
                thePerson.setFirstName(name);
                thePerson.setAge((age));
                return thePerson;
            }
        }
        throw new PersonNotFoundEx_Exception();
    }
    public int countPersons() {
        return personList.size();
    }
}