package model;

public class FloorProfit  {

    private double rentableArea;
    private int shops;
    private double freeArea;
    private double income;
    private double cost;
    private double profit;

    public FloorProfit(double rentableArea, int shops, double freeArea, double income, double cost, double profit) {
        this.rentableArea = rentableArea;
        this.shops = shops;
        this.freeArea = freeArea;
        this.income = income;
        this.cost = cost;
        this.profit = profit;
    }

    public double getRentableArea() { return rentableArea; }
    public int getShops() { return shops; }
    public double getFreeArea() { return freeArea; }
    public double getIncome() { return income; }
    public double getCost() { return cost; }
    public double getProfit() { return profit; }


}