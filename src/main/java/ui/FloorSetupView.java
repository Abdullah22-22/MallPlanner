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
import model.Floor;

public class FloorSetupView extends VBox {

    private final MallController controller = new MallController();

    private final int mallId;
    private final int floorNumber;

    private final TextField areaField = new TextField();
    private final TextField rentField = new TextField();
    private final TextField costField = new TextField();
    private final TextField corridorField = new TextField("15");
    private final TextField bathroomsField = new TextField("0");
    private final TextField restaurantsField = new TextField("0");
    private final TextField loungesField = new TextField("0");

    private final GridPane form = new GridPane();
    private final Button saveButton = new Button();

    // Keep progress if a database save fails, so Retry does not
    // create another floor or repeat already saved service areas.
    private Floor savedFloor;
    private double[] serviceSizes;
    private int nextServiceIndex;

    private final String[] serviceTypes = {
            "corridor", "bathrooms", "restaurants", "lounges"
    };

    public FloorSetupView(int mallId, int floorNumber) {
        this.mallId = mallId;
        this.floorNumber = floorNumber;

        setSpacing(20);
        setPadding(new Insets(28));

        Label title = new Label(
                Messages.get("floor.setup.title") + " "
                        + Messages.get("floor.number", floorNumber)
        );

        form.setHgap(20);
        form.setVgap(14);
        form.setPadding(new Insets(20));
        form.getStyleClass().add("card");

        addField(0, "floor.area", areaField);
        addField(1, "floor.rent", rentField);
        addField(2, "floor.cost", costField);
        addField(3, "floor.corridor", corridorField);
        addField(4, "floor.bathrooms.area", bathroomsField);
        addField(5, "floor.restaurants.area", restaurantsField);
        addField(6, "floor.lounges.area", loungesField);

        saveButton.setText(Messages.get("floor.save"));
        saveButton.setOnAction(event -> saveFloor());

        getChildren().addAll(title, form, saveButton);
    }

    private void addField(int row, String key, TextField field) {
        field.setPromptText(Messages.get(key));
        field.setPrefWidth(260);
        form.add(new Label(Messages.get(key)), 0, row);
        form.add(field, 1, row);
    }

    private double readNumber(TextField field) {
        double value = Double.parseDouble(field.getText().trim());

        if (!Double.isFinite(value)) {
            throw new NumberFormatException();
        }

        return value;
    }

    private void requireNonNegative(double value) {
        if (value < 0) {
            throw new InvalidInputException("error.negative");
        }
    }

    private void saveFloor() {
        saveButton.setDisable(true);

        try {
            if (savedFloor == null) {
                if (mallId <= 0 || floorNumber <= 0) {
                    FxApp.showError("error.floor.setup.context");
                    return;
                }

                double area = readNumber(areaField);
                double rent = readNumber(rentField);
                double cost = readNumber(costField);
                double corridorPercent = readNumber(corridorField);
                double bathrooms = readNumber(bathroomsField);
                double restaurants = readNumber(restaurantsField);
                double lounges = readNumber(loungesField);

                if (area <= 0) {
                    throw new InvalidInputException("error.area.zero");
                }

                if (rent <= 0) {
                    throw new InvalidInputException("error.price.zero");
                }

                requireNonNegative(cost);
                requireNonNegative(bathrooms);
                requireNonNegative(restaurants);
                requireNonNegative(lounges);

                if (corridorPercent < 0 || corridorPercent > 100) {
                    throw new InvalidInputException("error.percentage");
                }

                double corridor = area * (corridorPercent / 100);
                double totalServices =
                        corridor + bathrooms + restaurants + lounges;

                if (!Double.isFinite(totalServices)
                        || totalServices > area) {
                    FxApp.showError("error.services.big");
                    return;
                }

                // All input checks happen before saving anything.
                serviceSizes = new double[]{
                        corridor, bathrooms, restaurants, lounges
                };

                savedFloor = controller.addFloor(
                        mallId, floorNumber, area, rent, cost
                );

                // Preserve the values belonging to this saved floor
                // if a later service-area save needs to be retried.
                form.setDisable(true);
            }

            while (nextServiceIndex < serviceTypes.length) {
                controller.addServiceArea(
                        savedFloor.getId(),
                        serviceTypes[nextServiceIndex],
                        serviceSizes[nextServiceIndex]
                );
                nextServiceIndex++;
            }

            AppState.mallId = mallId;
            AppState.currentFloor = savedFloor;
            FxApp.switchScreen(new ShopView());

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