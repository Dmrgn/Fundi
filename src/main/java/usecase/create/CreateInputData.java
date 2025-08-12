package usecase.create;

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

    /**
     * Getter.
     * @return Username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter.
     * @return Portfolio Name
     */
    public String getPortfolioName() {
        return portfolioName;
    }
}
