package interface_adapter.portfolios;

import java.util.Map;

/**
 * The State information representing the portfolios page user.
 */
public class PortfoliosState {
    private Map<String, String> portfolios;

    public PortfoliosState(PortfoliosState copy) {
        portfolios = copy.portfolios;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public PortfoliosState() {

    }

    public Map<String, String> getPortfolios() {
        return portfolios;
    }

    public void setPortfolios(Map<String, String> portfolios) {
        this.portfolios = portfolios;
    }
}