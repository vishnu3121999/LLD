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

        while(true){
            System.out.println("Select porduct by providing RackId");
            Scanner sc = new Scanner(System.in);

            String productId = sc.next();

            System.out.println("Do Payment, Select:");
            System.out.println("1. Credit Card");
            System.out.println("2. Cash");

            int paymentChoice = sc.nextInt();
            boolean success = switch (paymentChoice){
                case 1 -> facade.pay("credit",productId);
                case 2 -> facade.pay("cash",productId);
                default -> throw new IllegalArgumentException("Invalid Option");
            };

            if(!success){
                continue;
            }





        }
    }
}