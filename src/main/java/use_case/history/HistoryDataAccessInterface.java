package use_case.history;

import entity.Transaction;

import java.util.List;

public interface HistoryDataAccessInterface {
    List<Transaction> pastTransactions(String portfolioId);
}
