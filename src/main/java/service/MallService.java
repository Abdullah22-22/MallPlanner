package service;

import exception.InvalidInputException;

public class MallService {

    // TODO: temporary. Delete when model/Mall.java is ready.
    static class Mall{
        String name;
        double totalArea;
        int floors;

        Mall(String name, double totalArea, int floors) {
            this.name = name;
            this.totalArea = totalArea;
            this.floors = floors;
        }
    }

    public double areaPerFloor(Mall mall) {
        if (mall.name == null || mall.name.trim().isEmpty()) {
            throw new InvalidInputException("error.name.empty");
        }
        if (mall.totalArea <= 0) {
            throw new InvalidInputException("error.area.zero");
        }
        if (mall.floors <= 0) {
            throw new InvalidInputException("error.floors.zero");
        }
        return mall.totalArea / mall.floors;
    }

}
