package strategy;

import java.util.Random;

public class CreditCardPayment implements Payment{

    @Override
    public boolean pay() {

        System.out.println("Scan QR & Pay");
        Random random = new Random();

        boolean paymentStatus = random.nextBoolean();
        return paymentStatus;
    }
}
