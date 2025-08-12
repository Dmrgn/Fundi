package usecase.buy;

import entity.Transaction;

/**
 * The Transaction DAO for the Buy Use Case.
 */
public interface BuyTransactionDataAccessInterface {
    /**
     * Save the transaction in the DAO.
     * @param transaction The new transaction
     */
    void save(Transaction transaction);
}
