package ui;

import i18n.Messages;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FxApp extends Application {

    private static BorderPane root;
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        root = new BorderPane();

        Scene scene = new Scene(root, 1280, 800);
        scene.getStylesheets().add(
                FxApp.class.getResource("/styles.css").toExternalForm());

        stage.setTitle("MallPlanner");
        stage.setScene(scene);
        stage.show();
    }

    // Every screen calls this to move to the next one
    public static void switchScreen(Pane screen) {
        root.setCenter(screen);
    }

    // Every screen calls this with a message key from a service
    public static void showError(String messageKey) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(Messages.get(messageKey));
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}