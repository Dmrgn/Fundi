package app;

import interface_adapter.company_details.CompanyDetailsController;
import interface_adapter.company_details.CompanyDetailsViewModel;
import interface_adapter.ViewManagerModel;
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
     * @param viewManagerModel         the view manager model
     * @return the company details view
     */
    public static CompanyDetailsView create(CompanyDetailsViewModel companyDetailsViewModel,
            CompanyDetailsController companyDetailsController,
            ViewManagerModel viewManagerModel) {
        return new CompanyDetailsView(companyDetailsViewModel, companyDetailsController,
                viewManagerModel);
    }
}
