package ui;

import controller.MallController;
import i18n.Messages;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.FloorProfit;

import java.util.List;

public class ReportView extends VBox {

    private final MallController controller = new MallController();

    public ReportView() {
        AppState.mallId = 173;   // TODO: remove, US-18 writes this
        setSpacing(20);
        setPadding(new Insets(28));

        List<FloorProfit> profits = loadReport();

        if (profits.isEmpty()) {
            getChildren().add(new Label(Messages.get("error.report.empty")));
            return;
        }

        Label title = new Label(Messages.get("report.title"));
        title.getStyleClass().add("title");

        getChildren().addAll(
                title,
                buildStatCards(profits),
                buildTable(profits),
                buildChart(profits));
    }

    // ---------- the three cards on top ----------

    private HBox buildStatCards(List<FloorProfit> profits) {
        FloorProfit best = controller.bestFloor(profits);
        double occupancy = controller.mallOccupancyPercent(profits);

        // Only a sum of what the service already calculated
        double totalProfit = 0;
        for (FloorProfit p : profits) {
            totalProfit += p.getProfit();
        }

        HBox cards = new HBox(16,
                statCard(Messages.get("report.card.best.floor"),
                        Messages.get("report.card.floor", best.getFloorNumber())),
                statCard(Messages.get("report.card.total.profit"),
                        Messages.get("report.card.eur", totalProfit)),
                statCard(Messages.get("report.card.occupancy"),
                        Messages.get("report.card.percent", occupancy)));

        for (javafx.scene.Node card : cards.getChildren()) {
            HBox.setHgrow(card, Priority.ALWAYS);
        }
        return cards;
    }

    private VBox statCard(String caption, String value) {
        Label captionLabel = new Label(caption);
        captionLabel.getStyleClass().add("stat-caption");

        Label valueLabel = new Label(value);
        valueLabel.getStyleClass().add("stat-value");

        VBox card = new VBox(6, captionLabel, valueLabel);
        card.setPadding(new Insets(20));
        card.getStyleClass().add("card");
        card.setAlignment(Pos.CENTER_LEFT);
        return card;
    }

    // ---------- floor by floor ----------

    private TableView<FloorProfit> buildTable(List<FloorProfit> profits) {
        TableView<FloorProfit> table = new TableView<>();
        table.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        VBox.setVgrow(table, Priority.ALWAYS);

        table.getColumns().add(column(Messages.get("report.column.floor"), "floorNumber"));
        table.getColumns().add(column(Messages.get("report.column.rentable"), "rentableArea"));
        table.getColumns().add(column(Messages.get("report.column.shops"), "usedByShops"));
        table.getColumns().add(column(Messages.get("report.column.income"), "income"));
        table.getColumns().add(column(Messages.get("report.column.profit"), "profit"));

        table.setItems(FXCollections.observableArrayList(profits));
        return table;
    }

    private <T> TableColumn<FloorProfit, T> column(String header, String property) {
        TableColumn<FloorProfit, T> col = new TableColumn<>(header);
        col.setCellValueFactory(new PropertyValueFactory<>(property));
        return col;
    }

    // ---------- profit per floor ----------

    private BarChart<String, Number> buildChart(List<FloorProfit> profits) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        chart.setTitle(Messages.get("report.chart.title"));
        chart.setLegendVisible(false);
        chart.setPrefHeight(280);
        chart.setCategoryGap(120);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (FloorProfit p : profits) {
            series.getData().add(new XYChart.Data<>(
                    Messages.get("report.chart.floor", p.getFloorNumber()),
                    p.getProfit()));
        }

        chart.getData().add(series);
        return chart;
    }

    // ---------- data ----------

    private List<FloorProfit> loadReport() {
        try {
            List<FloorProfit> profits = controller.profitReport(AppState.mallId);

            if (!profits.isEmpty()) {
                return profits;
            }
        } catch (Exception e) {
            // no mall saved yet
        }
        return SampleData.report();   // TODO: remove once MallSetupView (US-18) is done
    }
}