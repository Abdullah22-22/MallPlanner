package service;

import exception.InvalidInputException;
import model.Mall;


public class MallService {


    public double areaPerFloor(Mall mall, int floors) {
        if (mall.getName() == null || mall.getName().trim().isEmpty()) {
            throw new InvalidInputException("error.name.empty");
        }
        if (mall.getTotalArea() <= 0) {
            throw new InvalidInputException("error.area.zero");
        }
        if (floors <= 0) {
            throw new InvalidInputException("error.floors.zero");
        }
        return mall.getTotalArea() / floors;
    }

}