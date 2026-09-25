package service;

import exception.InvalidInputException;
import exception.NotEnoughSpaceException;
import model.Floor;
import model.Shop;

import java.util.List;

public class ShopService {

    private final AreaService areaService = new AreaService();

    // Sum of the areas of all shops in the list
    public double usedByShops(List<Shop> shops) {
        double used = 0;
        for (Shop shop : shops) {
            used += shop.getArea();
        }
        return used;
    }

    // Free space of the floor with this shop list
    public double freeSpace(Floor floor, double services, List<Shop> shops) {
        return areaService.freeSpace(floor, services, usedByShops(shops));
    }

    // Adds a shop if it fits. Returns the free space left after it.
    public double addShop(Floor floor, double services,
                          List<Shop> shops, double newShopArea) {

        double free = freeSpace(floor, services, shops);
        checkNewArea(newShopArea, free);
        return free - newShopArea;
    }

    // Changes the area of a shop. Returns the free space left after it.
    public double editShop(Floor floor, double services,
                           List<Shop> shops, Shop shop, double newArea) {

        Shop oldShop = findShop(shops, shop);

        // Free space as if the old shop was not there
        double free = freeSpace(floor, services, shops) + oldShop.getArea();
        checkNewArea(newArea, free);
        return free - newArea;
    }

    // Deletes a shop. The free space grows back by the shop area.
    public double deleteShop(Floor floor, double services,
                             List<Shop> shops, Shop shop) {

        Shop oldShop = findShop(shops, shop);
        return freeSpace(floor, services, shops) + oldShop.getArea();
    }

    // Area must be positive and must fit in the free space
    private void checkNewArea(double area, double free) {
        if (area <= 0) {
            throw new InvalidInputException("error.shop.area.zero");
        }
        if (area > free) {
            throw new NotEnoughSpaceException("error.not.enough.space", area - free);
        }
    }

    // Finds the shop in the list by its id
    private Shop findShop(List<Shop> shops, Shop shop) {
        if (shop != null) {
            for (Shop s : shops) {
                if (s.getId() == shop.getId()) {
                    return s;
                }
            }
        }
        throw new InvalidInputException("error.shop.not.found");
    }
}