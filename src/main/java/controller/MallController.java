package controller;

import dao.FloorDAO;
import dao.MallDAO;
import dao.ServiceAreaDAO;
import dao.ShopDAO;
import model.Floor;
import model.Mall;
import model.ServiceArea;
import model.Shop;
import service.FloorService;
import service.MallService;
import service.ShopService;
import model.FloorProfit;
import model.Suggestion;
import service.ProfitService;
import service.SuggestionService;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MallController {

    private final MallService mallService = new MallService();
    private final FloorService floorService = new FloorService();
    private final ShopService shopService = new ShopService();

    private final MallDAO mallDAO = new MallDAO();
    private final FloorDAO floorDAO = new FloorDAO();
    private final ServiceAreaDAO serviceAreaDAO = new ServiceAreaDAO();
    private final ShopDAO shopDAO = new ShopDAO();
    private final ProfitService profitService = new ProfitService();
    private final SuggestionService suggestionService = new SuggestionService();

    // ---------- mall ----------

    public double areaPerFloor(String name, double totalArea, int floors) {
        Mall mall = new Mall(name, totalArea);
        return mallService.areaPerFloor(mall, floors);
    }

    public int saveMall(String name, double totalArea) throws SQLException {
        Mall mall = new Mall(name, totalArea);
        mallDAO.save(mall);
        return mall.getId();
    }

    // ---------- floor ----------

    // Returns the saved floor, so the screen knows the real database id
    public Floor addFloor(int mallId, int number, double area,
                          double rentPrice, double cost) throws SQLException {
        Mall mall = mallDAO.findById(mallId);
        floorService.checkFloorArea(area, mall.getTotalArea());

        Floor floor = new Floor(mallId, number, area, rentPrice, cost);
        floorDAO.save(floor);

        return floor;
    }

    public List<Floor> floorsOfMall(int mallId) throws SQLException {
        return floorDAO.findByMallId(mallId);
    }

    // ---------- service areas ----------

    public void addServiceArea(int floorId, String type, double size)
            throws SQLException {

        serviceAreaDAO.save(
                new ServiceArea(floorId, type, size)
        );
    }

    // Sum of all service areas of one floor
    public double totalServices(int floorId) throws SQLException {

        double total = 0;

        for (ServiceArea area : serviceAreaDAO.findByFloorId(floorId)) {
            total += area.getSize();
        }

        return total;
    }

    // ---------- shops ----------

    public List<Shop> shopsOfFloor(int floorId) throws SQLException {
        return shopDAO.findByFloorId(floorId);
    }

    public double usedByShops(int floorId) throws SQLException {

        return shopService.usedByShops(shopsOfFloor(floorId));

    }

    public double freeSpace(Floor floor) throws SQLException {

        double services = totalServices(floor.getId());

        List<Shop> shops = shopsOfFloor(floor.getId());
        return shopService.freeSpace(floor, services, shops);

    }

    // Service checks first, then the DAO saves
    public void addShop(Floor floor, String name,
                        double area, String category)
            throws SQLException {

        double services = totalServices(floor.getId());
        List<Shop> shops = shopsOfFloor(floor.getId());
        shopService.addShop(floor, services, shops, area);
        shopDAO.save(new Shop(0, floor.getId(), name, area, category));

    }

    public void editShop(Floor floor, Shop shop, double newArea)
            throws SQLException {

        double services = totalServices(floor.getId());
        List<Shop> shops = shopsOfFloor(floor.getId());
        shopService.editShop(floor, services, shops, shop, newArea);

        shop.setArea(newArea);

        shopDAO.update(shop);
    }

    public void deleteShop(Floor floor, Shop shop) throws SQLException {
        double services = totalServices(floor.getId());
        List<Shop> shops = shopsOfFloor(floor.getId());
        shopService.deleteShop(floor, services, shops, shop);
        shopDAO.delete(shop.getId());
    }

    // ---------- suggestions ----------

    // Suggestions for one floor: free space, then the service
    public List<Suggestion> suggestionsForFloor(Floor floor) throws SQLException {
        double freeArea = freeSpace(floor);
        List<Suggestion> options = suggestionService.buildOptions(freeArea, floor.getRentPrice());
        return suggestionService.sortAndMarkBest(options);
    }

    // ---------- profit report ----------

    // Profit report: load the floors and their shops, then the service
    public List<FloorProfit> profitReport(int mallId) throws SQLException {
        List<Floor> floors = floorsOfMall(mallId);
        List<FloorProfit> profits = new ArrayList<>();

        for (Floor floor : floors) {
            double services = totalServices(floor.getId());
            double used = usedByShops(floor.getId());
            profits.add(profitService.calculateProfit(floor, services, used));
        }

        return profits;
    }

    public FloorProfit bestFloor(List<FloorProfit> profits) {
        return profitService.bestFloor(profits);
    }

    public double mallOccupancyPercent(List<FloorProfit> profits) {
        return profitService.mallOccupancyPercent(profits);
    }
}