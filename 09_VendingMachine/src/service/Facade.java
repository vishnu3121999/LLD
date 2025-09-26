package service;

import database.Repo;
import factory.PaymentFactory;
import models.VMState;
import strategy.Payment;

// Rename it to Vending Machine
public class Facade {
    Repo repo;
    VMState state;
    String selectedProduct;
    public Facade(Repo repo) {
        this.repo = repo;
    }


    public void selectProduct(String productId){
        if(state==VMState.IDLE){
            selectedProduct = productId;
        }
        else if(state==VMState.SELECTED)throw new IllegalStateException("Please complete payment or cancel prev txn");
        else if(state==VMState.DISPATCHING)throw new IllegalStateException("Dispatch is in progress, pls wait");
    }

    public boolean pay(String type){
        if(state==VMState.IDLE)throw new IllegalStateException("Select product first");
        if(state==VMState.DISPATCHING)throw new IllegalStateException("Dispatch in progress, pls collect your money back");
        Payment p =  PaymentFactory.get(type);
        boolean success =  p.processPayment();

        if(success){
            dispatchProduct(selectedProduct);
            return true;
        }
        else return false;
    }

    private void dispatchProduct(String selectedProduct) {
        int cnt = repo.getRacks().get(selectedProduct).getProductCount();
        repo.getRacks().get(selectedProduct).setProductCount(cnt-1);

        System.out.println("Dispatched : "+ repo.getRacks().get(selectedProduct).getProductId());
        System.out.println("Please collect product from tray");
    }


}
