import java.util.concurrent.locks.ReentrantLock;

class BankAccount {
    private int balance;
    private final ReentrantLock lock = new ReentrantLock();

    public BankAccount(int initialBalance) {
        this.balance = initialBalance;
    }

    public boolean transfer(BankAccount to, int amount) {
        boolean success = false;
        try {
            if (lock.tryLock() && to.lock.tryLock()) {
                if (balance >= amount) {
                    balance -= amount;
                    to.balance += amount;
                    success = true;
                }
            }
        } finally {
            lock.unlock();
            to.lock.unlock();
        }
        return success;
    }
}

public class BankTransaction {
    public static void main(String[] args) {
        BankAccount account1 = new BankAccount(1000);
        BankAccount account2 = new BankAccount(500);

        Thread t1 = new Thread(() -> System.out.println(account1.transfer(account2, 300)));
        Thread t2 = new Thread(() -> System.out.println(account2.transfer(account1, 200)));

        t1.start();
        t2.start();
    }
}
