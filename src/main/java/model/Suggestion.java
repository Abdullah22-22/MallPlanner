package model;

public class Suggestion {

    private int shopCount;
    private double shopSize;
    private double areaLeft;
    private double income;

    public Suggestion(int shopCount, double shopSize, double areaLeft, double income) {
        this.shopCount = shopCount;
        this.shopSize = shopSize;
        this.areaLeft = areaLeft;
        this.income = income;
    }

    public int getShopCount() { return shopCount; }
    public double getShopSize() { return shopSize; }
    public double getAreaLeft() { return areaLeft; }
    public double getIncome() { return income; }
}