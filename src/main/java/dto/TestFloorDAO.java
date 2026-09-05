package dao;

import model.Floor;
import model.Mall;

import java.util.List;

public class TestFloorDAO {
    public static void main(String[] args) throws Exception {
        MallDAO mallDAO = new MallDAO();
        FloorDAO floorDAO = new FloorDAO();

        // a floor needs a mall first
        Mall mall = new Mall("Test Mall", 3000);
        mallDAO.save(mall);
        System.out.println("Mall id: " + mall.getId());

        // save two floors
        Floor f1 = new Floor(mall.getId(), 1, 1000, 200, 50000);
        Floor f2 = new Floor(mall.getId(), 2, 1000, 180, 45000);
        floorDAO.save(f1);
        floorDAO.save(f2);
        System.out.println("Floor ids: " + f1.getId() + ", " + f2.getId());

        // findByMallId
        List<Floor> floors = floorDAO.findByMallId(mall.getId());
        System.out.println("Floors found: " + floors.size());

        // update
        f1.setArea(1200);
        floorDAO.update(f1);
        System.out.println("Area after update: "
                + floorDAO.findByMallId(mall.getId()).get(0).getArea());

        // delete both floors, then the mall
        floorDAO.delete(f1.getId());
        floorDAO.delete(f2.getId());
        System.out.println("Floors left: "
                + floorDAO.findByMallId(mall.getId()).size());

        mallDAO.delete(mall.getId());
        System.out.println("Cleaned up");
    }
}