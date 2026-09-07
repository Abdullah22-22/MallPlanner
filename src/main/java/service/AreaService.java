package service;

import exception.InvalidInputException;
import model.Floor;


public class AreaService {

    // Rentable area = floor area - all services of that floor
    public double rentableArea(Floor floor, double services) {
        if (floor == null) {
            throw new InvalidInputException("error.floor.null");
        }
        if (floor.getArea() <= 0) {
            throw new InvalidInputException("error.area.zero");
        }
        if (services < 0) {
            throw new InvalidInputException("error.services.negative");
        }
        if (services > floor.getArea()) {
            throw new InvalidInputException("error.services.big");
        }
        return floor.getArea() - services;
    }

    // Free space = rentable area - area already used by shops
    public double freeSpace(Floor floor, double services, double usedByShops){
        if (usedByShops < 0){
            throw new InvalidInputException("error.area.negative");
        }
        double rentable = rentableArea(floor,services);

        if(usedByShops > rentable){
            throw new InvalidInputException("error.shops.big");
        }
        return  rentable - usedByShops;
    }
}