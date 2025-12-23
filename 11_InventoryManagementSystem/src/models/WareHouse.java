package models;

import java.util.HashMap;
import java.util.Map;

public class WareHouse {
    String id;
//    Map<productId,quantity> inventory;
    Map<String,Integer> inventory;
//    String location;

    public WareHouse(String id) {
        this.id = id;
        this.inventory = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }
}
