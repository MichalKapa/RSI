package org.example.a;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import org.example.Person;
import org.example.PersonExistsEx;
import org.example.PersonNotFoundEx;
import org.example.jaxws.server_topdown.*;

import java.util.List;

@WebService(serviceName = "org.example.PersonService",
        endpointInterface = "org.example.PersonService")
public class PersonServiceImpl2 implements PersonService2 {
    private PersonRepository2 dataRepository = new PersonRepositoryImpl2();
    @WebMethod
    public Person getPerson(int id) throws PersonNotFoundEx_Exception {
        System.out.println("...called getPerson id="+id);
        return dataRepository.getPerson(id);
    }
    @WebMethod
    public List<Person> getAllPersons() {
        System.out.println("...called getAllPersons");
        return dataRepository.getAllPersons();
    }
    @WebMethod
    public Person addPerson(int id, String name, int age) throws
            PersonExistsEx_Exception {
        System.out.println("...called addPerson id=" + id + " name=" + name
                + "age=" + age);
        return dataRepository.addPerson(id, name, age);
    }
    @WebMethod
    public boolean deletePerson(int id) throws PersonNotFoundEx_Exception {
        System.out.println("...called deletePerson id=" + id);
        return dataRepository.deletePerson(id);
    }
    @WebMethod
    public int countPersons() {
        return dataRepository.countPersons();
    }

    @WebMethod
    public Person updatePerson(int id, String name, int age) throws PersonNotFoundEx_Exception {
        return dataRepository.updatePerson(id, name, age);
    }

}