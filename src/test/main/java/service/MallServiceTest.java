package service;

import exception.InvalidInputException;
import model.Mall;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MallServiceTest {

    private final MallService service = new MallService();

    @Test
    void normalCase() {
        Mall mall = new Mall("City Mall", 3000);
        assertEquals(1000, service.areaPerFloor(mall, 3));
    }

    @Test
    void emptyName() {
        Mall mall = new Mall("  ", 3000);
        InvalidInputException e = assertThrows(InvalidInputException.class,
                () -> service.areaPerFloor(mall, 3));
        assertEquals("error.name.empty", e.getMessage());
    }

    @Test
    void zeroArea() {
        Mall mall = new Mall("City Mall", 0);
        InvalidInputException e = assertThrows(InvalidInputException.class,
                () -> service.areaPerFloor(mall, 3));
        assertEquals("error.area.zero", e.getMessage());
    }

    @Test
    void zeroFloors() {
        Mall mall = new Mall("City Mall", 3000);
        InvalidInputException e = assertThrows(InvalidInputException.class,
                () -> service.areaPerFloor(mall, 0));
        assertEquals("error.floors.zero", e.getMessage());
    }
}