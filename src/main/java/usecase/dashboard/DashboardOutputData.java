package usecase.dashboard;

import java.util.List;

import entity.PortfolioValuePoint;

/**
 * Output data for the Dashboard use case.
 */
public class DashboardOutputData {
    private final List<PortfolioValuePoint> portfolioValueHistory;
    private final boolean useCaseFailed;

    public DashboardOutputData(List<PortfolioValuePoint> portfolioValueHistory, boolean useCaseFailed) {
        this.portfolioValueHistory = portfolioValueHistory;
        this.useCaseFailed = useCaseFailed;
    }

    public List<PortfolioValuePoint> getPortfolioValueHistory() {
        return portfolioValueHistory;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
