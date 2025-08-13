package usecase.delete_portfolio;

public interface DeletePortfolioOutputBoundary {
    void presentSuccess(String username);

    void presentFailure(String error);
}
