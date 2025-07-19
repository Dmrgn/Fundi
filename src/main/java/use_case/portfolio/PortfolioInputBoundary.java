package use_case.portfolio;

/**
 * Input boundary for portfolio functionality
 */
public interface PortfolioInputBoundary {

    /**
     * Executes the portfolio usecase.
     * @param portfolioInputData the input data.
     */
    void execute(PortfolioInputData portfolioInputData);
}
