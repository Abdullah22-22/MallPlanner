package console;

import exception.NotEnoughSpaceException;
import model.Floor;
import service.AreaService;
import service.ShopService;

public class ShopScreen {

    private final ConsoleInput input;
    private final ConsoleOutput output;
    private final ShopService shopService = new ShopService();
    private final AreaService areaService = new AreaService();

    public ShopScreen(ConsoleInput input, ConsoleOutput output) {
        this.input = input;
        this.output = output;
    }

    public void show(Floor floor, double services) {

        double usedByShops = 0;

        while (true) {

            double freeSpace =
                    areaService.freeSpace(floor, services, usedByShops);

            output.show("shop.used.space", usedByShops);
            output.show("shop.free.space", freeSpace);

            int choice = input.readInt("shop.menu");

            if (choice == 1) {

                String shopName = readShopName();
                double shopArea = input.readNumber("shop.area");

                try {
                    shopService.addShop(
                            floor,
                            services,
                            usedByShops,
                            shopArea
                    );

                    usedByShops += shopArea;

                    output.show("shop.added", shopName);

                } catch (NotEnoughSpaceException e) {
                    output.show("error.not.enough.space",
                            e.getMissingArea());
                }

            } else if (choice == 2) {

                double oldArea =
                        input.readNumber("shop.old.area");

                double newArea =
                        input.readNumber("shop.new.area");

                try {
                    shopService.editShop(
                            floor,
                            services,
                            usedByShops,
                            oldArea,
                            newArea
                    );

                    usedByShops =
                            usedByShops - oldArea + newArea;

                    output.show("shop.edited");

                } catch (NotEnoughSpaceException e) {
                    output.show("error.not.enough.space",
                            e.getMissingArea());
                }

            } else if (choice == 3) {

                double shopArea =
                        input.readNumber("shop.delete.area");

                if (shopArea > 0 && shopArea <= usedByShops) {
                    usedByShops -= shopArea;
                    output.show("shop.deleted");
                } else {
                    output.showError("error.shop.delete");
                }

            } else if (choice == 0) {

                break;

            } else {

                output.showError("error.shop.menu");
            }
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