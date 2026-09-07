package service;

import exception.InvalidInputException;

public class FloorService {

    public void checkFloorArea(double floorArea, double mallTotalArea) {
        if (floorArea <= 0) {
            throw new InvalidInputException("error.area.zero");
        }
        if (floorArea > mallTotalArea) {
            throw new InvalidInputException("error.floor.bigger.than.mall");
        }
    }

}