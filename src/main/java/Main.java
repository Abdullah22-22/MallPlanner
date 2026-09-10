import console.ConsoleInput;
import console.ConsoleOutput;
import console.FloorSetupScreen;
import console.ShopScreen;

public class Main {

    public static void main(String[] args) {

        ConsoleInput input = new ConsoleInput();
        ConsoleOutput output = new ConsoleOutput();

        FloorSetupScreen floorScreen =
                new FloorSetupScreen(input, output);

        ShopScreen shopScreen =
                new ShopScreen(input, output);

        floorScreen.show();
        shopScreen.show();
    }
}
