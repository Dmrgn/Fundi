package app;

import dataaccess.FinnhubCompanyDetailsDataAccessObject;
import interfaceadapter.ViewManagerModel;
import interfaceadapter.company_details.CompanyDetailsController;
import interfaceadapter.company_details.CompanyDetailsPresenter;
import interfaceadapter.company_details.CompanyDetailsViewModel;
import interfaceadapter.navigation.NavigationController;
import usecase.company_details.CompanyDetailsDataAccessInterface;
import usecase.company_details.CompanyDetailsInputBoundary;
import usecase.company_details.CompanyDetailsInteractor;
import usecase.company_details.CompanyDetailsOutputBoundary;

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
