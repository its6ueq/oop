public class NickleAccount extends BankAccount {
    @Override
    void withdraw(double amount) {
        transactions ++;
    }

    @Override
    void endMonth(){
        currentBalance -= 0.5 * transactions;
    }
}
