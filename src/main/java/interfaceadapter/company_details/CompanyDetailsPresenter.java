package interfaceadapter.company_details;

import interfaceadapter.ViewManagerModel;
import usecase.company_details.CompanyDetailsOutputBoundary;
import usecase.company_details.CompanyDetailsOutputData;

/**
 * The Presenter for the Company Details Use Case.
 */
public class CompanyDetailsPresenter implements CompanyDetailsOutputBoundary {

    private final CompanyDetailsViewModel companyDetailsViewModel;
    private final ViewManagerModel viewManagerModel;

    public CompanyDetailsPresenter(CompanyDetailsViewModel companyDetailsViewModel,
            ViewManagerModel viewManagerModel) {
        this.companyDetailsViewModel = companyDetailsViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(CompanyDetailsOutputData outputData) {
        final CompanyDetailsState companyDetailsState = companyDetailsViewModel.getState();
        companyDetailsState.setCompanyDetails(outputData.getCompanyDetails());
        companyDetailsState.setErrorMessage(null);
        companyDetailsViewModel.setState(companyDetailsState);
        companyDetailsViewModel.firePropertyChanged();

        viewManagerModel.setState(companyDetailsViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final CompanyDetailsState companyDetailsState = companyDetailsViewModel.getState();
        companyDetailsState.setErrorMessage(errorMessage);
        companyDetailsState.setCompanyDetails(null);
        companyDetailsViewModel.setState(companyDetailsState);
        companyDetailsViewModel.firePropertyChanged();
    }
}
