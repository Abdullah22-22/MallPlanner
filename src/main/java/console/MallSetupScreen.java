package console;

public class MallSetupScreen {

    private final ConsoleInput input;
    private final ConsoleOutput output;

    public MallSetupScreen(ConsoleInput input, ConsoleOutput output) {
        this.input = input;
        this.output = output;
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