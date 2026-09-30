package ui;

import javafx.application.Application;

public class UiFactory {

    private UiFactory() {
    }

    // Decides which user interface starts. The console flow is passed in,
    // so this class never needs to know what the console screens do.
    public static void start(String[] args, Runnable consoleApp) {
        if (wantsFx(args)) {
            Application.launch(FxApp.class, args);
        } else {
            consoleApp.run();
        }
    }

    private static boolean wantsFx(String[] args) {
        for (String arg : args) {
            if (arg.equals("--ui=fx") || arg.equals("fx")) {
                return true;
            }
        }
        return false;
    }
}