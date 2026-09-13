package console;

import controller.MallController;
import exception.InvalidInputException;
import exception.NotEnoughSpaceException;
import model.Floor;
import model.Shop;

import java.util.List;


public class ShopScreen {

    private final ConsoleInput input;
    private final ConsoleOutput output;
    private final MallController controller;

    public ShopScreen(ConsoleInput input, ConsoleOutput output,MallController controller) {
        this.input = input;
        this.output = output;
        this.controller = controller;
    }

    public void show(Floor floor) {

        while (true) {

            try {
                double used = controller.usedByShops(floor.getId());
                double free = controller.freeSpace(floor);

                showShopTable(floor);
                output.show("shop.used.space", used);
                output.show("shop.free.space", free);

            } catch (Exception e) {
                output.showError("error.save");
                return;
            }

            int choice = input.readInt("shop.menu");

            if (choice == 1) {
                addShop(floor);

            } else if (choice == 2) {
                editShop(floor);

            } else if (choice == 3) {
                deleteShop(floor);

            } else if (choice == 0) {

                break;

            } else {

                output.showError("error.shop.menu");
            }
        }
    }

    private void addShop(Floor floor) {

        String shopName = readShopName();
        double shopArea = input.readNumber("shop.area");

        try {
            controller.addShop(floor, shopName, shopArea);
            output.show("shop.added", shopName);

        } catch (NotEnoughSpaceException e) {
            output.show("error.not.enough.space", e.getMissingArea());

        } catch (InvalidInputException e) {
            output.showError(e.getMessage());

        } catch (Exception e) {
            output.showError("error.save");
        }
    }

    private void editShop(Floor floor) {

        Shop shop = readShopByNumber(floor);
        if (shop == null) {
            return;
        }

        double newArea = input.readNumber("shop.new.area");

        try {
            controller.editShop(floor, shop, newArea);
            output.show("shop.edited");

        } catch (NotEnoughSpaceException e) {
            output.show("error.not.enough.space", e.getMissingArea());

        } catch (InvalidInputException e) {
            output.showError(e.getMessage());

        } catch (Exception e) {
            output.showError("error.save");
        }
    }

    private void deleteShop(Floor floor) {

        Shop shop = readShopByNumber(floor);
        if (shop == null) {
            return;
        }

        try {
            controller.deleteShop(shop);
            output.show("shop.deleted");

        } catch (Exception e) {
            output.showError("error.save");
        }
    }

    // Shows the shops of the floor as a numbered list
    private void showShopTable(Floor floor) throws Exception {

        List<Shop> shops = controller.shopsOfFloor(floor.getId());

        for (int i = 0; i < shops.size(); i++) {
            Shop shop = shops.get(i);
            output.show("shop.table.row",
                    i + 1, shop.getName(), shop.getArea());
        }
    }

    // Lets the user pick a shop by the number shown in the table
    private Shop readShopByNumber(Floor floor) {

        try {
            List<Shop> shops = controller.shopsOfFloor(floor.getId());

            if (shops.isEmpty()) {
                output.showError("error.no.shops");
                return null;
            }

            int number = input.readInt("shop.number");

            if (number < 1 || number > shops.size()) {
                output.showError("error.shop.number");
                return null;
            }

            return shops.get(number - 1);

        } catch (Exception e) {
            output.showError("error.save");
            return null;
        }
    }

    private String readShopName() {

        while (true) {

            String shopName =
                    input.readText("shop.name");

            if (!shopName.isBlank()) {
                return shopName;
            }

            output.showError("error.shop.name");
        }
    }
}