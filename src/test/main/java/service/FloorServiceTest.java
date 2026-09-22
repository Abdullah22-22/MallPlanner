package service;

import exception.InvalidInputException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FloorServiceTest {

    private final FloorService service = new FloorService();

    @Test
    void normalCase() {
        assertDoesNotThrow(() -> service.checkFloorArea(1000, 5000));
    }

    @Test
    void zeroArea() {
        InvalidInputException e = assertThrows(InvalidInputException.class,
                () -> service.checkFloorArea(0, 5000));
        assertEquals("error.area.zero", e.getMessage());
    }

    @Test
    void negativeArea() {
        InvalidInputException e = assertThrows(InvalidInputException.class,
                () -> service.checkFloorArea(-100, 5000));
        assertEquals("error.area.zero", e.getMessage());
    }

    @Test
    void floorBiggerThanMall() {
        InvalidInputException e = assertThrows(InvalidInputException.class,
                () -> service.checkFloorArea(6000, 5000));
        assertEquals("error.floor.bigger.than.mall", e.getMessage());
    }
}