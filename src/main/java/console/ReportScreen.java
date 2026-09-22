package console;

import service.ProfitService.FloorProfit;

import java.util.List;

public class ReportScreen {

    private final ConsoleOutput output;

    public ReportScreen(ConsoleOutput output) {
        this.output = output;
    }

    public void show(List<FloorProfit> floorProfits,
                     FloorProfit bestFloor,
                     double occupancyPercent) {

        if (floorProfits == null || floorProfits.isEmpty()) {
            output.showError("error.report.empty");
            return;
        }

        output.show("report.title");
        output.show("report.header");

        for (FloorProfit floorProfit : floorProfits) {

            double freeArea =
                    floorProfit.getRentableArea()
                            - floorProfit.getUsedByShops();

            output.show(
                    "report.row",
                    floorProfit.getFloorNumber(),
                    floorProfit.getRentableArea(),
                    floorProfit.getUsedByShops(),
                    freeArea,
                    floorProfit.getIncome(),
                    floorProfit.getProfit()
            );
        }

        output.show(
                "report.best.floor",
                bestFloor.getFloorNumber(),
                bestFloor.getProfit()
        );

        output.show(
                "report.occupancy",
                occupancyPercent
        );
    }
}