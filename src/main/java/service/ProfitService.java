package service;

import exception.InvalidInputException;
import model.Floor;
import model.FloorProfit;

import java.util.List;

public class ProfitService {

    private final AreaService areaService = new AreaService();

    // Income = area actually rented by shops x rent price. Profit = income - cost.
    public FloorProfit calculateProfit(Floor floor, double services, double usedByShops) {
        double rentable = areaService.rentableArea(floor, services);
        double freeArea = areaService.freeSpace(floor, services, usedByShops);

        double income = usedByShops * floor.getRentPrice();
        double profit = income - floor.getCost();

        double occupancy = rentable == 0 ? 0 : (usedByShops / rentable) * 100;

        return new FloorProfit(floor.getFloorNumber(), rentable, usedByShops,
                freeArea, income, floor.getCost(), profit, occupancy);
    }
    // Best floor and occupancy percent for the whole mall
    public FloorProfit bestFloor(List<FloorProfit> floorProfits) {
        if (floorProfits == null || floorProfits.isEmpty()) {
            throw new InvalidInputException("error.floors.empty");
        }
        FloorProfit best = floorProfits.get(0);
        for (FloorProfit fp : floorProfits) {
            if (fp.getProfit() > best.getProfit()) {
                best = fp;
            }
        }
        return best;
    }

    public double mallOccupancyPercent(List<FloorProfit> floorProfits) {
        if (floorProfits == null || floorProfits.isEmpty()) {
            throw new InvalidInputException("error.floors.empty");
        }
        double totalRentable = 0;
        double totalUsed = 0;
        for (FloorProfit fp : floorProfits) {
            totalRentable += fp.getRentableArea();
            totalUsed += fp.getUsedByShops();
        }
        return totalRentable == 0 ? 0 : (totalUsed / totalRentable) * 100;
    }


}