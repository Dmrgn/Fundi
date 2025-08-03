package interface_adapter.portfolioHub;

import java.util.Map;

/**
 * The State information representing the portfolios page user.
 */
public class PortfolioHubState {
    private Map<String, String> portfolios;
    private String username;
    private String id;

    public PortfolioHubState(PortfolioHubState copy) {
        portfolios = copy.portfolios;
        username = copy.username;
        id = copy.id;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public PortfolioHubState() {

    }

    public Map<String, String> getPortfolios() {
        return portfolios;
    }

    public void setPortfolios(Map<String, String> portfolios) {
        this.portfolios = portfolios;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}