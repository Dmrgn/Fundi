package use_case.create;

/**
 * The output boundary for the Create Use Case.
 */
public interface CreateOutputBoundary {
    /**
     * Prepares the success view for the Create Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(CreateOutputData outputData);

    /**
     * Prepares the failure view for the Create Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}