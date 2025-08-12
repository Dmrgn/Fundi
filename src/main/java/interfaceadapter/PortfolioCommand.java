package interfaceadapter;

import interfaceadapter.portfolio.PortfolioViewModel;

public interface PortfolioCommand {
    /**
     * Execute a command on the Portfolio View Model.
     * @param portfolioViewModel The Portfolio View Model
     */
    void execute(PortfolioViewModel portfolioViewModel);
}
