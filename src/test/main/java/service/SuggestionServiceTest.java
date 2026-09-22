package service;

import exception.InvalidInputException;
import model.Suggestion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SuggestionServiceTest {

    private static final double DELTA = 0.001;
    private static final double RENT_PRICE = 100;

    private SuggestionService service;

    @BeforeEach
    void setUp() {
        service = new SuggestionService();
    }

    @Test
    void freeArea80_givesThreeOptions() {
        List<Suggestion> options = service.buildOptions(80, RENT_PRICE);

        assertEquals(3, options.size());

        Suggestion thirty = findBySize(options, 30);
        assertEquals(2, thirty.getShopCount());
        assertEquals(6000, thirty.getIncome(), DELTA);

        Suggestion twenty = findBySize(options, 20);
        assertEquals(4, twenty.getShopCount());
        assertEquals(8000, twenty.getIncome(), DELTA);

        Suggestion sixty = findBySize(options, 60);
        assertEquals(1, sixty.getShopCount());
        assertEquals(6000, sixty.getIncome(), DELTA);
    }

    @Test
    void noFreeArea_givesNoOptions() {
        List<Suggestion> options = service.buildOptions(0, RENT_PRICE);
        assertTrue(options.isEmpty());
    }

    @Test
    void negativeFreeArea_throws() {
        assertThrows(InvalidInputException.class,
                () -> service.buildOptions(-10, RENT_PRICE));
    }

    @Test
    void sortAndMarkBest_highestIncomeIsFirstAndMarked() {
        List<Suggestion> options = service.buildOptions(80, RENT_PRICE);

        List<Suggestion> sorted = service.sortAndMarkBest(options);

        assertEquals(8000, sorted.get(0).getIncome(), DELTA);
        assertTrue(sorted.get(0).isBest());

        long bestCount = sorted.stream().filter(Suggestion::isBest).count();
        assertEquals(1, bestCount);
    }

    private Suggestion findBySize(List<Suggestion> options, double size) {
        for (Suggestion s : options) {
            if (s.getShopSize() == size) {
                return s;
            }
        }
        fail("No suggestion with size " + size);
        return null;
    }
}