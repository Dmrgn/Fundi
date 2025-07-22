package entity;

import java.util.List;
import java.util.ArrayList;

/**
 * The representation of a portfolio.
 */
public class Portfolio {

    private final List<Transaction> transactions;

    public Portfolio() {
        transactions = new ArrayList<>();
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
}