package interfaceadapter.company_details;

import interfaceadapter.ViewModel;

/**
 * The View Model for the Company Details View.
 */
public class CompanyDetailsViewModel extends ViewModel<CompanyDetailsState> {

    public static final String TITLE_LABEL = "Company Details";
    public static final String BACK_BUTTON_LABEL = "← Back to Search";

    public CompanyDetailsViewModel() {
        super("company_details");
        setState(new CompanyDetailsState());
    }
}
