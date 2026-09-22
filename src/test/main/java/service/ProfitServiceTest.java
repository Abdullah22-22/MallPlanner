package service;

import exception.InvalidInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Floor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProfitServiceTest {

    private static final double DELTA = 0.001;

    private ProfitService service;

    @BeforeEach
    void setUp() {
        service = new ProfitService();
    }

    private Floor floor(int floorNumber, double area, double rentPrice, double cost) {
        return new Floor(1, 1, floorNumber, area, rentPrice, cost);
    }

    @Test
    void normalCase_incomeAndProfit() {
        Floor f = floor(1, 200, 100, 5000);          // rentable = 180 (services=20)
        ProfitService.FloorProfit profit = service.calculateProfit(f, 20, 100);

        assertEquals(18000, profit.getIncome(), DELTA);
        assertEquals(13000, profit.getProfit(), DELTA);
        assertEquals((100.0 / 180) * 100, profit.getOccupancyPercent(), DELTA);
    }

    @Test
    void zeroShops_occupancyIsZero() {
        Floor f = floor(1, 200, 100, 5000);
        ProfitService.FloorProfit profit = service.calculateProfit(f, 20, 0);

        assertEquals(0, profit.getOccupancyPercent(), DELTA);
        assertEquals(18000, profit.getIncome(), DELTA);
    }

    @Test
    void negativeProfit_whenCostBiggerThanIncome() {
        Floor f = floor(1, 100, 10, 5000);           // rentable = 80, income = 800
        ProfitService.FloorProfit profit = service.calculateProfit(f, 20, 0);

        assertEquals(-4200, profit.getProfit(), DELTA);
    }

    @Test
    void bestFloor_picksHighestProfit() {
        ProfitService.FloorProfit low = service.calculateProfit(floor(1, 100, 50, 1000), 0, 0);   // profit 4000
        ProfitService.FloorProfit high = service.calculateProfit(floor(2, 200, 50, 1000), 0, 0);  // profit 9000

        ProfitService.FloorProfit best = service.bestFloor(List.of(low, high));

        assertEquals(2, best.getFloorNumber());
    }

    @Test
    void mallOccupancyPercent_averagesAcrossFloors() {
        ProfitService.FloorProfit f1 = service.calculateProfit(floor(1, 100, 50, 0), 0, 50);   // rentable 100, used 50
        ProfitService.FloorProfit f2 = service.calculateProfit(floor(2, 100, 50, 0), 0, 100);  // rentable 100, used 100

        double occupancy = service.mallOccupancyPercent(List.of(f1, f2));

        assertEquals(75, occupancy, DELTA); // (50+100)/(100+100) * 100
    }

    @Test
    void floorNull_throws() {
        assertThrows(InvalidInputException.class,
                () -> service.calculateProfit(null, 20, 0));
    }

    @Test
    void bestFloor_emptyList_throws() {
        assertThrows(InvalidInputException.class,
                () -> service.bestFloor(List.of()));
    }
}