package usecase.buy;

/**
 * The output boundary for the Buy Use Case.
 */
public interface BuyOutputBoundary {
    /**
     * Prepares the success view for the Buy Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(BuyOutputData outputData);

    /**
     * Prepares the failure view for the Buy Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
