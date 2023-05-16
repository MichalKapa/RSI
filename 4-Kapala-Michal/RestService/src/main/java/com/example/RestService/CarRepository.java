package com.example.RestService;

import java.util.List;

public interface CarRepository {
    List<Car> getAllCars();
    Car getCar(int id) throws CarNotFoundEx;
    Car updateCar(Car car, int id) throws
            CarNotFoundEx;
    boolean deleteCar(int id) throws CarNotFoundEx;
    Car addCar(Car car) throws CarExistsEx;
    int countCars();
    Car rentCar(int id);
    Car returnCar(int id);
}
