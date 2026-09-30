package ui;

import i18n.Messages;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;

public class HeaderBar extends HBox {

    private static HeaderBar instance;

    private final Label subtitleLabel = new Label();

    public HeaderBar() {
        instance = this;

        getStyleClass().add("header");
        setPrefHeight(72);
        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(0, 28, 0, 28));
        setSpacing(12);

        Label brand = new Label("MallPlanner");
        brand.getStyleClass().add("header-brand");

        subtitleLabel.getStyleClass().add("header-subtitle");
        subtitleLabel.setText(Messages.get("app.subtitle"));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button english = new Button("English");
        english.getStyleClass().add("lang-button");
        english.setOnAction(e -> switchLanguage("en"));

        Button suomi = new Button("Suomi");
        suomi.getStyleClass().add("lang-button");
        suomi.setOnAction(e -> switchLanguage("fi"));

        getChildren().addAll(brand, subtitleLabel, spacer, english, suomi);
    }

    // Any screen can write its own line here: "Kamppi Center · Floor 1"
    public static void setSubtitle(String text) {
        if (instance != null) {
            instance.subtitleLabel.setText(text);
        }
    }

    private void switchLanguage(String code) {
        Messages.setLanguage(code);
        subtitleLabel.setText(Messages.get("app.subtitle"));
    }
}