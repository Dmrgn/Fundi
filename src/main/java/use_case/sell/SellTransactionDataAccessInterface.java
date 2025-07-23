package use_case.sell;

import entity.Transaction;

public interface SellTransactionDataAccessInterface {
    void save(Transaction transaction);

}
