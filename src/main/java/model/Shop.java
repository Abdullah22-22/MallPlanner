package model;

public class Shop {

    private int id;
    private int floorId;
    private String name;
    private double area;

    public Shop(int id, int floorId, String name, double area) {
        this.id = id;
        this.floorId = floorId;
        this.name = name;
        this.area = area;
    }

    public Shop(int id, int floorId, String name) {
        this.floorId = floorId;
        this.name = name;
        this.area = area;

    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getFloorId() { return floorId; }
    public void setFloorId(int floorId) { this.floorId = floorId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getArea() { return area; }
    public void setArea(double area) { this.area = area; }
}
