package service;

import exception.InvalidInputException;


public class FloorService {

    // TODO: temporary. Delete when model/Floor.java is ready.
    static class Floor {
        double area;
        double services;

        Floor(double area, double services) {
            this.area = area;
            this.services = services;
        }
    }

    public double freeSpace(Floor floor) {
        if (floor.area <= 0) {
            throw new InvalidInputException("error.area.zero");
        }
        if (floor.services < 0) {
            throw new InvalidInputException("error.services.negative");
        }
        if (floor.services > floor.area) {
            throw new InvalidInputException("error.services.big");
        }
        return floor.area - floor.services;
    }
}