package service;

import exception.InvalidInputException;
import model.Floor;

import java.util.List;

public class ProfitService {

    private final AreaService areaService = new AreaService();

    // Income = rentable area x rent price. Profit = income - cost.
    public FloorProfit calculateProfit(Floor floor, double services, double usedByShops) {
        double rentable = areaService.rentableArea(floor, services);
        // Reuses freeSpace() just to validate usedByShops (negative / bigger than rentable)
        areaService.freeSpace(floor, services, usedByShops);

        double income = rentable * floor.getRentPrice();
        double profit = income - floor.getCost();
        double occupancy = rentable == 0 ? 0 : (usedByShops / rentable) * 100;

        return new FloorProfit(floor.getFloorNumber(), rentable, usedByShops,
                income, floor.getCost(), profit, occupancy);
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

    // Result of a profit calculation for one floor.
    // TODO: if the model package owner already has/plans a model.FloorProfit class,
    // delete this nested class and switch back to "import model.FloorProfit;" above.
    public static class FloorProfit {
        private final int floorNumber;
        private final double rentableArea;
        private final double usedByShops;
        private final double income;
        private final double cost;
        private final double profit;
        private final double occupancyPercent;

        public FloorProfit(int floorNumber, double rentableArea, double usedByShops,
                           double income, double cost, double profit, double occupancyPercent) {
            this.floorNumber = floorNumber;
            this.rentableArea = rentableArea;
            this.usedByShops = usedByShops;
            this.income = income;
            this.cost = cost;
            this.profit = profit;
            this.occupancyPercent = occupancyPercent;
        }

        public int getFloorNumber() { return floorNumber; }
        public double getRentableArea() { return rentableArea; }
        public double getUsedByShops() { return usedByShops; }
        public double getIncome() { return income; }
        public double getCost() { return cost; }
        public double getProfit() { return profit; }
        public double getOccupancyPercent() { return occupancyPercent; }
    }
}