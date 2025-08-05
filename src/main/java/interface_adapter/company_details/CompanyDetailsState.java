package interface_adapter.company_details;

import entity.CompanyDetails;

/**
 * The State for the Company Details View Model.
 */
public class CompanyDetailsState {

    private CompanyDetails companyDetails;
    private String errorMessage;
    private String currentCompanySymbol;

    public CompanyDetailsState() {
    }

    public CompanyDetails getCompanyDetails() {
        return companyDetails;
    }

    public void setCompanyDetails(CompanyDetails companyDetails) {
        this.companyDetails = companyDetails;
        if (companyDetails != null) {
            this.currentCompanySymbol = companyDetails.getSymbol();
        }
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getCurrentCompanySymbol() {
        return currentCompanySymbol;
    }

    public void setCurrentCompanySymbol(String currentCompanySymbol) {
        this.currentCompanySymbol = currentCompanySymbol;
    }
}
