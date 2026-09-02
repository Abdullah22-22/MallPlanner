package service;

import exception.InvalidInputException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FloorServiceTest {

    private final FloorService service = new FloorService();

    @Test
    void normalCase() {
        FloorService.Floor floor = new FloorService.Floor(1000, 450);
        assertEquals(550, service.freeSpace(floor));
    }

    @Test
    void zeroArea() {
        FloorService.Floor floor = new FloorService.Floor(0, 100);
        InvalidInputException e = assertThrows(
                InvalidInputException.class, () -> service.freeSpace(floor));
        assertEquals("error.area.zero", e.getMessage());
    }

    @Test
    void negativeServices() {
        FloorService.Floor floor = new FloorService.Floor(1000, -50);
        InvalidInputException e = assertThrows(
                InvalidInputException.class, () -> service.freeSpace(floor));
        assertEquals("error.services.negative", e.getMessage());
    }

    @Test
    void servicesBiggerThanFloor() {
        FloorService.Floor floor = new FloorService.Floor(1000, 1200);
        InvalidInputException e = assertThrows(
                InvalidInputException.class, () -> service.freeSpace(floor));
        assertEquals("error.services.big", e.getMessage());
    }
}