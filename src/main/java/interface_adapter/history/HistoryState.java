package interface_adapter.history;

import java.time.LocalDate;

public class HistoryState {
    String[] tickers;
    double[] prices;
    int[] amounts;
    LocalDate[] dates;

    public HistoryState(HistoryState copy) {
        this.tickers = copy.tickers;
        this.prices = copy.prices;
        this.amounts = copy.amounts;
        this.dates = copy.dates;
    }

    public HistoryState() {

    }

    public String[] getTickers() {
        return tickers.clone();
    }

    public void setTickers(String[] tickers) {
        this.tickers = tickers.clone();
    }

    public double[] getPrices() {
        return prices.clone();
    }

    public void setPrices(double[] prices) {
        this.prices = prices.clone();
    }

    public int[] getAmounts() {
        return amounts.clone();
    }

    public void setAmounts(int[] amounts) {
        this.amounts = amounts.clone();
    }

    public LocalDate[] getDates() {
        return dates.clone();
    }

    public void setDates(LocalDate[] dates) {
        this.dates = dates.clone();
    }

}
