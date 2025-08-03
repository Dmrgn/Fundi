package use_case.create;

/**
 * The Input Data for the Create Use Case.
 */
public class CreateInputData {

    private final String username;
    private final String portfolioName;

    public CreateInputData(String username, String portfolioName) {
        this.username = username;
        this.portfolioName = portfolioName;
    }

    public String getUsername() {
        return username;
    }

    public String getPortfolioName() {
        return portfolioName;
    }
}
