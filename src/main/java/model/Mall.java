package model;

public class Mall {

    private int id;
    private String name;
    private double totalArea;

    public Mall(int id, String name, double totalArea) {
        this.id = id;
        this.name = name;
        this.totalArea = totalArea;
    }

    public Mall(String name, double totalArea) {
        this.name = name;
        this.totalArea = totalArea;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(double totalArea) {
        this.totalArea = totalArea;
    }
}