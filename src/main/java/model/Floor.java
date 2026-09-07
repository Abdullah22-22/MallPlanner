package model;

public class Floor {

    private int id;
    private int mallId;
    private int floorNumber;
    private double area;
    private double rentPrice;
    private double cost;

    public Floor(int id, int mallId, int floorNumber,
                 double area, double rentPrice, double cost) {
        this.id = id;
        this.mallId = mallId;
        this.floorNumber = floorNumber;
        this.area = area;
        this.rentPrice = rentPrice;
        this.cost = cost;
    }

    public  Floor (int mallId, int floorNumber,
                   double area, double rentPrice, double cost){
        this.mallId = mallId;
        this.floorNumber = floorNumber;
        this.area = area;
        this.rentPrice = rentPrice;
        this.cost = cost;
    }

    public int getId(){
        return  id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getMallId() {
        return mallId;
    }

    public void setMallId(int mallId) {
        this.mallId = mallId;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(double rentPrice) {
        this.rentPrice = rentPrice;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

}