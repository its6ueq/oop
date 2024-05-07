public class NickleAccount extends BankAccount {
    @Override
    void withdraw(double amount) {
        count ++;
    }

    @Override
    void endMonth(){
        currentBalance -= 0.5 * count;
    }
}
