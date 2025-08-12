package usecase.company_details;

import entity.CompanyDetails;

/**
 * Output Data for the Company Details Use Case.
 */
public class CompanyDetailsOutputData {

    private final CompanyDetails companyDetails;
    private final boolean useCaseFailed;
    private final String errorMessage;

    public CompanyDetailsOutputData(CompanyDetails companyDetails, boolean useCaseFailed, String errorMessage) {
        this.companyDetails = companyDetails;
        this.useCaseFailed = useCaseFailed;
        this.errorMessage = errorMessage;
    }

    public CompanyDetails getCompanyDetails() {
        return companyDetails;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
