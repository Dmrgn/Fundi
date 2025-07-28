package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.create.CreateController;
import interface_adapter.create.CreateInteractor;
import interface_adapter.create.CreatePresenter;
import interface_adapter.create.CreateViewModel;
import interface_adapter.portfolios.PortfoliosViewModel;
import use_case.create.CreateDataAccessInterface;
import use_case.create.CreateInputBoundary;
import use_case.create.CreateOutputBoundary;

public class CreateUseCaseFactory {
    private CreateUseCaseFactory() {

    }

    public static CreateController create(
            ViewManagerModel viewManagerModel,
            PortfoliosViewModel portfoliosViewModel,
            CreateViewModel createViewModel,
            CreateDataAccessInterface dataAccessObject
    ) {
        CreateOutputBoundary createPresenter = new CreatePresenter(
                viewManagerModel,
                portfoliosViewModel,
                createViewModel
        );
        CreateInputBoundary createInteractor = new CreateInteractor(
                dataAccessObject,
                createPresenter
        );
        return new CreateController(createInteractor);
    }
}
