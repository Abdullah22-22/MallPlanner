import console.ConsoleInput;
import console.ConsoleOutput;
import console.FloorSetupScreen;
import console.MallSetupScreen;
import console.ShopScreen;
import controller.MallController;
import model.Floor;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        ConsoleInput input = new ConsoleInput();
        ConsoleOutput output = new ConsoleOutput();

        MallController controller = new MallController();

        MallSetupScreen mallScreen = new MallSetupScreen(input, output, controller);
        List<Floor> floors = mallScreen.show();

        FloorSetupScreen floorScreen = new FloorSetupScreen(input, output, controller);
        ShopScreen shopScreen = new ShopScreen(input, output, controller);

        // Every floor gets its own services and its own shops
        for (Floor floor : floors) {
            System.out.println();
            System.out.println("=== Floor " + floor.getFloorNumber() + " ===");

            floorScreen.show(floor);
            shopScreen.show(floor);
        }
    }
}