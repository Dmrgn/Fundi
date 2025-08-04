package use_case.company_details;

/**
 * The Input Data for the Company Details Use Case.
 */
public class CompanyDetailsInputData {

    private final String symbol;

    public CompanyDetailsInputData(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
