package use_case.analysis;

import entity.Transaction;

import java.util.List;

public interface AnalysisTransactionDataAccessInterface {
    List<Transaction> pastTransactions(String portfolioId);

}
