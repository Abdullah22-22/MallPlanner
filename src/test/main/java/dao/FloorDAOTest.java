package dao;

import model.Floor;
import model.Mall;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FloorDAOTest {

    private final MallDAO mallDAO = new MallDAO();
    private final FloorDAO floorDAO = new FloorDAO();

    @Test
    void saveTwoFloorsAndReadThemBack() throws Exception {
        Mall mall = new Mall("Test Mall", 3000);
        mallDAO.save(mall);

        Floor f1 = new Floor(mall.getId(), 1, 1000, 200, 50000);
        Floor f2 = new Floor(mall.getId(), 2, 1000, 180, 45000);
        floorDAO.save(f1);
        floorDAO.save(f2);

        List<Floor> floors = floorDAO.findByMallId(mall.getId());
        assertEquals(2, floors.size());
        assertEquals(1, floors.get(0).getFloorNumber());
        assertEquals(2, floors.get(1).getFloorNumber());

        floorDAO.delete(f1.getId());
        floorDAO.delete(f2.getId());
        mallDAO.delete(mall.getId());
    }
}