package org.example.a;

import org.example.Person;
import org.example.PersonExistsEx;
import org.example.PersonNotFoundEx;
import org.example.jaxws.server_topdown.PersonExistsEx_Exception;
import org.example.jaxws.server_topdown.PersonNotFoundEx_Exception;

import java.util.List;

public interface PersonRepository2 {
    List<Person> getAllPersons();
    Person getPerson(int id) throws PersonNotFoundEx_Exception;
    Person updatePerson(int id, String name, int age) throws
            PersonNotFoundEx_Exception;
    boolean deletePerson(int id) throws PersonNotFoundEx_Exception;
    Person addPerson(int id, String name, int age) throws PersonExistsEx_Exception;
    int countPersons();
}