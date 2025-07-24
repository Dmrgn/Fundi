package use_case.portfolios;

/**
 * The Input Data for the Portfolios Use Case.
 */
public class PortfoliosInputData {

    private final String username;

    public PortfoliosInputData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
