package service;

import exception.InvalidInputException;

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
                options.add(new Suggestion(count, size, totalIncome));
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
        sorted.sort((a, b) -> Double.compare(b.getTotalIncome(), a.getTotalIncome()));

        for (Suggestion s : sorted) {
            s.setBest(false);
        }
        if (!sorted.isEmpty()) {
            sorted.get(0).setBest(true);
        }
        return sorted;
    }

    // One suggested way to split the free space into equal-size shops.
    // TODO: if the model package owner already has/plans a model.Suggestion class,
    // delete this nested class and switch to importing that one instead.
    public static class Suggestion {
        private final int shopCount;
        private final double shopSize;
        private final double totalIncome;
        private boolean best;

        public Suggestion(int shopCount, double shopSize, double totalIncome) {
            this.shopCount = shopCount;
            this.shopSize = shopSize;
            this.totalIncome = totalIncome;
        }

        public int getShopCount() { return shopCount; }
        public double getShopSize() { return shopSize; }
        public double getTotalArea() { return shopCount * shopSize; }
        public double getTotalIncome() { return totalIncome; }
        public boolean isBest() { return best; }
        public void setBest(boolean best) { this.best = best; }
    }
}