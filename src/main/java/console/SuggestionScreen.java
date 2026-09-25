package console;

import model.Floor;
import model.Suggestion;
import service.SuggestionService;
import i18n.Messages;

import java.util.List;

public class SuggestionScreen {

    private final ConsoleInput input;
    private final ConsoleOutput output;
    private final SuggestionService suggestionService;

    public SuggestionScreen(ConsoleInput input, ConsoleOutput output) {
        this.input = input;
        this.output = output;
        this.suggestionService = new SuggestionService();
    }

    public void show(Floor floor, double freeArea) {

        try {
            output.show("suggestion.free.area", freeArea);

            List<Suggestion> options =
                    suggestionService.buildOptions(
                            freeArea,
                            floor.getRentPrice()
                    );

            options = suggestionService.sortAndMarkBest(options);

            if (options.isEmpty()) {
                output.show("suggestion.no.options");
                return;
            }

            for (int i = 0; i < options.size(); i++) {

                Suggestion suggestion = options.get(i);

                output.show(
                        "suggestion.option",
                        i + 1,
                        suggestion.getShopCount(),
                        suggestion.getShopSize(),
                        suggestion.getTotalArea(),
                        suggestion.getIncome(),
                        suggestion.isBest() ? Messages.get("suggestion.best") : ""
                );
            }

            int choice = input.readInt("suggestion.choose");

            if (choice == 0) {
                return;
            }

            if (choice < 1 || choice > options.size()) {
                output.showError("error.suggestion.choice");
                return;
            }

            Suggestion selected = options.get(choice - 1);

            output.show(
                    "suggestion.selected",
                    selected.getShopCount(),
                    selected.getShopSize(),
                    selected.getTotalArea()
            );

        } catch (Exception e) {
            output.showError("error.suggestion");
        }
    }
}