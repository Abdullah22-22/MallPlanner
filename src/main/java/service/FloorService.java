package service;

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
            throw new IllegalArgumentException("error.area.zero");
        }
        if (floor.services < 0) {
            throw new IllegalArgumentException("error.services.negative");
        }
        if (floor.services > floor.area) {
            throw new IllegalArgumentException("error.services.big");
        }
        return floor.area - floor.services;
    }
}