package interface_adapter.portfolios;

import interface_adapter.ViewManagerModel;
import interface_adapter.create.CreateState;
import interface_adapter.create.CreateViewModel;
import interface_adapter.portfolios.PortfoliosState;
import interface_adapter.portfolios.PortfoliosViewModel;
import use_case.main.MainOutputBoundary;
import use_case.main.MainOutputData;
import use_case.portfolios.PortfoliosOutputBoundary;
import use_case.portfolios.PortfoliosOutputData;

/**
 * The Presenter for the portfolios Use Case.
 */
public class PortfoliosPresenter implements PortfoliosOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final CreateViewModel createViewModel;

    public PortfoliosPresenter(ViewManagerModel viewManagerModel, CreateViewModel createViewModel)  {
        this.viewManagerModel = viewManagerModel;
        this.createViewModel = createViewModel;
    }

    @Override
    public void prepareView(PortfoliosOutputData portfoliosOutputData) {
        switch (portfoliosOutputData.getPortfolioName()) {
            case "":
                final CreateState createState = createViewModel.getState();
                createState.setUsername(portfoliosOutputData.getUsername());
                this.createViewModel.setState(createState);
                this.createViewModel.firePropertyChanged();

                this.viewManagerModel.setState(createViewModel.getViewName());
                this.viewManagerModel.firePropertyChanged();
        }
    }
}