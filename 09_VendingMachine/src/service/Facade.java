package service;

import database.Repo;
import factory.PaymentFactory;
import strategy.Payment;

public class Facade {
    Repo repo;

    public Facade(Repo repo) {
        this.repo = repo;
    }


    public void selectProduct(String id){

    }

    public boolean pay(String type,String selectedProduct){
        Payment p =  PaymentFactory.get(type);
        boolean success =  p.pay();

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
