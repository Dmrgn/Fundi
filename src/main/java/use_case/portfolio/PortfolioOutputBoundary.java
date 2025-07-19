package use_case.portfolio;

/**
 * The output boundary for the Portfolio Use Case.
 */
public interface PortfolioOutputBoundary {
    /**
     * Prepares the success view for the Portfolio Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(PortfolioOutputData outputData);

    /**
     * Prepares the failure view for the Portfolio Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}