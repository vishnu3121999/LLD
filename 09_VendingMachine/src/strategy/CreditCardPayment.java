package strategy;

import java.util.Random;

public class CreditCardPayment implements Payment{

    @Override
    public boolean pay() {
        Random random = new Random();

        boolean paymentStatus = random.nextBoolean();
        return paymentStatus;
    }
}
