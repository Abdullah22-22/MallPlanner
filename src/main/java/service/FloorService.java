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

    public void checkFloorArea(double floorArea, double mallTotalArea) {
        if (floorArea <= 0) {
            throw new InvalidInputException("error.area.zero");
        }
        if (floorArea > mallTotalArea) {
            throw new InvalidInputException("error.floor.bigger.than.mall");
        }
    }

}