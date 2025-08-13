package usecase.delete_portfolio;

import usecase.portfolio_hub.PortfolioHubDataAccessInterface;

public interface DeletePortfolioDataAccessInterface extends PortfolioHubDataAccessInterface {
    void remove(String portfolioName, String username);
}
