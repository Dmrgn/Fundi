package usecase.portfolio;

/**
 * Input boundary for portfolio functionality
 */
public interface PortfolioInputBoundary {

    /**
     * Executes the portfolio usecase.
     * @param portfolioInputData the input data.
     */
    void execute(PortfolioInputData portfolioInputData);

    /**
     * Switch to the Buy Use Case.
     * @param portfolioId Portfolio Id used to update the Buy View Model State.
     */
    void routeToBuy(String portfolioId);

    /**
     * Switch to the Sell Use Case.
     * @param portfolioId Portfolio Id used to update the Sell View Model State.
     */
    void routeToSell(String portfolioId);
}
