package service;

import exception.InvalidInputException;
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
        List<SuggestionService.Suggestion> options = service.buildOptions(80, RENT_PRICE);

        assertEquals(3, options.size());

        SuggestionService.Suggestion thirty = findBySize(options, 30);
        assertEquals(2, thirty.getShopCount());
        assertEquals(6000, thirty.getTotalIncome(), DELTA);   // 2 * 30 * 100

        SuggestionService.Suggestion twenty = findBySize(options, 20);
        assertEquals(4, twenty.getShopCount());
        assertEquals(8000, twenty.getTotalIncome(), DELTA);   // 4 * 20 * 100

        SuggestionService.Suggestion sixty = findBySize(options, 60);
        assertEquals(1, sixty.getShopCount());
        assertEquals(6000, sixty.getTotalIncome(), DELTA);    // 1 * 60 * 100
    }

    @Test
    void noFreeArea_givesNoOptions() {
        List<SuggestionService.Suggestion> options = service.buildOptions(0, RENT_PRICE);
        assertTrue(options.isEmpty());
    }

    @Test
    void negativeFreeArea_throws() {
        assertThrows(InvalidInputException.class,
                () -> service.buildOptions(-10, RENT_PRICE));
    }

    @Test
    void sortAndMarkBest_highestIncomeIsFirstAndMarked() {
        List<SuggestionService.Suggestion> options = service.buildOptions(80, RENT_PRICE);

        List<SuggestionService.Suggestion> sorted = service.sortAndMarkBest(options);

        assertEquals(8000, sorted.get(0).getTotalIncome(), DELTA);
        assertTrue(sorted.get(0).isBest());

        long bestCount = sorted.stream().filter(SuggestionService.Suggestion::isBest).count();
        assertEquals(1, bestCount);
    }

    private SuggestionService.Suggestion findBySize(List<SuggestionService.Suggestion> options, double size) {
        for (SuggestionService.Suggestion s : options) {
            if (s.getShopSize() == size) {
                return s;
            }
        }
        fail("No suggestion with size " + size);
        return null;
    }
}