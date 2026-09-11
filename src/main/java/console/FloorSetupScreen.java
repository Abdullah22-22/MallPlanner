package console;

import model.Floor;

public class FloorSetupScreen {

    private final ConsoleInput input;
    private final ConsoleOutput output;

    public FloorSetupScreen(ConsoleInput input, ConsoleOutput output) {
        this.input = input;
        this.output = output;
    }

    public double  show(Floor floor) {

        double corridorPercent;

        while (true) {
            corridorPercent = input.readNumber("floor.corridor");

            if (corridorPercent >= 0 && corridorPercent <= 100) {
                break;
            }

            output.showError("error.percentage");
        }

        double  bathrooms;

        while (true) {
            bathrooms = input.readNumber("floor.bathrooms.area");

            if (bathrooms >= 0) {
                break;
            }

            output.showError("error.negative");
        }

        double  restaurants;

        while (true) {
            restaurants = input.readNumber("floor.restaurants.area");

            if (restaurants >= 0) {
                break;
            }

            output.showError("error.negative");
        }

        double  lounges;

        while (true) {
            lounges = input.readNumber("floor.lounges.area");

            if (lounges >= 0) {
                break;
            }

            output.showError("error.negative");
        }

        System.out.println();

        output.show("floor.setup.title");
        output.show("floor.setup.corridor", corridorPercent);
        output.show("floor.setup.bathrooms", bathrooms);
        output.show("floor.setup.restaurants", restaurants);
        output.show("floor.setup.lounges", lounges);
        // Corridor is a percentage of the floor, the rest are areas
        double corridor = floor.getArea() * corridorPercent / 100;
        return corridor + bathrooms + restaurants + lounges;
    }
}