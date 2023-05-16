package com.example.RestService;

import java.util.ArrayList;
import java.util.List;

public class CarRepositoryImpl implements CarRepository {
    private List<Car> carList;
    public CarRepositoryImpl() {
        carList = new ArrayList<>();
        carList.add(new Car(1, "VW" , 1999, "red", true, Car.CarStatus.LISTED));
        carList.add(new Car(2, "AUDI" , 2001, "blue", false, Car.CarStatus.LISTED));
        carList.add(new Car(3, "BMW" , 2003, "green", true, Car.CarStatus.NOT_LISTED));
        carList.add(new Car(4, "CITROEN" , 2005, "yellow", true, Car.CarStatus.LISTED));
        carList.add(new Car(5, "SKODA" , 2007, "black", false, Car.CarStatus.LISTED));
    }
    public List<Car> getAllCars() {
        return carList;
    }
    public Car getCar(int id) throws CarNotFoundEx {
        for (Car theCar : carList) {
            if (theCar.getId() == id) {
                return theCar;
            }
        }
        throw new CarNotFoundEx(id);
    }
    public Car addCar(Car car) throws
            CarExistsEx {
        for (Car theCar : carList) {
            if (theCar.getId() == car.getId()) {
                System.out.println(car.getId());
                throw new CarExistsEx(car.getId());
            }
        }
        carList.add(car);
        return car;
    }
    public boolean deleteCar(int id) throws CarNotFoundEx {
        for (int i = 0; i < carList.size(); i++) {
            if (carList.get(i).getId() == id) {
                carList.remove(i);
                return true;
            }
        }
        throw new CarNotFoundEx(id);
    }
    public Car updateCar(Car car, int id) throws CarNotFoundEx {
        for (Car theCar : carList) {
            if (theCar.getId() == id) {
                theCar.setBrand(car.getBrand());
                theCar.setYear((car.getYear()));
                theCar.setColor((car.getColor()));
                theCar.setIsNew((car.getIsNew()));
                theCar.setStatus(car.getStatus());
                return theCar;
            }
        }
        throw new CarNotFoundEx(id);
    }
    public int countCars() {
        return carList.size();
    }

    public Car rentCar(int id) {
        for (Car theCar : carList) {
            if (theCar.getId() == id) {
                if (theCar.getStatus().equals(Car.CarStatus.LISTED)) {
                    theCar.setStatus(Car.CarStatus.RENTED);
                    return theCar;
                }
                else {
                    throw new ConflictEx(theCar.getStatus().name());
                }
            }
        }
        throw new CarNotFoundEx(id);
    }
    public Car returnCar(int id) {
        for (Car theCar : carList) {
            if (theCar.getId() == id) {
                if (theCar.getStatus().equals(Car.CarStatus.RENTED)) {
                    theCar.setStatus(Car.CarStatus.LISTED);
                    return theCar;
                }
                else {
                    throw new ConflictEx(theCar.getStatus().name());
                }
            }
        }
        throw new CarNotFoundEx(id);
    }
}