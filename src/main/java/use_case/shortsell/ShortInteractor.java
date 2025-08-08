package use_case.shortsell;

import entity.Transaction;
import java.time.LocalDate;

public class ShortInteractor implements ShortInputBoundary {
    private final ShortStockDataAccessInterface stockDAO;
    private final ShortTransactionDataAccessInterface txDAO;
    private final ShortOutputBoundary presenter;

    public ShortInteractor(ShortStockDataAccessInterface stockDAO,
                           ShortTransactionDataAccessInterface txDAO,
                           ShortOutputBoundary presenter) {
        this.stockDAO = stockDAO;
        this.txDAO = txDAO;
        this.presenter = presenter;
    }

    @Override
    public void execute(ShortInputData inputData) {
        String ticker = inputData.getTicker();
        int amount = inputData.getAmount();
        if (!stockDAO.hasTicker(ticker)) {
            presenter.prepareFailView("Ticker not available");
            return;
        }
        if (amount <= 0) {
            presenter.prepareFailView("Amount must be > 0");
            return;
        }
        double price = stockDAO.getPrice(ticker);
        Transaction tx = new Transaction(
                inputData.getPortfolioId(),
                ticker,
                amount,
                LocalDate.now(),
                -price
        );
        txDAO.save(tx);
        presenter.prepareSuccessView(new ShortOutputData(ticker, price, amount));
    }
}
