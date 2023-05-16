package client;

import org.example.PersonNotFoundEx;
import org.example.PersonService;
import org.example.jaxws.server_topdown.Person;
import org.example.jaxws.server_topdown.PersonExistsEx_Exception;
import org.example.jaxws.server_topdown.PersonNotFoundEx_Exception;
import org.example.jaxws.server_topdown.PersonService_Service;

import java.net.MalformedURLException;
import java.net.URL;

public class ESClient {
    public static void main(String[] args) throws MalformedURLException,
            PersonNotFoundEx_Exception, PersonNotFoundEx, PersonExistsEx_Exception {
        int num =-1;
        URL addr = new URL("http://localhost:8081/personservice?wsdl");
        PersonService_Service pService = new PersonService_Service();
        org.example.jaxws.server_topdown.PersonService pServiceProxy = pService.getPersonServiceImplPort();
        num = pServiceProxy.countPersons();
        System.out.println("Num of Persons = "+num);
        org.example.jaxws.server_topdown.Person person = pServiceProxy.getPerson(2);
        System.out.println("Person "+person.getFirstName() +
                ",id = "+person.getId());

        System.out.println("--------------------getAll");
        for (Person p : pServiceProxy.getAllPersons()) {
           System.out.println(p.getId() + " " + p.getFirstName() + " " + p.getAge());
       }
        System.out.println("--------------------addPerson");
        Person added = pServiceProxy.addPerson(7, "Maciej", 102);
        System.out.println(added.getId() + " " + added.getFirstName() + " " + added.getAge());

        System.out.println("--------------------deletePerson 2");
        System.out.println(pServiceProxy.deletePerson(2));
        for (Person p : pServiceProxy.getAllPersons()) {
            System.out.println(p.getId() + " " + p.getFirstName() + " " + p.getAge());
        }

        System.out.println("--------------------update 7 Kot 103");
        Person updated = pServiceProxy.updatePerson(7, "Kot", 103);
        System.out.println(updated.getId() + " " + updated.getFirstName() + " " + updated.getAge());
    }
}