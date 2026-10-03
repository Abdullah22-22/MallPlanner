package ui;

import controller.MallController;
import exception.InvalidInputException;
import exception.NotEnoughSpaceException;
import i18n.Messages;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import model.Shop;

import java.util.List;

public class ShopView extends VBox {

    private final MallController controller = new MallController();

    private final TextField nameField = new TextField();
    private final TextField areaField = new TextField();
    private final ComboBox<String> categoryBox = new ComboBox<>();

    public ShopView() {
        setSpacing(20);
        setPadding(new Insets(28));

        // TODO: remove once FloorSetupView is done — floor 190 exists in the database
        if (AppState.currentFloor == null) {
            AppState.currentFloor = new model.Floor(190, 1, 1, 1000, 100, 20000);
        }

        getChildren().addAll(buildForm(), buildTable(), buildCapacityBar());
    }

    // ---------- the add form ----------

    private HBox buildForm() {
        nameField.setPromptText(Messages.get("shop.name"));
        nameField.setPrefWidth(280);

        areaField.setPromptText(Messages.get("shop.area"));
        areaField.setPrefWidth(120);

        categoryBox.setItems(FXCollections.observableArrayList(
                "food", "clothes", "other"));
        categoryBox.setValue("food");
        categoryBox.setPrefWidth(160);

        Button addButton = new Button(Messages.get("shop.add"));
        addButton.setOnAction(e -> addShop());

        HBox form = new HBox(12, nameField, areaField, categoryBox, addButton);
        form.setAlignment(Pos.CENTER_LEFT);
        form.getStyleClass().add("card");
        form.setPadding(new Insets(16));
        return form;
    }

    // ---------- the table ----------

    private TableView<Shop> buildTable() {
        TableView<Shop> table = new TableView<>();
        VBox.setVgrow(table, Priority.ALWAYS);

        TableColumn<Shop, String> nameCol =
                new TableColumn<>(Messages.get("shop.table.name"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(320);

        TableColumn<Shop, Double> areaCol =
                new TableColumn<>(Messages.get("shop.table.area"));
        areaCol.setCellValueFactory(new PropertyValueFactory<>("area"));
        areaCol.setPrefWidth(140);

        TableColumn<Shop, String> categoryCol =
                new TableColumn<>(Messages.get("shop.table.category"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        categoryCol.setPrefWidth(180);

        TableColumn<Shop, Void> actionsCol =
                new TableColumn<>(Messages.get("shop.table.actions"));
        actionsCol.setPrefWidth(200);
        actionsCol.setCellFactory(column -> new ActionCell());

        table.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        table.getColumns().addAll(nameCol, areaCol, categoryCol, actionsCol);
        table.setItems(FXCollections.observableArrayList(loadShops()));
        return table;
    }

    // Edit and Delete buttons on every row
    private class ActionCell extends TableCell<Shop, Void> {

        private final Button editButton = new Button(Messages.get("shop.edit"));
        private final Button deleteButton = new Button(Messages.get("shop.delete"));
        private final HBox box = new HBox(8, editButton, deleteButton);

        ActionCell() {
            editButton.setOnAction(e -> editShop(currentShop()));
            deleteButton.setOnAction(e -> deleteShop(currentShop()));
        }

        private Shop currentShop() {
            return getTableView().getItems().get(getIndex());
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            setGraphic(empty ? null : box);
        }
    }

    // ---------- used and free space ----------

    private VBox buildCapacityBar() {
        VBox box = new VBox(8);
        box.getStyleClass().add("card");
        box.setPadding(new Insets(16));

        try {
            double used = controller.usedByShops(AppState.currentFloor.getId());
            double free = controller.freeSpace(AppState.currentFloor);
            double rentable = used + free;

            ProgressBar bar = new ProgressBar(rentable == 0 ? 0 : used / rentable);
            bar.setPrefWidth(760);

            Label label = new Label(
                    Messages.get("shop.capacity", used, rentable, free));

            box.getChildren().addAll(bar, label);

        } catch (Exception e) {
            box.getChildren().add(new Label(Messages.get("error.save")));
        }
        return box;
    }

    // ---------- the three actions ----------

    private void addShop() {
        String name = nameField.getText().trim();

        if (name.isBlank()) {
            FxApp.showError("error.shop.name");
            return;
        }

        try {
            double area = Double.parseDouble(areaField.getText().trim());

            controller.addShop(AppState.currentFloor, name, area,
                    categoryBox.getValue());

            FxApp.switchScreen(new ShopView());

        } catch (NumberFormatException e) {
            FxApp.showError("error.not.a.number");

        } catch (NotEnoughSpaceException e) {
            FxApp.showError("error.not.enough.space", e.getMissingArea());

        } catch (InvalidInputException e) {
            FxApp.showError(e.getMessage());

        } catch (Exception e) {
            FxApp.showError("error.save");
        }
    }

    private void editShop(Shop shop) {
        TextInputDialog dialog = new TextInputDialog(String.valueOf(shop.getArea()));
        dialog.setHeaderText(null);
        dialog.setTitle(Messages.get("shop.edit"));
        dialog.setContentText(Messages.get("shop.new.area"));

        dialog.showAndWait().ifPresent(answer -> {
            try {
                double newArea = Double.parseDouble(answer.trim());

                controller.editShop(AppState.currentFloor, shop, newArea);
                FxApp.switchScreen(new ShopView());

            } catch (NumberFormatException e) {
                FxApp.showError("error.not.a.number");

            } catch (NotEnoughSpaceException e) {
                FxApp.showError("error.not.enough.space", e.getMissingArea());

            } catch (InvalidInputException e) {
                FxApp.showError(e.getMessage());

            } catch (Exception e) {
                FxApp.showError("error.save");
            }
        });
    }

    private void deleteShop(Shop shop) {
        try {
            controller.deleteShop(AppState.currentFloor, shop);
            FxApp.switchScreen(new ShopView());

        } catch (InvalidInputException e) {
            FxApp.showError(e.getMessage());

        } catch (Exception e) {
            FxApp.showError("error.save");
        }
    }

    private List<Shop> loadShops() {
        try {
            return controller.shopsOfFloor(AppState.currentFloor.getId());
        } catch (Exception e) {
            return SampleData.shops();   // no database yet
        }
    }
}