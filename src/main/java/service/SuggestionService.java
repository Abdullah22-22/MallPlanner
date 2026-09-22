package service;

import exception.InvalidInputException;
import model.Suggestion;

import java.util.ArrayList;
import java.util.List;

public class SuggestionService {

    // Standard shop sizes the mall suggests filling the free space with
    private static final double[] SHOP_SIZES = {30, 20, 60};

    // From the free area, build options (2 x 30, 4 x 20, 1 x 60)
    public List<Suggestion> buildOptions(double freeArea, double rentPrice) {
        if (freeArea < 0) {
            throw new InvalidInputException("error.area.negative");
        }
        if (rentPrice <= 0) {
            throw new InvalidInputException("error.price.zero");
        }

        List<Suggestion> options = new ArrayList<>();
        for (double size : SHOP_SIZES) {
            int count = (int) (freeArea / size);
            if (count >= 1) {
                double totalIncome = count * size * rentPrice;
                double areaLeft = freeArea - (count * size);
                options.add(new Suggestion(count, size, areaLeft, totalIncome));
            }
        }
        return options;
    }

    // Sort the options by income and mark the best one
    public List<Suggestion> sortAndMarkBest(List<Suggestion> options) {
        if (options == null) {
            throw new InvalidInputException("error.options.null");
        }

        List<Suggestion> sorted = new ArrayList<>(options);
        sorted.sort((a, b) -> Double.compare(b.getIncome(), a.getIncome()));


        for (Suggestion s : sorted) {
            s.setBest(false);
        }
        if (!sorted.isEmpty()) {
            sorted.get(0).setBest(true);
        }
        return sorted;
    }

}