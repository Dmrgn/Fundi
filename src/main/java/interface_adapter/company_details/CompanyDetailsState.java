package interface_adapter.company_details;

import entity.CompanyDetails;
import java.util.ArrayList;
import java.util.List;

/**
 * The State for the Company Details View Model.
 */
public class CompanyDetailsState {

    private CompanyDetails companyDetails;
    private String errorMessage;
    private List<String> breadcrumbs = new ArrayList<>();
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

    public List<String> getBreadcrumbs() {
        return breadcrumbs;
    }

    public void setBreadcrumbs(List<String> breadcrumbs) {
        this.breadcrumbs = breadcrumbs != null ? breadcrumbs : new ArrayList<>();
    }

    public String getCurrentCompanySymbol() {
        return currentCompanySymbol;
    }

    public void setCurrentCompanySymbol(String currentCompanySymbol) {
        this.currentCompanySymbol = currentCompanySymbol;
    }
}
