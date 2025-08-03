package use_case.portfolio_hub;

/**
 * The Input Data for the Portfolio Hub Use Case.
 */
public class PortfolioHubInputData {

    private final String username;

    public PortfolioHubInputData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
