package org.example;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

import java.util.List;

@WebService
public interface PersonService {
    @WebMethod
    Person getPerson(int id) throws PersonNotFoundEx;
    @WebMethod
    Person addPerson(int id, String name, int age) throws PersonExistsEx;
    @WebMethod
    boolean deletePerson(int id) throws PersonNotFoundEx;
    @WebMethod
    List<Person> getAllPersons();
    @WebMethod
    int countPersons();
    Person updatePerson(int id, String name, int age) throws PersonNotFoundEx;
} 