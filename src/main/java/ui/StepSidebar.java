package ui;

import i18n.Messages;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StepSidebar extends VBox {

    public static final int MALL = 1;
    public static final int FLOOR = 2;
    public static final int AREA = 3;
    public static final int SHOPS = 4;
    public static final int SUGGESTIONS = 5;
    public static final int REPORT = 6;

    private static StepSidebar instance;
    private static int current = MALL;

    private static final String[] KEYS = {
            "step.mall", "step.floor", "step.area",
            "step.shops", "step.suggestions", "step.report"
    };

    public StepSidebar() {
        instance = this;

        getStyleClass().add("sidebar");
        setPrefWidth(256);
        setPadding(new Insets(28, 20, 28, 20));
        setSpacing(8);

        build();
    }

    // The screen that is open now. Everything before it is done.
    public static void setCurrent(int step) {
        current = step;
        if (instance != null) {
            instance.build();
        }
    }

    public static int currentStep() {
        return current;
    }

    private void build() {
        getChildren().clear();

        Label heading = new Label(Messages.get("step.heading"));
        heading.getStyleClass().add("sidebar-heading");
        getChildren().add(heading);

        for (int i = 0; i < KEYS.length; i++) {
            getChildren().add(row(i + 1, Messages.get(KEYS[i])));
        }
    }

    private HBox row(int number, String text) {
        boolean done = number < current;
        boolean active = number == current;

        Label badge = new Label(done ? "\u2713" : String.valueOf(number));
        badge.getStyleClass().add("step-badge");
        badge.setMinSize(28, 28);
        badge.setAlignment(Pos.CENTER);

        Label name = new Label(text);
        name.getStyleClass().add("step-name");

        HBox row = new HBox(12, badge, name);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(10, 14, 10, 14));
        row.getStyleClass().add("step-row");

        if (done) {
            badge.getStyleClass().add("step-badge-done");
            name.getStyleClass().add("step-name-done");
        }
        if (active) {
            row.getStyleClass().add("step-row-active");
            badge.getStyleClass().add("step-badge-active");
            name.getStyleClass().add("step-name-active");
        }
        return row;
    }
}