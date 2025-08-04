package use_case.create;

import java.util.Map;

/**
 * Output Data for the Create Use Case.
 */
public class CreateOutputData {

    private final String username;
    private final Map<String, String> portfolios;

    public CreateOutputData(String username, Map<String, String> portfolios) {
        this.username = username;
        this.portfolios = portfolios;
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
     * @return Portfolios
     */
    public Map<String, String> getPortfolios() {
        return portfolios;
    }
}