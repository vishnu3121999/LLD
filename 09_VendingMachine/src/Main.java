import database.Repo;
import factory.PaymentFactory;
import models.Product;
import models.Rack;
import service.Facade;
import strategy.Payment;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Product snicker = new Product("1","snicker",20);
        Product drink = new Product("2","drink",30);

        Rack rack1 = new Rack("100","1",5);
        Rack rack2 = new Rack("200","2",10);

        Repo repo = new Repo();
        repo.addProduct(snicker);
        repo.addProduct(drink);
        repo.addRack(rack1);
        repo.addRack(rack2);

        Facade facade = new Facade(repo);


        // Happy Flow :  select -> pay -> collect
        // Happy Flow :  select -> cancel
        // Happy Flow :  cancel
        // Unsupported Flow :  select -> pay -> cancel   (cancelling not allowed after payment )

        // Unhappy flows are difficult to simulate. Need to create multiple threads for diff req to simulate the exception behaviour
        // Unhappy flow1 : pay
        // Unhappy flow1 : select -> pay -> pay
        // Unhappy flow1 : select -> pay -> select
        // Unhappy flow1 : select -> select
            try{

            }
            catch (Exception e){
                System.out.println("Exception:"+e.getMessage());
            }







    }
}