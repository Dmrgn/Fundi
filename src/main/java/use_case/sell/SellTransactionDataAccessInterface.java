package use_case.sell;

import entity.Transaction;

public interface SellTransactionDataAccessInterface {
    double valueOfTicker(String portfolioId, String ticker);
    void save(Transaction transaction);

}
