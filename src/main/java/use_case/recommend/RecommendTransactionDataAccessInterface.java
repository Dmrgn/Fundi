package use_case.recommend;

import java.util.Set;

public interface RecommendTransactionDataAccessInterface {
    public Set<String> getPortfolioTickers(String portfolioId);
}
