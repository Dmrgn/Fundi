package use_case.analysis;

import entity.Transaction;

import java.util.List;
import java.util.Map;

public interface AnalysisDataAccessInterface {
    Map<String, List<Transaction>> pastTransactions();
}
