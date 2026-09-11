package model;

public class ServiceArea {
    private int id;
    private int floorid;
    private String type;
    private double size;

    public ServiceArea(int id, int floorid, String type, double size) {
        this.id = id;
        this.floorid = floorid;
        this.type = type;
        this.size = size;
    }

    public ServiceArea(int floorid, String type, double size) {
        this.floorid = floorid;
        this.type = type;
        this.size = size;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getFloorId() { return floorid; }
    public void setFloorId(int floorid) { this.floorid = floorid; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getSize() { return size; }
    public void setSize(double size) { this.size = size; }
}

