package ui;

import model.Floor;
import model.FloorProfit;
import model.Shop;

import java.util.List;

public class SampleData {

    private SampleData() {
    }

    public static Floor floor() {
        return new Floor(1, 1, 1, 1000, 100, 20000);
    }

    public static List<Shop> shops() {
        return List.of(
                new Shop(1, 1, "Aino Bakery", 180, "food"),
                new Shop(2, 1, "Sisu Sport", 200, "clothes"),
                new Shop(3, 1, "Kirja & Co", 90, "other"));
    }

    public static List<FloorProfit> report() {
        return List.of(
                new FloorProfit(1, 870, 470, 400, 47000, 20000, 27000, 54.0),
                new FloorProfit(2, 780, 600, 180, 60000, 20000, 40000, 76.9));
    }
}