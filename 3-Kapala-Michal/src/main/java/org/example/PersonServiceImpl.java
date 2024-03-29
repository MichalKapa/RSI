package org.example;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import org.example.jaxws.server_topdown.PersonNotFoundEx_Exception;

import java.util.List;

@WebService(serviceName = "org.example.PersonService",
        endpointInterface = "org.example.PersonService")
public class PersonServiceImpl implements PersonService {
    private PersonRepository dataRepository = new PersonRepositoryImpl();
    @WebMethod
    public Person getPerson(int id) throws PersonNotFoundEx {
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
            PersonExistsEx {
        System.out.println("...called addPerson id=" + id + " name=" + name
                + "age=" + age);
        return dataRepository.addPerson(id, name, age);
    }
    @WebMethod
    public boolean deletePerson(int id) throws PersonNotFoundEx {
        System.out.println("...called deletePerson id=" + id);
        return dataRepository.deletePerson(id);
    }
    @WebMethod
    public int countPersons() {
        return dataRepository.countPersons();
    }

    @WebMethod
    public Person updatePerson(int id, String name, int age) throws  PersonNotFoundEx {
        return dataRepository.updatePerson(id, name, age);
    }

}