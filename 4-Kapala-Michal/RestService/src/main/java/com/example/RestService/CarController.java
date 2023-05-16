package com.example.RestService;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
public class CarController {
    private CarRepository dataRepo = new CarRepositoryImpl();
    @GetMapping("/cars/{id}")
    public EntityModel<?> getCar(@PathVariable int id) {
        try {
            System.out.println("...called GET"); //dla śledzenia działania
            Car car = dataRepo.getCar(id);
            if (car.getStatus().equals(Car.CarStatus.RENTED)) {
                return EntityModel.of(car,
                        linkTo(methodOn(CarController.class)
                                .getCar(car.getId())).withSelfRel(),
                        linkTo(methodOn(CarController.class)
                                .returnCar(car.getId())).withRel("returnCar"),
                        linkTo(methodOn(CarController.class)
                                .getAllCars()).withRel("list all") );
            }
            if (car.getStatus().equals(Car.CarStatus.LISTED)) {
                return EntityModel.of(car,
                        linkTo(methodOn(CarController.class)
                                .getCar(car.getId())).withSelfRel(),
                        linkTo(methodOn(CarController.class)
                                .deleteCar(car.getId())).withRel("rentCar"),
                        linkTo(methodOn(CarController.class)
                                .deleteCar(car.getId())).withRel("deleteCar"),
                        linkTo(methodOn(CarController.class)
                                .getAllCars()).withRel("list all"));
            }
            else {
                return EntityModel.of(car,
                        linkTo(methodOn(CarController.class)
                                .getCar(car.getId())).withSelfRel(),
                        linkTo(methodOn(CarController.class)
                                .deleteCar(car.getId())).withRel("deleteCar"),
                        linkTo(methodOn(CarController.class)
                                .getAllCars()).withRel("list all"));
            }
        } catch (CarNotFoundEx e) {
            System.out.println("...GET Exception");
            throw e;
        }
    }


    @GetMapping("/cars")
    public CollectionModel<EntityModel<Car>> getAllCars() {
        try {
            System.out.println("...called GET ALL"); //dla śledzenia działania
            List<EntityModel<Car>> cars = dataRepo.getAllCars()
                    .stream()
                    .map(car -> {
                        List<Link> links = new ArrayList<>();
                        links.add(linkTo(methodOn(CarController.class).getCar(car.getId())).withSelfRel());

                        if (car.getStatus() == Car.CarStatus.RENTED) {
                            links.add(linkTo(methodOn(CarController.class).returnCar(car.getId())).withRel("returnCar"));
                        } else if (car.getStatus() == Car.CarStatus.LISTED) {
                            links.add(linkTo(methodOn(CarController.class).rentCar(car.getId())).withRel("rentCar"));
                            links.add(linkTo(methodOn(CarController.class).deleteCar(car.getId())).withRel("deleteCar"));
                        }
                        else {
                            links.add(linkTo(methodOn(CarController.class).deleteCar(car.getId())).withRel("deleteCar"));
                        }
                        links.add(linkTo(methodOn(CarController.class).getAllCars()).withRel("list all"));

                        return EntityModel.of(car, links);
                    })
                    .collect(Collectors.toList());

            return CollectionModel.of(cars, linkTo(methodOn(CarController.class).getAllCars()).withSelfRel());
        } catch (CarNotFoundEx e) {
            System.out.println("...GET ALL Exception");
            throw e;
        }
    }

    @DeleteMapping("/cars/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable int id) throws CarNotFoundEx {
        try {
            System.out.println("...called DELETE"); //dla śledzenia działania
            dataRepo.deleteCar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (CarNotFoundEx e) {
            System.out.println("...DELETE Exception");
            throw e;
        }
    }

    @PostMapping("/cars")
    public EntityModel<Car> addCar(@RequestBody Car car) throws CarExistsEx {
        try {
            System.out.println("...called ADD"); //dla śledzenia działania
            dataRepo.addCar(car);
            return EntityModel.of(car,
                    linkTo(methodOn(CarController.class)
                            .getCar(car.getId())).withSelfRel(),
                    linkTo(methodOn(CarController.class)
                            .deleteCar(car.getId())).withRel("deleteCar"),
                    linkTo(methodOn(CarController.class)
                            .getAllCars()).withRel("list all"));

        } catch (CarExistsEx e) {
            System.out.println("...ADD Exception");
            throw e;
        }
    }

    @PutMapping("/cars/{id}")
    public EntityModel<?> updateCar(@PathVariable int id, @RequestBody Car car) throws CarNotFoundEx {
        try {
            System.out.println("...called PUT"); //dla śledzenia działania
            Car updatedCar = dataRepo.updateCar(car, id);
            return EntityModel.of(updatedCar,
                    linkTo(methodOn(CarController.class)
                            .getCar(id)).withSelfRel(),
                    linkTo(methodOn(CarController.class)
                            .deleteCar(id)).withRel("deleteCar"),
                    linkTo(methodOn(CarController.class)
                            .getAllCars()).withRel("list all") );

        } catch (CarNotFoundEx e) {
            System.out.println("...PUT Exception");
            throw e;
        }
    }

    @PatchMapping("/cars/{id}/rent")
    public EntityModel<?> rentCar(@PathVariable int id) {
        try {
            System.out.println("...called rentCar");
            dataRepo.rentCar(id);
            return getCar(id);
        } catch (ConflictEx e) {
            System.out.println("...rentCar Exception");
            throw e;
        }
    }

    @PatchMapping("/cars/{id}/return")
    public EntityModel<?> returnCar(@PathVariable int id) {
        try {
            System.out.println("...called returnCar");
            dataRepo.returnCar(id);
            return getCar(id);
        } catch (ConflictEx e) {
            System.out.println("...returnCar Exception");
            throw e;
        }
    }

    }


