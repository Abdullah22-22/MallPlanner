import console.ConsoleInput;
import console.ConsoleOutput;
import console.MallSetupScreen;

public class Main {

    public static void main(String[] args) {
        ConsoleInput input = new ConsoleInput();
        ConsoleOutput output = new ConsoleOutput();

        MallSetupScreen screen = new MallSetupScreen(input, output);
        screen.show();
    }
}