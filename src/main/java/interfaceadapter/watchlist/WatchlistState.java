package interfaceadapter.watchlist;

import java.util.List;
import java.util.ArrayList;

public class WatchlistState {
    private String username;
    private List<String> tickers;
    private String errorMessage;
    private String successMessage;

    public WatchlistState() {
        this.tickers = new ArrayList<>();
        this.username = "";
        this.errorMessage = "";
        this.successMessage = "";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getTickers() {
        return tickers;
    }

    public void setTickers(List<String> tickers) {
        this.tickers = tickers;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }
}
