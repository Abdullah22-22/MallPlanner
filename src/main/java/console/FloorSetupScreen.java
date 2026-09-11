package console;

public class FloorSetupScreen {

    private final ConsoleInput input;
    private final ConsoleOutput output;

    public FloorSetupScreen(ConsoleInput input, ConsoleOutput output) {
        this.input = input;
        this.output = output;
    }

    public void show() {

        double corridorPercent;

        while (true) {
            corridorPercent = input.readNumber("floor.corridor");

            if (corridorPercent >= 0 && corridorPercent <= 100) {
                break;
            }

            output.showError("error.percentage");
        }

        int bathrooms;

        while (true) {
            bathrooms = input.readInt("floor.bathrooms");

            if (bathrooms >= 0) {
                break;
            }

            output.showError("error.negative");
        }

        int restaurants;

        while (true) {
            restaurants = input.readInt("floor.restaurants");

            if (restaurants >= 0) {
                break;
            }

            output.showError("error.negative");
        }

        int lounges;

        while (true) {
            lounges = input.readInt("floor.lounges");

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
    }
}