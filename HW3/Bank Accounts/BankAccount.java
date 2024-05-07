import java.util.Random;

abstract class BankAccount {
    double currentBalance;
    int transactions;

    void constructor(double initialBalance){
        this.currentBalance = initialBalance;

    };
    void deposit(double amount){
        currentBalance += amount;
    };
    void withdraw(double amount){
        currentBalance -= amount;
    }

     void endMonth () {
        transactions = 0;
     }
 }