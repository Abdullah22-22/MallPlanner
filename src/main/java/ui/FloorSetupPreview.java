package ui;

import javafx.application.Application;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class FloorSetupPreview {

    public static void main(String[] args) {
        Application.launch(PreviewApp.class, args);
    }

    public static class PreviewApp extends FxApp {

        @Override
        public void start(Stage stage) {
            super.start(stage);

            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Floor setup preview");
            dialog.setHeaderText("Enter an existing test mall ID");
            dialog.setContentText("Mall ID:");
            dialog.initOwner(stage);

            dialog.showAndWait().ifPresentOrElse(answer -> {
                try {
                    int mallId = Integer.parseInt(answer.trim());

                    if (mallId <= 0) {
                        FxApp.showError("error.floor.setup.context");
                        stage.close();
                        return;
                    }

                    // Preview floor number 1.
                    FxApp.switchScreen(new FloorSetupView(mallId, 1));

                } catch (NumberFormatException exception) {
                    FxApp.showError("error.not.a.number");
                    stage.close();
                }
            }, stage::close);
        }
    }
}