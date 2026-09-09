package service;

import exception.InvalidInputException;
import exception.NotEnoughSpaceException;
import model.Floor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ShopServiceTest {

    private final ShopService service = new ShopService();

    private Floor floorWithArea(double area) {
        return new Floor(1, 1, area, 200, 50000);
    }

    // Floor 1000, services 450, used 470  ->  free space is 80

    @Test
    void shopFits() {
        assertEquals(50, service.addShop(floorWithArea(1000), 450, 470, 30));
    }

    @Test
    void shopTooBig() {
        NotEnoughSpaceException e = assertThrows(NotEnoughSpaceException.class,
                () -> service.addShop(floorWithArea(1000), 450, 470, 120));
        assertEquals("error.not.enough.space", e.getMessage());
        assertEquals(40, e.getMissingArea());
    }

    @Test
    void zeroShopArea() {
        InvalidInputException e = assertThrows(InvalidInputException.class,
                () -> service.addShop(floorWithArea(1000), 450, 470, 0));
        assertEquals("error.shop.area.zero", e.getMessage());
    }

    @Test
    void shopUsesAllFreeSpace() {
        assertEquals(0, service.addShop(floorWithArea(1000), 450, 470, 80));
    }

    @Test
    void editShopToBiggerArea() {
        // A 30 shop becomes 50. Used goes 470 -> 440, then 50 is added.
        assertEquals(60, service.editShop(floorWithArea(1000), 450, 470, 30, 50));
    }

    @Test
    void editShopTooBig() {
        NotEnoughSpaceException e = assertThrows(NotEnoughSpaceException.class,
                () -> service.editShop(floorWithArea(1000), 450, 470, 30, 200));
        assertEquals("error.not.enough.space", e.getMessage());
        assertEquals(90, e.getMissingArea());
    }
}