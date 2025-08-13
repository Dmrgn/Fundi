package usecase.watchlist;

import dataaccess.TickerCache;
import java.util.List;

public class WatchlistInteractor implements WatchlistInputBoundary {
    private final WatchlistDataAccessInterface dataAccess;
    private final WatchlistOutputBoundary presenter;

    public WatchlistInteractor(WatchlistDataAccessInterface dataAccess, WatchlistOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void addTicker(WatchlistInputData inputData) {
        String ticker = inputData.getTicker().trim().toUpperCase();

        // Validate input
        if (ticker.isEmpty()) {
            presenter.presentError("Please enter a ticker symbol.");
            return;
        }

        // Validate format
        if (!ticker.matches("^[A-Z]{1,5}$")) {
            presenter.presentError("Invalid ticker symbol. Please enter a valid symbol (1-5 letters).");
            return;
        }

        // Validate with cache
        if (!TickerCache.isValidTicker(ticker)) {
            presenter.presentError(ticker + " is not a real stock ticker.");
            return;
        }

        try {
            dataAccess.addToWatchlist(inputData.getUsername(), ticker);
            presenter.presentSuccess(ticker + " added to watchlist");
            fetchWatchlist(inputData.getUsername());
        } catch (Exception e) {
            presenter.presentError("Error adding ticker to watchlist: " + e.getMessage());
        }
    }

    @Override
    public void removeTicker(WatchlistInputData inputData) {
        try {
            dataAccess.removeFromWatchlist(inputData.getUsername(), inputData.getTicker());
            presenter.presentSuccess(inputData.getTicker() + " removed from watchlist successfully!");
            fetchWatchlist(inputData.getUsername());
        } catch (Exception e) {
            presenter.presentError("Error removing ticker from watchlist: " + e.getMessage());
        }
    }

    @Override
    public void fetchWatchlist(String username) {
        try {
            List<String> tickers = dataAccess.getWatchlist(username);
            presenter.presentWatchlist(new WatchlistOutputData(username, tickers));
        } catch (Exception e) {
            presenter.presentError("Error loading watchlist: " + e.getMessage());
        }
    }
}
