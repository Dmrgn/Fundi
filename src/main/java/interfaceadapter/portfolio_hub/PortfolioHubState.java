package interfaceadapter.portfolio_hub;

import java.util.Map;

/**
 * The State for Portfolio Hub View Model.
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

    /**
     * Getter.
     * @return Portfolios
     */
    public Map<String, String> getPortfolios() {
        return portfolios;
    }

    /**
     * Setter.
     * @param portfolios Value
     */
    public void setPortfolios(Map<String, String> portfolios) {
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
     * Setter.
     * @param username Value
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter.
     * @return Id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter.
     * @param id Value
     */
    public void setId(String id) {
        this.id = id;
    }
}