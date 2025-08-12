package interfaceadapter.company_details;

import interfaceadapter.navigation.NavigationController;
import usecase.company_details.CompanyDetailsInputBoundary;
import usecase.company_details.CompanyDetailsInputData;

/**
 * Controller for the Company Details Use Case.
 */
public class CompanyDetailsController {

    private final CompanyDetailsInputBoundary companyDetailsUseCaseInteractor;
    private final NavigationController navigationController;
    private String currentCompanySymbol; // Track current company

    public CompanyDetailsController(CompanyDetailsInputBoundary companyDetailsUseCaseInteractor,
            NavigationController navigationController) {
        this.companyDetailsUseCaseInteractor = companyDetailsUseCaseInteractor;
        this.navigationController = navigationController;
    }

    /**
     * Executes the Company Details Use Case.
     * 
     * @param symbol      the company symbol
     * @param currentView the current view name for navigation
     */
    public void execute(String symbol, String currentView) {
        // Update navigation stack with company symbol tracking
        if ("company_details".equals(currentView) && currentCompanySymbol != null) {
            // Navigating from one company to another
            navigationController.navigateToCompany(currentView, "company_details", currentCompanySymbol);
        } else {
            // Navigating from a different view (like search) to company details
            navigationController.navigateTo(currentView, "company_details");
        }

        // Update current company symbol
        currentCompanySymbol = symbol;

        final CompanyDetailsInputData companyDetailsInputData = new CompanyDetailsInputData(symbol);
        companyDetailsUseCaseInteractor.execute(companyDetailsInputData);
    }

    /**
     * Get the current company symbol being displayed
     */
    public String getCurrentCompanySymbol() {
        return currentCompanySymbol;
    }
}
