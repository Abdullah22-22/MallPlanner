package service;

import exception.InvalidInputException;
import model.Floor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AreaServiceTest {

    private final AreaService service = new AreaService();

    private Floor floorWithArea(double area) {
        return new Floor(1, 1, area, 200, 50000);
    }

    @Test
    void normalCase() {
        assertEquals(550, service.rentableArea(floorWithArea(1000), 450));
    }

    @Test
    void noServices() {
        assertEquals(1000, service.rentableArea(floorWithArea(1000), 0));
    }

    @Test
    void servicesBiggerThanFloor() {
        InvalidInputException e = assertThrows(InvalidInputException.class,
                () -> service.rentableArea(floorWithArea(1000), 1200));
        assertEquals("error.services.big", e.getMessage());
    }

    @Test
    void zeroArea() {
        InvalidInputException e = assertThrows(InvalidInputException.class,
                () -> service.rentableArea(floorWithArea(0), 100));
        assertEquals("error.area.zero", e.getMessage());
    }

    @Test
    void negativeServices() {
        InvalidInputException e = assertThrows(InvalidInputException.class,
                () -> service.rentableArea(floorWithArea(1000), -50));
        assertEquals("error.services.negative", e.getMessage());
    }

    @Test
    void freeSpaceAfterShops() {
        assertEquals(80, service.freeSpace(floorWithArea(1000), 450, 470));
    }

    @Test
    void shopsBiggerThanRentable() {
        InvalidInputException e = assertThrows(InvalidInputException.class,
                () -> service.freeSpace(floorWithArea(1000), 450, 600));
        assertEquals("error.shops.big", e.getMessage());
    }
}