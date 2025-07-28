package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.create.CreateViewModel;
import interface_adapter.portfolios.PortfoliosController;
import interface_adapter.portfolios.PortfoliosInteractor;
import interface_adapter.portfolios.PortfoliosPresenter;
import interface_adapter.portfolios.PortfoliosViewModel;
import use_case.portfolios.PortfoliosDataAccessInterface;
import use_case.portfolios.PortfoliosInputBoundary;
import use_case.portfolios.PortfoliosOutputBoundary;

public class PortfoliosUseCaseFactory {
    private PortfoliosUseCaseFactory() {

    }

    public static PortfoliosController create(
            ViewManagerModel viewManagerModel,
            PortfoliosViewModel portfoliosViewModel,
            CreateViewModel createViewModel,
            PortfoliosDataAccessInterface dataAccessObject
    ) {
        PortfoliosOutputBoundary portfoliosPresenter = new PortfoliosPresenter(
                viewManagerModel, portfoliosViewModel, createViewModel
        );
        PortfoliosInputBoundary portfoliosInteractor = new PortfoliosInteractor(
                portfoliosPresenter, dataAccessObject
        );
        return new PortfoliosController(portfoliosInteractor);
    }
}
