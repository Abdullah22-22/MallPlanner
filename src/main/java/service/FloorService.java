package service;

import exception.InvalidInputException;
import model.Floor;


public class FloorService {


    public double freeSpace(Floor floor, double totalServices) {
        if (floor.getArea() <= 0) {
            throw new InvalidInputException("error.area.zero");
        }
        if (totalServices < 0) {
            throw new InvalidInputException("error.services.negative");
        }
        if (totalServices > floor.getArea()) {
            throw new InvalidInputException("error.services.big");
        }
        return floor.getArea() - totalServices;
    }
}