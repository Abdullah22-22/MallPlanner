package ui;

import i18n.Messages;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.function.Supplier;

public class FxApp extends Application {

    private static BorderPane root;
    private static Stage stage;
    // How to rebuild whatever is on screen, for a language change
    private static Supplier<Pane> currentScreen;

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        root = new BorderPane();

        Scene scene = new Scene(root, 1280, 800);
        scene.getStylesheets().add(
                FxApp.class.getResource("/styles.css").toExternalForm());

        root.setTop(new HeaderBar());
        stage.setTitle("MallPlanner");
        stage.setScene(scene);
        stage.show();
    }

    // Every screen calls this to move to the next one
    public static void switchScreen(Pane screen) {
        currentScreen = null;
        root.setCenter(screen);
    }

    // Same, but remembers how to build the screen again.
    // A screen passed this way comes back in the new language
    // when the user presses English or Suomi.
    public static void switchScreen(Supplier<Pane> screenFactory) {
        currentScreen = screenFactory;
        root.setCenter(screenFactory.get());
    }

    // Builds the current screen again, so its labels read Messages anew
    public static void redrawCurrentScreen() {
        if (currentScreen != null) {
            root.setCenter(currentScreen.get());
        }
    }

    // Every screen calls this with a message key from a service
    public static void showError(String messageKey) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(Messages.get(messageKey));
        alert.showAndWait();
    }

    // Same as above, but for messages that carry numbers
    public static void showError(String messageKey, Object... args) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(Messages.get(messageKey, args));
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}