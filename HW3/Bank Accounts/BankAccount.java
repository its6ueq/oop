import java.util.Random;

abstract class BankAccount {
    double currentBalance;
    int count;
    int transactions;

    void constructor(double initialBalance){
        this.currentBalance = initialBalance;
        transactions = 0;
    };
    void deposit(double amount){
        currentBalance += amount;
        transactions++;
    };
    void withdraw(double amount){
        currentBalance -= amount;
        transactions++;
    }

     void endMonth () {
        transactions = 0;
        count = 0;
     }
 }