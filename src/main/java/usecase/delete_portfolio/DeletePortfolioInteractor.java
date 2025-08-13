package usecase.delete_portfolio;

public class DeletePortfolioInteractor implements DeletePortfolioInputBoundary {
    private final DeletePortfolioDataAccessInterface dao;
    private final DeletePortfolioOutputBoundary presenter;

    public DeletePortfolioInteractor(DeletePortfolioDataAccessInterface dao, DeletePortfolioOutputBoundary presenter) {
        this.dao = dao;
        this.presenter = presenter;
    }

    @Override
    public void execute(DeletePortfolioInputData inputData) {
        try {
            dao.remove(inputData.getPortfolioName(), inputData.getUsername());
            presenter.presentSuccess(inputData.getUsername());
        } catch (Exception e) {
            presenter.presentFailure("Failed to delete portfolio: " + e.getMessage());
        }
    }
}
