package strategy;

import java.util.Random;

public class CashPayment implements Payment{
    @Override
    public boolean processPayment() {

        System.out.println("Insert Cash");

        Random random = new Random();
        int amountReceived = random.nextInt(100);

        return true;
    }
}
