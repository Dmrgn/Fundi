package usecase.company_details;

import entity.CompanyDetails;

/**
 * The Company Details Interactor.
 */
public class CompanyDetailsInteractor implements CompanyDetailsInputBoundary {

    private final CompanyDetailsDataAccessInterface companyDetailsDataAccessInterface;
    private final CompanyDetailsOutputBoundary companyDetailsPresenter;

    public CompanyDetailsInteractor(CompanyDetailsDataAccessInterface companyDetailsDataAccessInterface,
            CompanyDetailsOutputBoundary companyDetailsPresenter) {
        this.companyDetailsDataAccessInterface = companyDetailsDataAccessInterface;
        this.companyDetailsPresenter = companyDetailsPresenter;
    }

    @Override
    public void execute(CompanyDetailsInputData companyDetailsInputData) {
        try {
            final String symbol = companyDetailsInputData.getSymbol();
            final CompanyDetails companyDetails = companyDetailsDataAccessInterface.getCompanyDetails(symbol);

            final CompanyDetailsOutputData outputData = new CompanyDetailsOutputData(
                    companyDetails, false, null);
            companyDetailsPresenter.prepareSuccessView(outputData);
        }
        catch (Exception ex) {
            companyDetailsPresenter.prepareFailView("Failed to fetch company details: " + ex.getMessage());
        }
    }
}
