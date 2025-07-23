package use_case.buy;

import entity.Transaction;

/**
 * DAO for the Buy Use Case.
 */
public interface BuyTransactionDataAccessInterface {

    void addTransaction(Transaction transaction);
}