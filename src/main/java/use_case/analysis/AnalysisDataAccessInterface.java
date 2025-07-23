package use_case.analysis;

import entity.Transaction;

import java.util.List;
import java.util.Map;

public interface AnalysisDataAccessInterface {
    List<Transaction> pastTransactions(String portfolioId);
}
