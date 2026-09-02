package service;

import exception.InvalidInputException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MallServiceTest {

    private final MallService service = new MallService();

    @Test
    void normalCase() {
        MallService.Mall mall = new MallService.Mall("City Mall", 3000, 3);
        assertEquals(1000, service.areaPerFloor(mall));
    }

    @Test
    void emptyName() {
        MallService.Mall mall = new MallService.Mall("  ", 3000, 3);
        InvalidInputException e = assertThrows(
                InvalidInputException.class, () -> service.areaPerFloor(mall));
        assertEquals("error.name.empty", e.getMessage());
    }

    @Test
    void zeroArea() {
        MallService.Mall mall = new MallService.Mall("City Mall", 0, 3);
        InvalidInputException e = assertThrows(
                InvalidInputException.class, () -> service.areaPerFloor(mall));
        assertEquals("error.area.zero", e.getMessage());
    }

    @Test
    void zeroFloors() {
        MallService.Mall mall = new MallService.Mall("City Mall", 3000, 0);
        InvalidInputException e = assertThrows(
                InvalidInputException.class, () -> service.areaPerFloor(mall));
        assertEquals("error.floors.zero", e.getMessage());
    }
}