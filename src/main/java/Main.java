
import console.ConsoleInput;
import console.ConsoleOutput;
import console.MallSetupScreen;
import controller.MallController;

public class Main {

    public static void main(String[] args) {
        ConsoleInput input = new ConsoleInput();
        ConsoleOutput output = new ConsoleOutput();

        MallController controller = new MallController();
        MallSetupScreen screen = new MallSetupScreen(input, output, controller);
        screen.show();
    }
}