package use_case.history;

import java.time.LocalDate;

public class HistoryOutputData {
    String[] tickers;
    double[] prices;
    int[] amounts;
    LocalDate[] dates;

    public HistoryOutputData(String[] tickers, double[] prices, int[] amounts, LocalDate[] dates) {
        this.tickers = tickers;
        this.prices = prices;
        this.amounts = amounts;
        this.dates = dates;
    }

    public String[] getTickers() {
        return tickers;
    }

    public double[] getPrices() {
        return prices;
    }

    public int[] getAmounts() {
        return amounts;
    }

    public LocalDate[] getDates() {
        return dates;
    }
}
