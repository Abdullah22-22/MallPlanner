package ui;

import controller.MallController;
import exception.InvalidInputException;
import i18n.Messages;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import model.Suggestion;

import java.util.List;

public class SuggestionView extends VBox {

    private final MallController controller = new MallController();

    public SuggestionView() {
        setSpacing(20);
        setPadding(new Insets(28));
        StepSidebar.setCurrent(StepSidebar.SUGGESTIONS);

        if (AppState.currentFloor == null) {
            AppState.currentFloor = SampleData.floor();
        }

        try {
            double free = controller.freeSpace(AppState.currentFloor);
            List<Suggestion> options =
                    controller.suggestionsForFloor(AppState.currentFloor);

            getChildren().add(buildHeading(free));

            if (options.isEmpty()) {
                getChildren().add(new Label(Messages.get("suggestion.no.options")));
            } else {
                for (Suggestion option : options) {
                    getChildren().add(buildCard(option));
                }
                getChildren().add(new Label(Messages.get("suggestion.naming.note")));
            }

            getChildren().add(buildButtons());

        } catch (InvalidInputException e) {
            FxApp.showError(e.getMessage());
        } catch (Exception e) {
            FxApp.showError("error.suggestion");
        }
    }

    // ---------- heading ----------

    private VBox buildHeading(double free) {
        Label title = new Label(Messages.get("suggestion.free.area", free));
        title.getStyleClass().add("title");

        Label subtitle = new Label(Messages.get("suggestion.intro"));

        return new VBox(6, title, subtitle);
    }

    // ---------- one option ----------

    private HBox buildCard(Suggestion option) {
        Label shape = new Label(Messages.get(
                "suggestion.shape",
                option.getShopCount(),
                option.getShopSize()));
        shape.getStyleClass().add("suggestion-shape");

        Label used = new Label(Messages.get(
                "suggestion.used",
                option.getTotalArea(),
                option.getAreaLeft()));

        VBox left = new VBox(4, shape, used);
        left.setPrefWidth(260);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label income = new Label(Messages.get("suggestion.income", option.getIncome()));
        income.getStyleClass().add("suggestion-income");

        VBox right = new VBox(4, income);
        right.setAlignment(Pos.CENTER_RIGHT);

        if (option.isBest()) {
            Label bestTag = new Label(Messages.get("suggestion.best"));
            bestTag.getStyleClass().add("suggestion-best-tag");
            right.getChildren().add(bestTag);
        }

        Button apply = new Button(Messages.get("suggestion.apply"));
        apply.setOnAction(e -> apply(option));

        HBox card = new HBox(16, left, spacer, right, apply);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(20));
        card.getStyleClass().add("card");

        if (option.isBest()) {
            card.getStyleClass().add("card-best");
        }
        return card;
    }

    // ---------- buttons at the bottom ----------

    private HBox buildButtons() {
        Button report = new Button(Messages.get("suggestion.see.report"));
        report.setOnAction(e -> FxApp.switchScreen(ReportView::new));

        Button skip = new Button(Messages.get("suggestion.skip"));
        skip.getStyleClass().add("lang-button");
        // Skip changes nothing on the floor
        skip.setOnAction(e -> FxApp.switchScreen(ReportView::new));

        return new HBox(12, report, skip);
    }

    // ---------- apply ----------

    // Creates the shops of this option, named Shop 1, Shop 2 and so on
    private void apply(Suggestion option) {
        try {
            int existing = controller
                    .shopsOfFloor(AppState.currentFloor.getId())
                    .size();

            for (int i = 1; i <= option.getShopCount(); i++) {
                controller.addShop(
                        AppState.currentFloor,
                        Messages.get("suggestion.shop.name") + " " + (existing + i),
                        option.getShopSize(),
                        Messages.get("suggestion.shop.category"));
            }

            FxApp.switchScreen(ShopView::new);

        } catch (InvalidInputException e) {
            FxApp.showError(e.getMessage());
        } catch (Exception e) {
            FxApp.showError("error.save");
        }
    }
}