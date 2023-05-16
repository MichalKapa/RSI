package org.example.a;

import jakarta.xml.ws.Endpoint;

import java.io.IOException;

import static java.lang.System.exit;

public class ServiceHost2 {
    public static void main(String[] args) {
        System.out.println("Web Service org.example.PersonService is running ...");
        PersonServiceImpl2 psi = new PersonServiceImpl2();
        Endpoint.publish("http://localhost:8081/personservice", psi);
        System.out.println("Press ENTER to STOP org.example.PersonService ...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        exit(0);
    }
}