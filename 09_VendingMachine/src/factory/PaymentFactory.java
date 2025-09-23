package factory;

import strategy.CreditCardPayment;
import strategy.Payment;

public class PaymentFactory {

    public static Payment get(String type){

        switch(type){
            case "credit" : return new CreditCardPayment();
            default: return new CreditCardPayment();
        }
    }
}
