import console.ConsoleInput;
import console.ConsoleOutput;
import console.FloorSetupScreen;
import console.MallSetupScreen;
import console.ShopScreen;
import controller.MallController;
import model.Floor;

public class Main {

    public static void main(String[] args) {
        ConsoleInput input = new ConsoleInput();
        ConsoleOutput output = new ConsoleOutput();

        MallController controller = new MallController();

        MallSetupScreen mallScreen = new MallSetupScreen(input, output, controller);
        mallScreen.show();

        // One floor for now. The list comes from the database later.
        double floorArea = input.readNumber("floor.area");
        Floor floor = new Floor(1, 1, floorArea, 200, 50000);

        FloorSetupScreen floorScreen = new FloorSetupScreen(input, output);
        double services = floorScreen.show(floor);

        ShopScreen shopScreen = new ShopScreen(input, output);
        shopScreen.show(floor, services);
    }
}