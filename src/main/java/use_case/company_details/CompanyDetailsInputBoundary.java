package use_case.company_details;

/**
 * Input Boundary for actions which are related to fetching company details.
 */
public interface CompanyDetailsInputBoundary {

    /**
     * Executes the company details use case.
     * 
     * @param companyDetailsInputData the input data
     */
    void execute(CompanyDetailsInputData companyDetailsInputData);
}
