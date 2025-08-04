package app;

import data_access.FinnhubCompanyDetailsDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.company_details.CompanyDetailsController;
import interface_adapter.company_details.CompanyDetailsPresenter;
import interface_adapter.company_details.CompanyDetailsViewModel;
import interface_adapter.navigation.NavigationController;
import use_case.company_details.CompanyDetailsDataAccessInterface;
import use_case.company_details.CompanyDetailsInputBoundary;
import use_case.company_details.CompanyDetailsInteractor;
import use_case.company_details.CompanyDetailsOutputBoundary;

import java.io.IOException;

/**
 * Factory for the Company Details Use Case.
 */
public class CompanyDetailsUseCaseFactory {

    private CompanyDetailsUseCaseFactory() {
    }

    /**
     * Creates a CompanyDetailsController with all necessary dependencies.
     * 
     * @param viewManagerModel        the view manager model
     * @param companyDetailsViewModel the company details view model
     * @param navigationController    the navigation controller
     * @return the company details controller
     * @throws IOException if the data access object creation fails
     */
    public static CompanyDetailsController create(
            ViewManagerModel viewManagerModel,
            CompanyDetailsViewModel companyDetailsViewModel,
            NavigationController navigationController) throws IOException {

        CompanyDetailsDataAccessInterface companyDetailsDataAccess = new FinnhubCompanyDetailsDataAccessObject();

        CompanyDetailsOutputBoundary companyDetailsPresenter = new CompanyDetailsPresenter(companyDetailsViewModel,
                viewManagerModel);

        CompanyDetailsInputBoundary companyDetailsInteractor = new CompanyDetailsInteractor(companyDetailsDataAccess,
                companyDetailsPresenter);

        return new CompanyDetailsController(companyDetailsInteractor, navigationController);
    }
}
