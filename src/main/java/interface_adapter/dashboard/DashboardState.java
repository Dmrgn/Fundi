package interface_adapter.dashboard;

import entity.PortfolioValuePoint;
import java.util.ArrayList;
import java.util.List;

/**
 * The state for the Dashboard View Model.
 */
public class DashboardState {
    private String username = "";
    private List<PortfolioValuePoint> portfolioValueHistory = new ArrayList<>();
    private String errorMessage = "";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<PortfolioValuePoint> getPortfolioValueHistory() {
        return portfolioValueHistory;
    }

    public void setPortfolioValueHistory(List<PortfolioValuePoint> portfolioValueHistory) {
        this.portfolioValueHistory = portfolioValueHistory != null ? new ArrayList<>(portfolioValueHistory)
                : new ArrayList<>();
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
