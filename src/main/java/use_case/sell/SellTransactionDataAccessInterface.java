package use_case.sell;

import entity.Transaction;

public interface SellTransactionDataAccessInterface {
    int amountOfTicker(String portfolioId, String ticker);
    void save(Transaction transaction);

}
