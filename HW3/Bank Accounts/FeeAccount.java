public class FeeAccount extends BankAccount {
    @Override
    void endMonth(){
        currentBalance -= 5;
    }
}