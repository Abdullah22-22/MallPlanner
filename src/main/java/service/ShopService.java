package service;

import exception.InvalidInputException;
import exception.NotEnoughSpaceException;
import model.Floor;

public class ShopService{

    private  final  AreaService areaService = new AreaService();

    // Adds a shop if it fits. Returns the free space that is left after it.
    public double addShop(Floor floor, double services,
                          double usedByShops, double newShopArea){

        if (newShopArea <= 0){
            throw new InvalidInputException("error.shop.area.zero");
        }

        double free = areaService.freeSpace(floor, services, usedByShops);

        if(newShopArea > free){
            double missing = newShopArea - free;
            throw new NotEnoughSpaceException("error.not.enough.space", missing);
        }
        return free - newShopArea;
    }

    // Changes the area of a shop that already exists
    public double editShop(Floor floor, double services,
                           double usedByShops, double oldArea, double newArea) {

        // Remove the old shop first, then add it with the new area
        return addShop(floor, services, usedByShops - oldArea, newArea);

    }
}