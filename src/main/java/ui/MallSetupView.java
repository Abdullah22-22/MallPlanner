package ui;

import controller.MallController;
import exception.InvalidInputException;
import i18n.Messages;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class MallSetupView extends VBox {

    private final MallController controller = new MallController();

    private final TextField nameField = new TextField();
    private final TextField areaField = new TextField();
    private final TextField floorsField = new TextField("1");

    private final Label stackedLabel = new Label();
    private final GridPane form = new GridPane();
    private final Button saveButton = new Button();

    public MallSetupView() {
        setSpacing(20);
        setPadding(new Insets(28));

        Label title = new Label(Messages.get("mall.setup.title"));

        form.setHgap(20);
        form.setVgap(14);
        form.setPadding(new Insets(20));
        form.getStyleClass().add("card");

        addField(0, "mall.name", nameField);
        addField(1, "mall.total.area", areaField);
        addField(2, "mall.floors", floorsField);

        areaField.textProperty().addListener((o, a, b) -> updateStacked());
        floorsField.textProperty().addListener((o, a, b) -> updateStacked());
        updateStacked();

        saveButton.setText(Messages.get("mall.save"));
        saveButton.setOnAction(event -> saveMall());

        getChildren().addAll(title, form, stackedLabel, saveButton);
    }

    private void addField(int row, String key, TextField field) {
        field.setPromptText(Messages.get(key));
        field.setPrefWidth(260);
        form.add(new Label(Messages.get(key)), 0, row);
        form.add(field, 1, row);
    }

    private void updateStacked() {
        double stacked = 0;
        try {
            double area = Double.parseDouble(areaField.getText().trim());
            int floors = Integer.parseInt(floorsField.getText().trim());
            if (area >= 0 && floors > 0 && Double.isFinite(area)) {
                stacked = area * floors;
            }
        } catch (NumberFormatException ignored) {
            // keep 0
        }
        stackedLabel.setText(Messages.get("mall.stacked.space") + " " + stacked);
    }

    private void saveMall() {
        saveButton.setDisable(true);

        try {
            String name = nameField.getText().trim();
            if (name.isBlank()) {
                FxApp.showError("error.mall.name.empty");
                return;
            }

            double totalArea = Double.parseDouble(areaField.getText().trim());
            if (!Double.isFinite(totalArea)) {
                throw new NumberFormatException();
            }
            if (totalArea < 0) {
                FxApp.showError("error.area.negative");
                return;
            }

            int floors = Integer.parseInt(floorsField.getText().trim());
            if (floors <= 0) {
                FxApp.showError("error.floors");
                return;
            }

            int mallId = controller.saveMall(name, totalArea);

            AppState.mallId = mallId;
            AppState.floorCount = floors;
            FxApp.switchScreen(() -> new FloorSetupView(mallId, 1));

        } catch (NumberFormatException exception) {
            FxApp.showError("error.not.a.number");

        } catch (InvalidInputException exception) {
            FxApp.showError(exception.getMessage());

        } catch (Exception exception) {
            FxApp.showError("error.save");

        } finally {
            saveButton.setDisable(false);
        }
    }
}
