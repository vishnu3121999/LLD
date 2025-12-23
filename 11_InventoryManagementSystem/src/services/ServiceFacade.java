package services;

import database.State;
import models.Product;
import models.WareHouse;

public class ServiceFacade {
    private State state;

    public ServiceFacade(State state) {
        this.state = state;
    }

    public void addProduct(String productId, String warehouseId, int quantity) {
        WareHouse warehouse = state.getWareHouseMap().get(warehouseId);
        if (warehouse != null) {
            warehouse.getInventory().put(productId, 
                warehouse.getInventory().getOrDefault(productId, 0) + quantity);
        }
    }

    public void removeProduct(String productId, String warehouseId, int quantity) {
        WareHouse warehouse = state.getWareHouseMap().get(warehouseId);
        if (warehouse != null && warehouse.getInventory().containsKey(productId)) {
            int currentQuantity = warehouse.getInventory().get(productId);
            if (currentQuantity >= quantity) {
                warehouse.getInventory().put(productId, currentQuantity - quantity);
            }
        }
    }

    public void transferProduct(String productId, String srcWarehouseId, String destWarehouseId, int quantity) {
        WareHouse srcWarehouse = state.getWareHouseMap().get(srcWarehouseId);
        WareHouse destWarehouse = state.getWareHouseMap().get(destWarehouseId);
        
        if (srcWarehouse != null && destWarehouse != null 
            && srcWarehouse.getInventory().containsKey(productId)) {
            int currentQuantity = srcWarehouse.getInventory().get(productId);
            if (currentQuantity >= quantity) {
                srcWarehouse.getInventory().put(productId, currentQuantity - quantity);
                destWarehouse.getInventory().put(productId, 
                    destWarehouse.getInventory().getOrDefault(productId, 0) + quantity);
            }
        }
    }
}
