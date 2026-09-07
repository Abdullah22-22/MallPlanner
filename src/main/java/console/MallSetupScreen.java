package console;

import controller.MallController;
import exception.InvalidInputException;

public class MallSetupScreen {

    private final ConsoleInput input;
    private final ConsoleOutput output;
    private final MallController controller;

    public MallSetupScreen(ConsoleInput input, ConsoleOutput output,
                           MallController controller) {
        this.input = input;
        this.output = output;
        this.controller = controller;
    }

    public void show() {

        String mallName;

        while (true) {
            mallName = input.readText("mall.name");

            if (!mallName.isBlank()) {
                break;
            }

            System.out.println("[!] Mall name cannot be empty");
        }

        double totalArea;

        while (true) {
            totalArea = input.readNumber("mall.total.area");

            if (totalArea >= 0) {
                break;
            }

            output.showError("error.area.negative");
        }

        int floors;

        while (true) {
            floors = input.readInt("mall.floors");

            if (floors > 0) {
                break;
            }

            output.showError("error.floors");
        }

        double perFloor = controller.areaPerFloor(mallName, totalArea, floors);
        System.out.println("Suggested area per floor: " + perFloor);

        int mallId;
        try {
            mallId = controller.saveMall(mallName, totalArea);
        } catch (Exception e) {
            output.showError("error.save");
            return;
        }
        System.out.println();

        for (int i = 1; i <= floors; i++) {

            System.out.println("Floor " + i);

            double floorArea;

            while (true) {
                floorArea = input.readNumber("floor.area");

                if (floorArea >= 0) {
                    break;
                }

                output.showError("error.area.negative");
            }

            double rentPrice = input.readNumber("floor.rent");
            double cost = input.readNumber("floor.cost");

            try {
                controller.addFloor(mallId, i, floorArea, rentPrice, cost);
            } catch (InvalidInputException  e) {
                output.showError(e.getMessage());
                i--;
                System.out.println();
                continue;
            } catch (Exception e) {
                output.showError("error.save");
                i--;
                System.out.println();
                continue;
            }

            System.out.println(
                    "Floor " + i +
                            " -> Area: " + floorArea +
                            ", Rent: " + rentPrice +
                            ", Cost: " + cost
            );

            System.out.println();
        }

        System.out.println("Mall: " + mallName);
        System.out.println("Total area: " + totalArea);
        System.out.println("Floors: " + floors);
    }
}