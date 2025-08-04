package use_case.company_details;

/**
 * The output boundary for the Company Details Use Case.
 */
public interface CompanyDetailsOutputBoundary {

    /**
     * Prepares the success view for the Company Details Use Case.
     * 
     * @param outputData the output data
     */
    void prepareSuccessView(CompanyDetailsOutputData outputData);

    /**
     * Prepares the failure view for the Company Details Use Case.
     * 
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
