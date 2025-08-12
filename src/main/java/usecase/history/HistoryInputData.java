package usecase.history;

/**
 * Input data for the History Use Case.
 */
public class HistoryInputData {
    private final String portfolioId;

    public HistoryInputData(String portfolioId) {
        this.portfolioId = portfolioId;
    }

    /**
     * Getter.
     * @return Portfolio Id
     */
    public String getPortfolioId() {
        return portfolioId;
    }
}
