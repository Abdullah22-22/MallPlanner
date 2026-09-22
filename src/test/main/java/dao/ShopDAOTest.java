package dao;

import model.Floor;
import model.Mall;
import model.Shop;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopDAOTest {

    private final dao.MallDAO mallDAO = new dao.MallDAO();
    private final dao.FloorDAO floorDAO = new dao.FloorDAO();
    private final ShopDAO shopDAO = new ShopDAO();

    @Test
    void saveReadUpdateAndDeleteShop() throws Exception {
        Mall mall = new Mall("Test Mall", 3000);
        mallDAO.save(mall);

        Floor floor = new Floor(mall.getId(), 1, 1000, 200, 50000);
        floorDAO.save(floor);

        Shop shop = new Shop(floor.getId(), "Kauppa", 45.0, "tavarat");
        shopDAO.save(shop);

        List<Shop> shops = shopDAO.findByFloorId(floor.getId());
        Shop found = shops.get(0);

        assertEquals("Kauppa", found.getName());
        assertEquals(45.0, found.getArea(), 0.001);
        assertEquals("tavarat", found.getCategory());

        found.setName("Kauppa");
        found.setArea(30.0);
        found.setCategory("tavarat");
        shopDAO.update(found);

        List<Shop> afterUpdate = shopDAO.findByFloorId(floor.getId());
        Shop updated = afterUpdate.get(0);
        assertEquals("Kauppa", updated.getName());
        assertEquals(30.0, updated.getArea(), 0.001);
        assertEquals("tavarat",updated.getCategory());

        shopDAO.delete(updated.getId());
        List<Shop> afterDelete = shopDAO.findByFloorId(floor.getId());
        assertTrue(afterDelete.isEmpty());


        floorDAO.delete(floor.getId());
        mallDAO.delete(mall.getId());
    }
}
