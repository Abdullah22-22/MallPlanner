import console.ConsoleInput;
import console.ConsoleOutput;
import console.FloorSetupScreen;
import console.MallSetupScreen;
import console.ShopScreen;
import console.SuggestionScreen;
import console.ReportScreen;

import controller.MallController;

import model.Floor;

import model.FloorProfit;
import service.ProfitService;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        ConsoleInput input = new ConsoleInput();
        ConsoleOutput output = new ConsoleOutput();

        MallController controller = new MallController();

        MallSetupScreen mallScreen =
                new MallSetupScreen(input, output, controller);

        List<Floor> floors = mallScreen.show();

        FloorSetupScreen floorScreen =
                new FloorSetupScreen(input, output, controller);

        ShopScreen shopScreen =
                new ShopScreen(input, output, controller);

        SuggestionScreen suggestionScreen =
                new SuggestionScreen(input, output);

        ReportScreen reportScreen =
                new ReportScreen(output);

        ProfitService profitService =
                new ProfitService();

        List<FloorProfit> floorProfits =
                new ArrayList<>();

        // Setup each floor, add shops, show suggestions
        for (Floor floor : floors) {

            System.out.println();
            System.out.println(
                    "=== Floor " + floor.getFloorNumber() + " ==="
            );

            floorScreen.show(floor);
            shopScreen.show(floor);

            try {

                double services =
                        controller.totalServices(floor.getId());

                double usedByShops =
                        controller.usedByShops(floor.getId());

                double freeArea =
                        controller.freeSpace(floor);

                // Show suggestions after shops
                suggestionScreen.show(floor, freeArea);

                // Calculate report information
                FloorProfit floorProfit =
                        profitService.calculateProfit(
                                floor,
                                services,
                                usedByShops
                        );

                floorProfits.add(floorProfit);

            } catch (Exception e) {

                output.showError("error.save");
            }
        }

        // Show final mall report
        if (!floorProfits.isEmpty()) {

            FloorProfit bestFloor =
                    profitService.bestFloor(floorProfits);

            double occupancy =
                    profitService.mallOccupancyPercent(
                            floorProfits
                    );

            reportScreen.show(
                    floorProfits,
                    bestFloor,
                    occupancy
            );
        }
    }
}