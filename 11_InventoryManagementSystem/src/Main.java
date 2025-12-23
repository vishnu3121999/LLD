import models.Product;
import models.WareHouse;
import services.ServiceFacade;
import database.State;

public class Main {
    public static void main(String[] args) {
        // Initialize state
        State state = new State();
        ServiceFacade serviceFacade = new ServiceFacade(state);

        // Create products
        Product product1 = new Product("P001");
        Product product2 = new Product("P002");
        Product product3 = new Product("P003");

        // Create warehouses
        WareHouse warehouse1 = new WareHouse("WH001");
        WareHouse warehouse2 = new WareHouse("WH002");
        WareHouse warehouse3 = new WareHouse("WH003");

        // Add products and warehouses to state
        state.getProductMap().put(product1.getId(), product1);
        state.getProductMap().put(product2.getId(), product2);
        state.getProductMap().put(product3.getId(), product3);

        state.getWareHouseMap().put(warehouse1.getId(), warehouse1);
        state.getWareHouseMap().put(warehouse2.getId(), warehouse2);
        state.getWareHouseMap().put(warehouse3.getId(), warehouse3);

        // Call APIs
        System.out.println("=== Adding products to warehouses ===");
        serviceFacade.addProduct("P001", "WH001", 100);
        serviceFacade.addProduct("P002", "WH001", 50);
        serviceFacade.addProduct("P001", "WH002", 75);
        serviceFacade.addProduct("P003", "WH002", 200);

        System.out.println("WH001 Inventory: " + warehouse1.getInventory());
        System.out.println("WH002 Inventory: " + warehouse2.getInventory());
        System.out.println("WH003 Inventory: " + warehouse3.getInventory());

        System.out.println("\n=== Removing products from warehouse ===");
        serviceFacade.removeProduct("P001", "WH001", 25);
        System.out.println("WH001 Inventory after removal: " + warehouse1.getInventory());

        System.out.println("\n=== Transferring products between warehouses ===");
        serviceFacade.transferProduct("P002", "WH001", "WH003", 30);
        System.out.println("WH001 Inventory after transfer: " + warehouse1.getInventory());
        System.out.println("WH003 Inventory after transfer: " + warehouse3.getInventory());

        System.out.println("\n=== Final state ===");
        System.out.println("WH001 Inventory: " + warehouse1.getInventory());
        System.out.println("WH002 Inventory: " + warehouse2.getInventory());
        System.out.println("WH003 Inventory: " + warehouse3.getInventory());
    }
}

