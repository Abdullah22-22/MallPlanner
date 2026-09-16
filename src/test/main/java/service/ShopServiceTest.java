package service;

import exception.InvalidInputException;
import exception.NotEnoughSpaceException;
import model.Floor;
import model.Shop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    // Floor 100 m2, services 40 m2  ->  rentable area 60 m2
    private static final double SERVICES = 40;
    private static final double DELTA = 0.001;

    private ShopService service;
    private Floor floor;

    @BeforeEach
    void setUp() {
        service = new ShopService();
        floor = new Floor(1, 1, 1, 100, 100, 10);
    }

    // One place to create shops (update here if Shop gets a category)
    private Shop shop(int id, double area) {
        return new Shop(id, floor.getId(), "Shop " + id, area);
    }

    // ---------- used and free space ----------

    @Test
    void usedByShops_sumsAllShopAreas() {
        List<Shop> shops = List.of(shop(1, 10), shop(2, 20), shop(3, 15));
        assertEquals(45, service.usedByShops(shops), DELTA);
    }

    @Test
    void freeSpace_noShops_isRentableArea() {
        assertEquals(60, service.freeSpace(floor, SERVICES, List.of()), DELTA);
    }

    // ---------- add ----------

    @Test
    void addShop_fits_returnsFreeSpaceLeft() {
        double free = service.addShop(floor, SERVICES, List.of(), 50);
        assertEquals(10, free, DELTA);
    }

    @Test
    void addShop_zeroArea_throws() {
        InvalidInputException e = assertThrows(InvalidInputException.class,
                () -> service.addShop(floor, SERVICES, List.of(), 0));
        assertEquals("error.shop.area.zero", e.getMessage());
    }

    @Test
    void addShop_negativeArea_throws() {
        assertThrows(InvalidInputException.class,
                () -> service.addShop(floor, SERVICES, List.of(), -5));
    }

    @Test
    void addShop_notEnoughSpace_throwsWithMissingArea() {
        List<Shop> shops = List.of(shop(1, 50));   // free = 10

        NotEnoughSpaceException e = assertThrows(NotEnoughSpaceException.class,
                () -> service.addShop(floor, SERVICES, shops, 30));
        assertEquals(20, e.getMissingArea(), DELTA);
    }

    // ---------- delete ----------

    @Test
    void deleteShop_givesSpaceBack() {
        Shop b = shop(2, 20);
        List<Shop> shops = List.of(shop(1, 30), b);   // free = 10

        double free = service.deleteShop(floor, SERVICES, shops, b);
        assertEquals(30, free, DELTA);
    }

    @Test
    void deleteShop_secondOfThree_givesItsSpaceBack() {
        Shop second = shop(2, 20);
        List<Shop> shops = List.of(shop(1, 10), second, shop(3, 15)); // free = 15

        double free = service.deleteShop(floor, SERVICES, shops, second);
        assertEquals(35, free, DELTA);
    }

    @Test
    void deleteShop_findsShopById() {
        List<Shop> shops = List.of(shop(1, 30));   // free = 30
        Shop sameIdOtherObject = shop(1, 30);

        double free = service.deleteShop(floor, SERVICES, shops, sameIdOtherObject);
        assertEquals(60, free, DELTA);
    }

    @Test
    void deleteShop_notInList_throws() {
        List<Shop> shops = List.of(shop(1, 30));

        InvalidInputException e = assertThrows(InvalidInputException.class,
                () -> service.deleteShop(floor, SERVICES, shops, shop(99, 10)));
        assertEquals("error.shop.not.found", e.getMessage());
    }

    // ---------- edit ----------

    @Test
    void editShop_bigger_updatesFreeSpace() {
        Shop a = shop(1, 30);

        double free = service.editShop(floor, SERVICES, List.of(a), a, 50);
        assertEquals(10, free, DELTA);
    }

    @Test
    void editShop_smaller_updatesFreeSpace() {
        Shop a = shop(1, 30);

        double free = service.editShop(floor, SERVICES, List.of(a), a, 10);
        assertEquals(50, free, DELTA);
    }

    @Test
    void editShop_tooBig_throwsWithMissingArea() {
        Shop a = shop(1, 30);
        List<Shop> shops = List.of(a, shop(2, 20));   // free = 10, with a removed = 40

        NotEnoughSpaceException e = assertThrows(NotEnoughSpaceException.class,
                () -> service.editShop(floor, SERVICES, shops, a, 50));
        assertEquals(10, e.getMissingArea(), DELTA);
    }

    @Test
    void editShop_zeroArea_throws() {
        Shop a = shop(1, 30);

        assertThrows(InvalidInputException.class,
                () -> service.editShop(floor, SERVICES, List.of(a), a, 0));
    }

    @Test
    void editShop_notInList_throws() {
        List<Shop> shops = List.of(shop(1, 30));

        assertThrows(InvalidInputException.class,
                () -> service.editShop(floor, SERVICES, shops, shop(99, 10), 20));
    }
}