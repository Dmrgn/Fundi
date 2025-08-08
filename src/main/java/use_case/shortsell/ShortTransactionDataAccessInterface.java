package use_case.shortsell;

import entity.Transaction;

public interface ShortTransactionDataAccessInterface {
    void save(Transaction transaction);
}
