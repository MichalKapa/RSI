package org.example.a;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import org.example.Person;
import org.example.PersonExistsEx;
import org.example.PersonNotFoundEx;
import org.example.jaxws.server_topdown.PersonExistsEx_Exception;
import org.example.jaxws.server_topdown.PersonNotFoundEx_Exception;

import java.util.List;

@WebService
public interface PersonService2 {
    @WebMethod
    Person getPerson(int id) throws PersonNotFoundEx_Exception;
    @WebMethod
    Person addPerson(int id, String name, int age) throws PersonExistsEx_Exception;
    @WebMethod
    boolean deletePerson(int id) throws PersonNotFoundEx_Exception;
    @WebMethod
    List<Person> getAllPersons();
    @WebMethod
    int countPersons();
    @WebMethod
    Person updatePerson(int id, String name, int age) throws PersonNotFoundEx_Exception;
} 