package app;

import interface_adapter.company_details.CompanyDetailsController;
import interface_adapter.company_details.CompanyDetailsViewModel;
import interface_adapter.navigation.NavigationController;
import view.CompanyDetailsView;

/**
 * Factory for creating the Company Details View.
 */
public class CompanyDetailsViewFactory {

    /**
     * Prevents instantiation.
     */
    private CompanyDetailsViewFactory() {
    }

    /**
     * Creates a CompanyDetailsView with all necessary dependencies.
     * 
     * @param companyDetailsViewModel  the company details view model
     * @param companyDetailsController the company details controller
     * @param navigationController     the navigation controller
     * @return the company details view
     */
    public static CompanyDetailsView create(CompanyDetailsViewModel companyDetailsViewModel,
            CompanyDetailsController companyDetailsController,
            NavigationController navigationController) {
        return new CompanyDetailsView(companyDetailsViewModel, companyDetailsController,
                navigationController);
    }
}
