package console;

public class ShopScreen {

    private final ConsoleInput input;
    private final ConsoleOutput output;

    public ShopScreen(ConsoleInput input, ConsoleOutput output) {
        this.input = input;
        this.output = output;
    }

    public void show() {

        String shopName;

        while (true) {
            shopName = input.readText("shop.name");

            if (!shopName.isBlank()) {
                break;
            }

            output.showError("error.shop.name");
        }

        double shopArea;

        while (true) {
            shopArea = input.readNumber("shop.area");

            if (shopArea > 0) {
                break;
            }

            output.showError("error.shop.area");
        }

        System.out.println();

        System.out.println("Shop:");
        System.out.println("Name: " + shopName);
        System.out.println("Area: " + shopArea + " m2");
    }
}