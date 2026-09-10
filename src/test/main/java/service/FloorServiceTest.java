package service;

import exception.InvalidInputException;
import model.Floor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FloorServiceTest {

    private final FloorService service = new FloorService();

    private Floor floorWithArea(double area) {
        return new Floor(1, 1, area, 200, 50000);
    }

    @Test
    void normalCase() {
        assertEquals(550, service.freeSpace(floorWithArea(1000), 450));
    }

    @Test
    void zeroArea() {
        InvalidInputException e = assertThrows(InvalidInputException.class,
                () -> service.freeSpace(floorWithArea(0), 100));
        assertEquals("error.area.zero", e.getMessage());
    }

    @Test
    void negativeServices() {
        InvalidInputException e = assertThrows(InvalidInputException.class,
                () -> service.freeSpace(floorWithArea(1000), -50));
        assertEquals("error.services.negative", e.getMessage());
    }

    @Test
    void servicesBiggerThanFloor() {
        InvalidInputException e = assertThrows(InvalidInputException.class,
                () -> service.freeSpace(floorWithArea(1000), 1200));
        assertEquals("error.services.big", e.getMessage());
    }
}