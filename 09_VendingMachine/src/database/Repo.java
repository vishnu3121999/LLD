package database;

import models.*;

import java.util.HashMap;
import java.util.Map;

public class Repo {
    Map<String, Rack> racks;
    Map<String, Product> products;

    public Repo(){
        racks = new HashMap<>();
        products = new HashMap<>();
    }

    public void addProduct(Product product) {
        products.put(product.getId(), product);
    }
    public void addRack(Rack rack) {
        racks.put(rack.getId(),rack);
    }
    public Map<String, Product> getProducts() {
        return products;
    }

    public void setProducts(Map<String, Product> products) {
        this.products = products;
    }

    public Map<String, Rack> getRacks() {
        return racks;
    }

    public void setRacks(Map<String, Rack> racks) {
        this.racks = racks;
    }
}
