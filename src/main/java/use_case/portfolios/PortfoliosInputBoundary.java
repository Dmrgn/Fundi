package use_case.portfolios;


/**
 * Input boundary for portfolios functionality
 */
public interface PortfoliosInputBoundary {

    /**
     * Executes the portfolio usecase.
     * @param portfoliosInputData the input data.
     */
    void execute(PortfoliosInputData portfoliosInputData);
}
