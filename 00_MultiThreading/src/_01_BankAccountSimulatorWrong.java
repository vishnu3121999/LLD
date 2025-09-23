
/**
 * Bank Account Simulator

 * Description:
 * Two or more threads perform deposits and withdrawals on the same BankAccount object.

 * Task:
 *  - Implement deposit(int amount) and withdraw(int amount) methods.
 *  - Protect balance updates with synchronized so that the final balance is always correct.

 * Try it yourself:
 *  Create a test that spawns 10 deposit threads (each adding ₹1000 ten times)
 *  and 10 withdrawal threads (each removing ₹500 twenty times),
 *  then verify the ending balance.
 */

import java.util.ArrayList;

public class _01_BankAccountSimulatorWrong {
    static class BankAccount{
        int balance=0;

        void deposit(int amount){
            balance+=amount;
        }

        void withdraw(int amount){
            balance-=amount;
        }
    }
    public static void main(String[] args) throws InterruptedException {
        BankAccount bankAccount = new BankAccount();
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(()->{
                for (int j = 0; j < 100; j++) {
                    bankAccount.deposit(1000);
                }
            });
            threads.add(t);
            t.start();
        }
        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(()->{
                for (int j = 0; j < 200; j++) {
                    bankAccount.withdraw(500);
                }
            });
            threads.add(t);
            t.start();
        }

        // Wait for all threads to complete
        for (Thread t : threads) {
            t.join();
        }
        System.out.println(bankAccount.balance);
    }
}
