import java.util.Random;

public class GamblerAccount extends BankAccount {
    @Override
    void withdraw(double amount) {
        Random rand = new Random();
        if(rand.nextDouble () > 0.5) count += 1;
    }

    @Override
    void endMonth(){
        currentBalance -= count;
    }
}
