package model;

public class FloorProfit  {

    private final int floorNumber;
    private final double rentableArea;
    private final double usedByShops;
    private final double freeArea;
    private final double income;
    private final double cost;
    private final double profit;
    private final double occupancyPercent;

    public FloorProfit(int floorNumber, double rentableArea, double usedByShops,
                       double freeArea, double income, double cost,
                       double profit, double occupancyPercent) {
        this.floorNumber = floorNumber;
        this.rentableArea = rentableArea;
        this.usedByShops = usedByShops;
        this.freeArea = freeArea;
        this.income = income;
        this.cost = cost;
        this.profit = profit;
        this.occupancyPercent = occupancyPercent;
    }

    public int getFloorNumber() { return floorNumber; }
    public double getRentableArea() { return rentableArea; }
    public double getUsedByShops() { return usedByShops; }
    public double getFreeArea() { return freeArea; }
    public double getIncome() { return income; }
    public double getCost() { return cost; }
    public double getProfit() { return profit; }
    public double getOccupancyPercent() { return occupancyPercent; }


}