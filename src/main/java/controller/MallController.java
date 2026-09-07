package controller;

import dao.FloorDAO;
import dao.MallDAO;
import model.Floor;
import model.Mall;
import service.MallService;
import service.FloorService;

import java.sql.SQLException;

public class MallController {

    private  final MallService mallService = new MallService();
    private final MallDAO mallDAO = new MallDAO();
    private final FloorDAO floorDAO = new FloorDAO();
    private final FloorService floorService = new FloorService();

    public double  areaPerFloor(String name, double totalArea, int floors){
        Mall mall = new Mall(name, totalArea);
        return mallService.areaPerFloor(mall,floors);
    }

    public int saveMall(String name, double totalArea) throws SQLException{
        Mall mall = new Mall(name, totalArea);
        mallDAO.save(mall);
        return mall.getId();
    }

    public void addFloor(int mallId, int number, double area,
                         double rentPrice, double cost) throws SQLException {
        Mall mall = mallDAO.findById(mallId);
        floorService.checkFloorArea(area, mall.getTotalArea());
        floorDAO.save(new Floor(mallId, number, area, rentPrice, cost));
    }
}